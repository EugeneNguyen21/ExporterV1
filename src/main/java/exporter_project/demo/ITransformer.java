package exporter_project.demo;

import exporter_project.demo.configuration.Transformation;

import java.util.ArrayList;
import java.util.List;

public interface ITransformer {

    Object transform(
            Transformation transformation,
            Object input,
            List<KeyValue> parameters,
            ArrayList<KeyValue> row
    );

    Object transform(
            Transformation transformation,
            Object input,
            List<KeyValue> parameters
    );




}
