package exporter_project.demo.extractor;

import org.springframework.stereotype.Component;
import exporter_project.demo.KeyValue;

import java.util.ArrayList;

@Component
public class ResultSetObject {

    private ArrayList<ArrayList<KeyValue>> values = new ArrayList<>();

    public void addNewValue(ArrayList<KeyValue> keyValues){
        values.add(keyValues);
    }
    public void setValues(ArrayList<ArrayList<KeyValue>> values) {
        this.values = values;
    }

    public ArrayList<ArrayList<KeyValue>> getValues() {
        return values;
    }

    public Object getSingleValue(String inputColName, int rowIndex){
        ArrayList<KeyValue> row = getValues().get(rowIndex);
        for (KeyValue keyValue : row) {
            if (inputColName.equals(keyValue.getKey())) {
                return keyValue.getValue();
            }
        }
        return "";
    }




}
