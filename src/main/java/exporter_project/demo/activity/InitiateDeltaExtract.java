package exporter_project.demo.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exporter_project.demo.IActivityHandler;
import exporter_project.demo.deployment.Activity;
import org.springframework.jdbc.core.JdbcTemplate;

public class InitiateDeltaExtract implements IActivityHandler {

    private static final Logger log = LogManager.getLogger(InitiateDeltaExtract.class);

    public static final String NO_LAST_EXTRACT_DATE = "noLastExtractDate";
    public static final String LAST_EXTRACT_DATE = "lastExtractDate";
    public static final String CURRENT_EXTRACT_DATE = "currentExtractDate";

    public void execute(JdbcTemplate jdbcTemplate, Activity activity) {

//        NO_LAST_EXTRACT_DATE.length();
//
//        Timestamp lastExtractDate;
//
//        Optional<Timestamp> _lastExtractDate = jdbcTemplate.query(
//                "select `value` from `parameters` where `key`=?",
//                new String[]{LAST_EXTRACT_DATE},
//                (rs, rowNum) -> rs.getTimestamp("value")
//        ).stream().findFirst();
//
//        if (_lastExtractDate.isEmpty()) {
//            Context.getInstance().put(NO_LAST_EXTRACT_DATE, Boolean.TRUE);
//            lastExtractDate = Timestamp.valueOf("2000-01-01 00:00:00");
//        } else {
//            Context.getInstance().put(NO_LAST_EXTRACT_DATE, Boolean.FALSE);
//            lastExtractDate = _lastExtractDate.get();
//        }
//        Timestamp currentDate = Timestamp.from(Instant.now());
//        Context.getInstance().put(LAST_EXTRACT_DATE, lastExtractDate);
//        Context.getInstance().put(CURRENT_EXTRACT_DATE, currentDate);
//
//        System.out.println("current extract date string form is " + currentDate);

    }
}
