package exporter_project.demo.activity;


import exporter_project.demo.IActivityHandler;
import exporter_project.demo.deployment.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class PurgeFiles implements IActivityHandler {

    private static final Logger log = LoggerFactory.getLogger(PurgeFiles.class);

    public void execute(JdbcTemplate jdbcTemplate, Activity activity) {

    }
}
