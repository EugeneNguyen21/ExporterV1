package exporter_project.demo.extractor;

import org.springframework.jdbc.core.RowMapper;
import exporter_project.demo.KeyValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Row implements RowMapper<Row> {

    private final Map<String, Object> fieldValueMap = new HashMap<>();

    public Row(final List<KeyValue> fieldvalues) {

        fieldvalues.stream().forEach(
                kv -> fieldValueMap.put(kv.getKey(), kv.getValue())
        );
    }

    public Row() {

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        fieldValueMap.entrySet()
                .stream()
                .forEach(
                        (entry) -> sb
                                .append("\n")
                                .append(entry.getKey())
                                .append(" : ")
                                .append(entry.getValue()==null?"--null--":entry.getValue().toString())

                );
        return sb.toString();
    }

    public List<Object> rowValues(List<String> keys) {

        return keys
                .stream()
                .map(
                        key -> fieldValueMap.get(key)
                )
                .collect(Collectors.toList());
    }

    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    @Override
    public Row mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
