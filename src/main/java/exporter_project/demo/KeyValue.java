package exporter_project.demo;

import exporter_project.demo.configuration.OutputFile;
import exporter_project.demo.configuration.Transformation;
import exporter_project.demo.extractor.Row;

import java.util.ArrayList;
import java.util.List;

public class KeyValue {

    private String key;

    private Object value;


    public KeyValue() {
        this(null, null);
    }

    public KeyValue(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    public Object transform(String outputColumnName, Object value, OutputFile file, Row row) {

        List<Transformation> transformers = file.getTransformations(outputColumnName);
        Object _value = value;

        for(Transformation transformation : transformers) {
            setValue(transformation.getTransformer().transform(transformation, value, transformation.getParameters(), row));
        }

        if(value == null){
            return "";
        }

        return value; /*_value is the values of the parameters ex:UNKNOWN, ADMIN, LN_ADMIN*/
    }

}
