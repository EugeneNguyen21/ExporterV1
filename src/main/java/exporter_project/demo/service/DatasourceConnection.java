package exporter_project.demo.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.*;

@Configuration
public class DatasourceConnection {
    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    public ResultSet ConnectToDB(String sql) throws SQLException {


        try {
            connection = DriverManager.getConnection(datasourceURL, datasourceUsername, datasourcePassword);
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
        return resultSet;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
