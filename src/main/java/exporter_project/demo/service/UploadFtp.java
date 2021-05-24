package exporter_project.demo.service;
import java.io.*;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

@Service
public class UploadFtp {
    public void uploadFtp() {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        File localFile = new File("C:\\Users\\Admin\\Desktop\\exported_files\\M05 - Utilitites User Book V9.docx");

        try {
            client.connect("192.168.0.108");
            client.login("User-FTP", "123456");


            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            String remoteFile = "M05 - Utilitites User Book V9.docx";
            InputStream inputStream = new FileInputStream(localFile);
            System.out.println("Start uploading file");

            boolean done = client.storeFile(remoteFile, inputStream);

            if (done) {
                System.out.println("The file is uploaded successfully.");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
