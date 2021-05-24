package exporter_project.demo.configuration;

import java.util.Collections;
import java.util.List;

public class Model {

    private String name;

    private String description;

    private List<OutputFile> files;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OutputFile> getFiles() {
        return (files==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(files);
    }

    public void setFiles(List<OutputFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Name : " + name + "\nDescription : " + description + "\n";
    }
}
