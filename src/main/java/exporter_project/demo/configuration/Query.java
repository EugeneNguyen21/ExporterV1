package exporter_project.demo.configuration;

import exporter_project.demo.Context;

import java.util.Collections;
import java.util.List;

public class Query {

    private String dbVendor;

    private String sql;

    private List<String> params;

    public String getDbVendor() {
        return dbVendor;
    }

    public void setDbVendor(String dbVendor) {
        this.dbVendor = dbVendor;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getParams() {
        return (params==null)? Collections.EMPTY_LIST:Collections.unmodifiableList(params);
    }

    public Object[] getParamValues() {
        return params
                .stream()
                .map(param -> Context.getInstance().get(param))
                .toArray();
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
