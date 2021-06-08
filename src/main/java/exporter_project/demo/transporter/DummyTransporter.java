package exporter_project.demo.transporter;

import exporter_project.demo.ITransporter;
import exporter_project.demo.deployment.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DummyTransporter implements ITransporter {

    private static final Logger log = LoggerFactory.getLogger(DummyTransporter.class);

    @Override
    public void transport(String fileName, Transport transport) {
        log.info("DummyTransporter.transport(...)");
    }
}
