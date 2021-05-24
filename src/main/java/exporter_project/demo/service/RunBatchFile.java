package exporter_project.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RunBatchFile {

//    @Scheduled(fixedDelay = 10000)
    public void runBatchFile() throws IOException {
        Runtime.getRuntime().exec("C:\\Users\\Admin\\Desktop\\exported_files\\deleteFiles.bat");
        System.out.println("batch file running");
    }
}
