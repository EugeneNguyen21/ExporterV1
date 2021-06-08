package exporter_project.demo.extractor;


import exporter_project.demo.IFormatter;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.OutputFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import java.util.stream.Collectors;

public class TableExtractor{

    static final Logger log = LogManager.getLogger("MyFile");

    public void extract(
            OutputFile file,
            JdbcTemplate jdbcTemplate,
            String sql,
            Object[] queryParams,
            IFormatter formatter,
            ResultSetObject resultSetObject,
            ArrayList<String> inputColumns,
            ArrayList<String> outputColumns) {


        log.info("Start import data to empty csv file");

//        if (queryParams.length>0) {
//            log.info("lastExtractDate : " + queryParams[0]);
//            log.info("currentExtractDate : " + queryParams[1]);
//        }

        int numOfRows = 0;
        for (int i = 0; i < resultSetObject.getValues().size(); i++) {
            int finalI = i;
            Row row =  new Row(
                    file.getOutputColumnNames()
                            .stream()
                            .map(
                                    outputColumnName -> {
                                        String inputColumnName = inputColumns.get(outputColumns.indexOf(outputColumnName));
                                        KeyValue kv;
                                        if(inputColumnName.equals("")){
                                            kv = new KeyValue(
                                                    outputColumnName,
                                                    file.transform(outputColumnName, "", resultSetObject.getValues().get(finalI))          /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
                                            );
                                        } else {
                                            kv = new KeyValue(
                                                    outputColumnName,                                            /*key is each input column name ex: id, contacttype, firstname, lastname*/
                                                    file.transform(outputColumnName, resultSetObject.getValues().get(finalI).getFieldValueMap().get(inputColumnName), resultSetObject.getValues().get(finalI))          /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
                                            );                                                      /*keyvalue is the input name column and its corresponding value ex: (id, 1), (contacttype, UNKNOWN), (firstname, Ennov)*/
                                        }
                                        return kv;
                                    }
                            )
                            .collect(Collectors.toList())
            );
            formatter.format(row); /*write reach row in csv format before closing*/
            numOfRows++;
        }

        log.info("Import to csv done " + "- " + " " + numOfRows + " rows imported to csv file");
    }
}
