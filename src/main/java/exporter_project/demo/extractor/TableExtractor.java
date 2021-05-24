package exporter_project.demo.extractor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exporter_project.demo.IFormatter;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.OutputFile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TableExtractor{

    private static final Logger log = LogManager.getLogger(TableExtractor.class);

    public List<Row> extract(
            OutputFile file,
            JdbcTemplate jdbcTemplate,
            String sql,
            Object[] queryParams,
            IFormatter formatter,
            ResultSetObject resultSetObject,
            ArrayList<String> inputColumns,
            ArrayList<String> outputColumns) {


        log.info("Extract...");

        if (queryParams.length>0) {
            log.info("lastExtractDate : " + queryParams[0]);
            log.info("currentExtractDate : " + queryParams[1]);
        }

        return jdbcTemplate.query(
                sql,
                queryParams,
                (rs, rowNum) -> {
                    Row row =  new Row(
                            file.getOutputColumnNames()
                                    .stream()
                                    .map(
                                            outputColumnName -> {
                                                String inputColumnName = inputColumns.get(outputColumns.indexOf(outputColumnName));
                                                try {
                                                    KeyValue kv;
                                                    if(inputColumnName.equals("")){
                                                        kv = new KeyValue(
                                                                outputColumnName,
                                                                file.transform(outputColumnName, "", resultSetObject.getValues().get(rowNum) )          /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
                                                        );
                                                    } else {
                                                        kv = new KeyValue(
                                                                outputColumnName,                                            /*key is each input column name ex: id, contacttype, firstname, lastname*/
                                                                file.transform(outputColumnName, rs.getObject(inputColumnName), resultSetObject.getValues().get(rowNum))          /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
                                                        );                                                      /*keyvalue is the input name column and its corresponding value ex: (id, 1), (contacttype, UNKNOWN), (firstname, Ennov)*/
                                                    }
                                                    return kv;
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                    )
                                    .collect(Collectors.toList())
                    );
                    log.info("Row : " + row.toString());

                    formatter.format(row); /*write reach row in csv format before closing*/
                    return null;
                }
        );
    }
}