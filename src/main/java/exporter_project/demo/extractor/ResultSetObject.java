package exporter_project.demo.extractor;

import exporter_project.demo.configuration.OutputFile;
import exporter_project.demo.service.DatasourceConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import exporter_project.demo.KeyValue;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultSetObject {

    List<Row> resultSetValues = new ArrayList<>();

    public void setValues(Row row){
        resultSetValues.add(row);
    }

    public List<Row> getValues(){
        return resultSetValues;
    }

}
