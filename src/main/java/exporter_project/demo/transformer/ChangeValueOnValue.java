package exporter_project.demo.transformer;

import exporter_project.demo.ITransformer;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.Transformation;
import exporter_project.demo.extractor.Row;

import java.util.*;

public class ChangeValueOnValue implements ITransformer {

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters, Row row) {

        for (int i = 0; i < parameters.size(); i++) {
            System.out.println("parameter key is " + parameters.get(0).getKey().toString());
            System.out.println("parameter value is " + parameters.get(0).getValue().toString());
        }
        List<String> rowKey  = new ArrayList<String>(row.getFieldValueMap().keySet() );
        List<Object> rowValue  = new ArrayList<Object>( row.getFieldValueMap().values() );

        Object _input = input;

        System.out.println("row key is " + rowKey);
        System.out.println("row value is " + rowValue);

        for (int i = 0; i < parameters.size(); i++) {
            List<String> parameterValue = (List<String>) parameters.get(i).getValue();
            if(row.getFieldValueMap().containsKey(parameters.get(i).getKey())){
                if(rowValue.contains(parameterValue.get(0))){
                    _input = parameterValue.get(1);
                }
            }
        }

        return _input;
    }

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters) {
        return null;
    }
}
