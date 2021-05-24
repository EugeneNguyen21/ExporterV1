package exporter_project.demo.transformer;

import exporter_project.demo.ITransformer;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.Transformation;

import java.util.ArrayList;
import java.util.List;

public class VAL_ChangeValueOnValue implements ITransformer {

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters, ArrayList<KeyValue> row) {

        return input;
    }

    @Override
    public Object transform(Transformation transformation, Object input, List<KeyValue> parameters) {
        return null;
    }
}
