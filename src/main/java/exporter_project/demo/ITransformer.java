package exporter_project.demo;

import exporter_project.demo.configuration.Transformation;
import exporter_project.demo.extractor.Row;

import java.util.ArrayList;
import java.util.List;

public interface ITransformer {

    Object transform(
            Transformation transformation,
            Object input,
            List<KeyValue> parameters,
            Row row
    );

    Object transform(
            Transformation transformation,
            Object input,
            List<KeyValue> parameters
    );




}
