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
        dos.writeBytes("forfiles /p " + outputFolder +  " /s /m *.* /D -2 /C \"cmd /c del @path\"");
    }
}


