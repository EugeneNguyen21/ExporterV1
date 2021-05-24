package exporter_project.demo.transporter;

import exporter_project.demo.ITransporter;
import exporter_project.demo.deployment.Transport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DummyTransporter implements ITransporter {

    private static final Logger log = LogManager.getLogger(DummyTransporter.class);

    @Override
    public void transport(String fileName, Transport transport) {
        log.info("DummyTransporter.transport(...)");
    }
}
