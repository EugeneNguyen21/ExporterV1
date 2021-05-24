package exporter_project.demo;

import exporter_project.demo.deployment.Activity;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IActivityHandler {

    void execute(JdbcTemplate jdbcTemplate, Activity activity);
}
