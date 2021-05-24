package exporter_project.demo.configuration;

import java.util.Collections;
import java.util.List;

public class Column {

    private String input;

    private String output;

    private String type;

    private List<Transformation> transformations;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<Transformation> getTransformations() {
        return (transformations==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(transformations);
    }

    public void setTransformations(List<Transformation> transformations) {
        this.transformations = transformations;
    }
}
