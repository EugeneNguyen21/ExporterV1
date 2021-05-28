package exporter_project.demo.transformer;

import exporter_project.demo.ITransformer;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.Transformation;
import exporter_project.demo.extractor.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Mapper implements ITransformer {

    public static final String MAPPING_TABLE = "mappingTable";
    public static final String DEFAULT_VALUE = "defaultValue";

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters) {
        return null;
    }

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters, Row row) {

        Map mappingTable = (Map)transformation.getParameterValue(MAPPING_TABLE);
        if (mappingTable==null) {
            mappingTable = Collections.EMPTY_MAP;
        }

        Object value = mappingTable.get(input);

        if (value!=null) return value;

        Object defaultValue = transformation.getParameterValue(DEFAULT_VALUE);
        if (defaultValue!=null) {
            return defaultValue;
        }

        throw new RuntimeException("Mapping not defined for " + input.toString());
    }
}
