package exporter_project.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import exporter_project.demo.*;
import exporter_project.demo.extractor.Row;
import exporter_project.demo.extractor.TableExtractor;
import exporter_project.demo.formatter.JsonFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import exporter_project.demo.extractor.ResultSetObject;
import exporter_project.demo.configuration.Model;
import exporter_project.demo.configuration.Query;
import exporter_project.demo.deployment.Deployment;
import exporter_project.demo.transporter.Ftp;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Exporter {

    private final Logger log = LogManager.getLogger(DemoApplication.class);

    public final String MODEL_NAME = "MODEL_NAME";

    @Value("${export.model.filename}")
    private String modelFileName;

    @Value("${export.deployment.filename}")
    private String deploymentFileName;




    @Autowired
    DatasourceConnection datasourceConnection;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    Context context;

    @Autowired
    Ftp ftp;

    @Autowired
    BatchCreation batchCreation;

    @Autowired
    ResultSetObject resultSetObject;

    @Autowired
    HashFunction hashFunction;

    @Autowired
    JsonFormatter jsonFormatter;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    public JSONObject exportFromDB() throws IOException, SQLException, NoSuchAlgorithmException {
        JSONObject jsonObjectArray = new JSONObject();

        File modelFile = new File(modelFileName);
        if (!modelFile.isFile()) {
            throw new RuntimeException("Export model file <" + modelFileName + "> doesn't exists");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Model model = objectMapper.readValue(
                modelFile,
                Model.class
        );

        Context.getInstance().put(MODEL_NAME, model.getName());

        File deploymentFile = new File(deploymentFileName);
        if (!deploymentFile.isFile()) {
            throw new RuntimeException("Export deployment file <" + deploymentFileName + "> doesn't exists");
        }

        Deployment deployment = objectMapper.readValue(
                deploymentFile,
                Deployment.class
        );

        // preparation activities
        deployment
                .getPreparationActivities()
                .stream()
                .forEach(activity -> activity.getActivityHandler().execute(jdbcTemplate, activity));

        model
                .getFiles()
                .stream()
                .forEach(
                        file -> {

                            Optional<Query> query = file.getQuery(deployment.getExtractor().getDbVendor());
                            if (query.isEmpty()) {
                                throw new RuntimeException("No query defined for dbVendor <" + deployment.getExtractor().getDbVendor() + "> in file <" + file.getName() + ">");
                            }

                            String filePath = null;
                            Connection connection = null;
                            PreparedStatement statement = null;
                            ArrayList<String> inputColumns = (ArrayList<String>) file.getInputColumnNames();
                            ArrayList<String> outputColumns = (ArrayList<String>) file.getOutputColumnNames();
                            ArrayList<ArrayList<KeyValue>> allValueInRs = new ArrayList<>();

                            jdbcTemplate.query(
                                    query.get().getSql(),
                                    query.get().getParamValues(),
                                    (rs, rowNum) -> {
                                        List<String> resultSetColNames = new ArrayList<>();
                                        int colCount = rs.getMetaData().getColumnCount();
                                        for (int i = 1; i <= colCount; i++) {
                                            resultSetColNames.add(rs.getMetaData().getColumnName(i));
                                        }
                                        Row row =  new Row(
                                                resultSetColNames.
                                                        stream().
                                                        map(
                                                                colName -> {
                                                                    KeyValue kv = new KeyValue(colName, "");
                                                                    try {
                                                                        kv.setValue(rs.getObject(String.valueOf(colName)));
                                                                    } catch (SQLException throwables) {
                                                                        throwables.printStackTrace();
                                                                    }
                                                                    return kv;
                                                                }
                                                        ).collect(Collectors.toList())
                                        );
                                        resultSetObject.setValues(row);
                                        return null;
                                    }
                            );



//                            try{
//                                connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
//                                statement = connection.prepareStatement(query.get().getSql());
//                                rs = statement.executeQuery();
//                                ResultSetMetaData rsmd = rs.getMetaData();
//
//                                List<String> resultSetColNames = new ArrayList<>();
//                                int colCount = rsmd.getColumnCount();
//                                for (int i = 1; i <= colCount; i++) {
//                                    resultSetColNames.add(rsmd.getColumnName(i));
//                                }
//
//                                while(rs.next()) {
//
//                                    Row row = new Row(
//                                            resultSetColNames.
//                                                    stream().
//                                                    map(
//                                                            colName -> {
//                                                                KeyValue kv;
//                                                                try {
//                                                                    kv = new KeyValue(colName, finalRs.getObject(String.valueOf(colName)));
//                                                                    return kv;
//                                                                } catch (SQLException throwables) {
//                                                                    throwables.printStackTrace();
//                                                                }
//
//                                                            }
//                                                    ).collect(Collectors.toList())
//                                    );
//                                    resultSetObject.setValues(row);
//                                }
//
//                                datasourceConnection.closeConnection();
//                            }catch(Exception e){ System.out.println(e);}


                            // Formatting & Transformation
                            IFormatter formatter;
                            try {
                                formatter = (IFormatter) Class.forName(file.getFormatter()).getConstructor().newInstance();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }



                            try {
                                filePath = formatter.init(                  /*create empty csv file + format + name format + return filepath*/
                                        inputColumns,
                                        outputColumns.toArray(new String[]{}),
                                        file.isIncludeHeaders(),
                                        file.getName(),
                                        file.getFileNamePattern(),
                                        deployment.getFormatter().getOutputFolder()
                                );
                            } catch (ParseException | SQLException e) {
                                e.printStackTrace();
                            }

                            // Extraction
                            TableExtractor tableExtractor = new TableExtractor();

                            tableExtractor.extract(
                                    file,
                                    jdbcTemplate,
                                    query.get().getSql(),
                                    query.get().getParamValues(),
                                    formatter,
                                    resultSetObject,
                                    inputColumns,
                                    outputColumns
                            );

                            jsonFormatter.addValueToJson(
                                    file,
                                    query.get().getParamValues(),
                                    jsonObjectArray,
                                    resultSetObject,
                                    inputColumns,
                                    outputColumns
                            );


                            formatter.close(); /*finished writing content for csv file*/

//                            // Transport
//                            ITransporter transporter;
//                            try {
//                                transporter = (ITransporter) Class.forName(file.getTransport().getHandlerClassName()).getConstructor().newInstance();
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }

//                            transporter.transport(filePath, deployment.getTransport());
                            ftp.transport(filePath, deployment.getTransport());

                            File csvFile = new File(filePath);
                            MessageDigest messageDigest = null;
                            try {
                                messageDigest = MessageDigest.getInstance("MD5");
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            try {
                                System.out.println(hashFunction.checksum(messageDigest, csvFile));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                );
        // finalization activities
        deployment
                .getFinalizationActivities()
                .stream()
                .forEach(activity -> activity.getActivityHandler().execute(jdbcTemplate, activity));

        return jsonObjectArray;
    }
}
