package exporter_project.demo.configuration;

import exporter_project.demo.KeyValue;
import exporter_project.demo.extractor.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OutputFile {

    private String name;

    private String formatter;

    private String fileNamePattern;

    private Transport transport;

    private List<Query> queries;

    private List<Column> columns;

    private boolean includeHeaders;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public List<Query> getQueries() {
        return (queries==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(queries);
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public Optional<Query> getQuery(String dbVendor) {
        return queries
                .stream()
                .filter(
                        query -> query.getDbVendor().toLowerCase().equals(dbVendor.toLowerCase())
                )
                .findFirst();
    }

    public List<Column> getColumns() {
        return (columns==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(columns);
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<String> getInputColumnNames() {
        return columns
                .stream()
                .map(
                        column -> column.getInput()
                )
                .collect(Collectors.toList());
    }

    public List<String> getOutputColumnNames() {
        return columns
                .stream()
                .map(
                        column -> column.getOutput()
                )
                .collect(Collectors.toList());
    }

    public Object transform(String outputColumnName, Object value, Row row) {

        Object _value = value;
        for(Transformation transformation : getTransformations(outputColumnName)) {
            _value = transformation.getTransformer().transform(transformation, _value, transformation.getParameters(), row);
        }
        if(value == null){
            return "";
        }
        return _value; /*transformed value*/
    }

    public List<Transformation> getTransformations(String columnName) {

        Optional<List<Transformation>> transformers = columns
                .stream()
                .filter(column -> column.getOutput().equals(columnName))
                .map(column -> column.getTransformations())
                .collect(Collectors.toList())
                .stream()
                .findFirst();

        if (transformers.isPresent()) {
            return transformers.get();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }
}
