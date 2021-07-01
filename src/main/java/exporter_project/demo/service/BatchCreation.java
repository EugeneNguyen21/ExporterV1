package exporter_project.demo.service;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BatchCreation {
    public void createBat(String outputFolder) throws IOException {

        String batchFilepath = outputFolder + File.separator + "deleteFiles" + ".bat";

        File file=new File(batchFilepath);
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos=new DataOutputStream(fos);
//        dos.writeBytes("forfiles /p " + outputFolder +  " /s /m *.* /D -2 /C \"cmd /c del @path\"");

        dos.writeBytes("cd desktop/Ennov/ExporterV1\n");
        dos.writeBytes("sftp sftpuser@149.202.140.150\n");
        dos.writeBytes("yes\n");
        dos.writeBytes("DA#2Lqx825#PGJ1oH\n");
        dos.writeBytes("get FROM_ATRIUM/ASIP_HCP_ATRIUM_INFIRMIER_1.csv C:\\Users\\Admin\\Desktop\n");
    }
}


