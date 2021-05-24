package exporter_project.demo.transformer;

import exporter_project.demo.ITransformer;
import exporter_project.demo.KeyValue;
import exporter_project.demo.configuration.Transformation;

import java.util.ArrayList;
import java.util.List;

public class COL_ChangeColName implements ITransformer {

    @Override
    public String transform(Transformation transformation, Object input, List<KeyValue> parameters) {
        return null;
    }

    @Override
    public String transform(Transformation transformation, Object input, List<KeyValue> parameters, ArrayList<KeyValue> row) {
        System.out.println("COL__ChangeColName is " + input);

        switch(String.valueOf(input)){
            case "FR":
                return "Europe";
            case "VN":
                return "Asia";
            case "JP":
                return "Japan";
            case "Ennov":
                return "Company";
            case "Duc":
                return "Product Specialist";
            case "Minh":
                return "Support";
            default:
                return "";
        }
    }
}
