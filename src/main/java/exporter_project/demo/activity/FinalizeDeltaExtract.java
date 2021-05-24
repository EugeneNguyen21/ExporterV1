package exporter_project.demo.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exporter_project.demo.IActivityHandler;
import exporter_project.demo.deployment.Activity;
import org.springframework.jdbc.core.JdbcTemplate;


public class FinalizeDeltaExtract implements IActivityHandler {

    private static final Logger log = LogManager.getLogger(FinalizeDeltaExtract.class);

    public void execute(JdbcTemplate jdbcTemplate, Activity activity) {

//        if ((Boolean)Context.getInstance().get(InitiateDeltaExtract.NO_LAST_EXTRACT_DATE)) {
//            jdbcTemplate.execute(
//                    "INSERT INTO parameters(`key`, context, `value`) VALUES(\'"
//                            + InitiateDeltaExtract.LAST_EXTRACT_DATE
//                            + "\', \'"
//                            + (String) Context.getInstance().get(DemoApplication.MODEL_NAME)
//                            + "\', \'"
//                            + ((Timestamp) Context.getInstance().get(InitiateDeltaExtract.CURRENT_EXTRACT_DATE)).toString()
//                            + "\')"
//            );
//
//        } else {
//            jdbcTemplate.execute(
//                    "UPDATE parameters SET `value` = \'"
//                            + ((Timestamp) Context.getInstance().get(InitiateDeltaExtract.CURRENT_EXTRACT_DATE)).toString() +
//                            "\' WHERE `key` = \'"
//                            + InitiateDeltaExtract.LAST_EXTRACT_DATE
//                            + "\' AND context = \'"
//                            + (String) Context.getInstance().get(DemoApplication.MODEL_NAME)
//                            + "\'"
//            );
//        }

    }
}
