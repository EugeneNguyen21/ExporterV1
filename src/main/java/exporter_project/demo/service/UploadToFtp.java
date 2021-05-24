package exporter_project.demo.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
public class UploadToFtp {
    public Boolean uploadToFtp(MultipartFile file) {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        if (file.isEmpty()) {
            return false;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            client.connect("192.168.0.108");
            boolean success = client.login("User-FTP", "123456");
            if(success)
                System.out.println("connection successful");
            else
                System.out.println("connection failed");

            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            InputStream inputStream = file.getInputStream();
            System.out.println("Start uploading file");

            boolean done = client.storeFile(fileName, inputStream);

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

        return true;
    }

}
