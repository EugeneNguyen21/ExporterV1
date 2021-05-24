package exporter_project.demo.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exporter_project.demo.IActivityHandler;
import exporter_project.demo.deployment.Activity;
import org.springframework.jdbc.core.JdbcTemplate;

public class PurgeFiles implements IActivityHandler {

    private static final Logger log = LogManager.getLogger(PurgeFiles.class);

    public void execute(JdbcTemplate jdbcTemplate, Activity activity) {

    }
}
