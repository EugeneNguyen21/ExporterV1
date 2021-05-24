package exporter_project.demo.deployment;

import exporter_project.demo.IActivityHandler;
import exporter_project.demo.KeyValue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Activity {


    private String handlerClassName;

    private List<KeyValue> parameters;


    public IActivityHandler getActivityHandler() {
        try {
            return (IActivityHandler)Class.forName(handlerClassName).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getHandlerClassName() {
        return handlerClassName;
    }

    public void setHandlerClassName(String handlerClassName) {
        this.handlerClassName = handlerClassName;
    }

    public List<KeyValue> getParameters() {
        return (parameters==null)? Collections.EMPTY_LIST:Collections.unmodifiableList(parameters);
    }

    public void setParameters(List<KeyValue> parameters) {
        this.parameters = parameters;
    }

    public Object getParameterValue(String parameterName) {
        if (parameters==null) {
            return null;
        }

        Optional<Object> value = parameters
                .stream()
                .filter(kv -> kv.getKey().equals(parameterName))
                .map(kv -> kv.getValue())
                .findFirst();

        if (value.isPresent()) {
            return value.get();
        } else {
            return null;
        }
    }
}
