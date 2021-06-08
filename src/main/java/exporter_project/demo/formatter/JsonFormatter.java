package exporter_project.demo.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exporter_project.demo.extractor.Row;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import exporter_project.demo.extractor.ResultSetObject;
import exporter_project.demo.IFormatter;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.OutputFile;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class JsonFormatter implements IFormatter {

    static final Logger log = LogManager.getLogger("MyFile");


    public void addValueToJson(OutputFile file,
                               Object[] queryParams,
                               JSONObject jsonObjectArray,
                               ResultSetObject resultSetObject,
                               ArrayList<String> inputColumns,
                               ArrayList<String> outputColumns) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode jsonObjectValue = mapper.createArrayNode();


        for (int i = 0; i < resultSetObject.getValues().size(); i++) {
            ObjectNode item = mapper.createObjectNode();
            Row row = resultSetObject.getValues().get(i);
            Map<String, Object> rowValueKey = row.getFieldValueMap();

            file.getOutputColumnNames()
                    .stream()
                    .forEach(
                            outputColName ->{
                                Object value = null;
                                KeyValue kv;
                                String inputColName = inputColumns.get(outputColumns.indexOf(outputColName));
                                    if(rowValueKey.containsKey(inputColName)){
                                        value = rowValueKey.get(inputColName);
                                        System.out.println("value is bbbbbbbbb" + value);
                                    } else {
                                        value = "";
                                    }
                                kv = new KeyValue(outputColName, value);
                                kv.transform(outputColName,value , file, row) ;
                                item.put(kv.getKey(), String.valueOf(kv.getValue()));
                                System.out.println("jsonObjectValue is " + item);
                                System.out.println("jsonObjectValue is " + kv);
                            }
                    );
            jsonObjectValue.add(item);
            System.out.println("jsonObjectValue is " + jsonObjectValue);

        }
        jsonObjectArray.put(file.getName(), jsonObjectValue);


//        for (int i = 0; i < resultSetObject.getValues().size(); i++){
//            ObjectNode item = mapper.createObjectNode();
//            for (int j = 0; j < file.getOutputColumnNames().size(); j++) {
//                String outputColName = file.getOutputColumnNames().get(j);
//                String inputColName = inputColumns.get(outputColumns.indexOf(outputColName));
//                Row row = resultSetObject.getValues().get(i);
//                Object value = resultSetObject.getFieldValue(inputColName, i);
//
//                KeyValue kv;
//
//                System.out.println("value before transform ");
//
//                if(inputColName.equals("")){
//                    kv = new KeyValue(
//                            outputColName,
//                            value
//                    );
//                    kv.transform(outputColName,"" , file, row) ;
//                } else {
//                    kv = new KeyValue(
//                            outputColName,
//                            value
//                    );
//                    kv.transform(outputColName,value , file, row);
//                    System.out.println("value in JsonFormatter is " + kv.getValue() + " and key is " + kv.getKey());
//                }
//                item.put(outputColName,String.valueOf(value));
//                item.put(kv.getKey(), String.valueOf(kv.getValue()));
//            }
//            jsonObjectValue.add(item);
//        }
//        jsonObjectArray.put(file.getName(), jsonObjectValue);
//
//        log.info("Json object: " + file.getName() + " was created at " + LocalDateTime.now());

//        jdbcTemplate.query(
//                sql,
//                queryParams,
//                (rs, rowNum) -> {
//                    ObjectNode item = mapper.createObjectNode();
//                    row =  new ArrayList(
//                            file.getOutputColumnNames()              /*get a list of input column names from model.json ex: id, contactType, firstName, lastName, "", "", Street*/
//                                    .stream()
//                                    .map(
//                                            outputColumnName -> {
//                                                String inputColumn = inputColumns.get(outputColumns.indexOf(outputColumnName));
//                                                KeyValue kv = new KeyValue();
//                                                Object value;
//                                                String key;
//                                                try {
//                                                    if(inputColumn.equals("")){
//                                                        value = file.transform(outputColumnName, "", resultSetObject.getValues());
//                                                        kv = new KeyValue(
//                                                                outputColumnName,
//                                                                value         /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
//                                                        );
//                                                        kv.transform(outputColumnName,value , file) ;
//                                                    } else {
//                                                        value = file.transform(outputColumnName, rs.getObject(inputColumn), resultSetObject.getValues()) ;
//                                                        kv = new KeyValue(
//                                                                outputColumnName,                                            /*key is each input column name ex: id, contacttype, firstname, lastname*/
//                                                                value        /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
//                                                        );
//                                                        kv.transform(outputColumnName,value , file);
//                                                        key = kv.getKey();
//                                                        System.out.println("kv value is " + kv.getKey() + " and " + kv.getValue());
//                                                    }
//                                                    item.put(outputColumnName,String.valueOf(value));
//                                                    return kv;
//                                                } catch (SQLException e) {
//                                                    throw new RuntimeException(e);
//                                                }
//                                            }
//                                    )
//                                    .collect(Collectors.toList())
//                    );
//                    jsonObjectValue.add(item);
//                    return null;
//                }
//        );
//        jsonObjectArray.put(file.getName(),jsonObjectValue);
    }


    @Override
    public String init(List<String> inputColumns, String[] headers, boolean includeHeaders, String name, String fileNamePattern, String outputFolder) throws ParseException, SQLException {
        return null;
    }

    @Override
    public void format(ArrayList row) {

    }

    @Override
    public void format(Row row) {

    }

    @Override
    public void close(ResultSetObject resultSetObject) {

    }


}
