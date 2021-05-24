package exporter_project.demo;

import exporter_project.demo.deployment.Transport;

public interface ITransporter {

    public void transport(
            String fileName,
            Transport transport
    );


}
