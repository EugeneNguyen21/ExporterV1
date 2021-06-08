package exporter_project.demo;

import exporter_project.demo.extractor.ResultSetObject;
import exporter_project.demo.extractor.Row;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface IFormatter {

    String init(
            List<String> inputColumns,
            String[] headers,
            boolean includeHeaders,
            String name,
            String fileNamePattern,
            String outputFolder
    ) throws ParseException, SQLException;

//    void format(Row row);
    void format(ArrayList row);

    void format(Row row);

    void close(ResultSetObject resultSetObject);
}
