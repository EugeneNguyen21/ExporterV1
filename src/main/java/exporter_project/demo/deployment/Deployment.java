package exporter_project.demo.deployment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deployment {

    private ArrayList<Activity> preparationActivities;
    private Extractor extractor;
    private Formatter formatter;
    private Transport transport;
    private ArrayList<Activity> finalizationActivities;


    public List<Activity> getPreparationActivities() {
        return (preparationActivities==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(preparationActivities);
    }

    public void setPreparationActivities(ArrayList<Activity> preparationActivities) {
        this.preparationActivities = preparationActivities;
    }

    public Extractor getExtractor() {
        return extractor;
    }

    public void setExtractor(Extractor extractor) {
        this.extractor = extractor;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public List<Activity> getFinalizationActivities() {
        return (finalizationActivities==null)?Collections.EMPTY_LIST:Collections.unmodifiableList(finalizationActivities);
    }

    public void setFinalizationActivities(ArrayList<Activity> finalizationActivities) {
        this.finalizationActivities = finalizationActivities;
    }
}
