package exporter_project.demo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Context {

    private Map<String, Object> parameters = new HashMap<>();

    private static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private Context() {
        super();
    }

    public void put(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Object get(String key) {
        return this.parameters.get(key);
    }
}

