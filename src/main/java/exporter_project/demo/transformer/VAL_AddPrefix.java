package exporter_project.demo.transformer;

import exporter_project.demo.ITransformer;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.Transformation;
import exporter_project.demo.extractor.Row;

import java.util.ArrayList;
import java.util.List;

public class VAL_AddPrefix implements ITransformer {

    public static final String PREFIX = "VAL_prefix";

    public String getTransformerName (){
        return "VAL_prefix";
    }

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters) {
        return null;
    }

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters, Row row) {

        return transformation.getParameterValue(PREFIX).toString() + input;
    }
}

