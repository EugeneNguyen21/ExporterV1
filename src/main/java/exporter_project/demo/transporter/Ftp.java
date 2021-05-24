package exporter_project.demo.transporter;

import exporter_project.demo.ITransporter;
import exporter_project.demo.deployment.Transport;
import org.apache.commons.net.ftp.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;


import java.io.*;

@Component
public class Ftp implements ITransporter {

    private static final Logger log = LogManager.getLogger(Ftp.class);

    private String server;
    private int port;
    private String user;
    private String password;

    public static Logger getLog() {
        return log;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void transport(String filePath, Transport transport) {
        this.server = transport.getServer();
        this.port = transport.getPort();
        this.user = transport.getUser();
        this.password = transport.getPassword();
        System.out.println("file path is  " + filePath);
        System.out.println("server is " + this.server);
        System.out.println("user is " + this.user);
        System.out.println("password is " + this.password);

        FTPClient client = new FTPClient();
        FileInputStream fis = null;

        try {
            client.connect(getServer());
            boolean success = client.login(getUser(), getPassword());
            if(success)
                System.out.println("connection successful");
            else
                System.out.println("connection failed");

            File file = new File(filePath);
            String fileName = StringUtils.cleanPath(file.getName());

            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            InputStream inputStream = new FileInputStream(file);
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
    }

//        try {
//            FtpClientIntegrationTest ftpServer = null;
//            if (transport.isFakeServer()) {
//                log.info("Run the FakeServer!");
//                this.server = "192.168.0.108";
//                this.port = 21;
//                this.user = "FTP-User";
//                this.password = "123456";
//                ftpServer = new FtpClientIntegrationTest(port, user, password);
//                port = ftpServer.start();
//
//            }
//            log.info("port : " + port);
//
//            ftpClient = new FTPClient();
//            ftpClient.enterLocalPassiveMode();
//
//            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
//
//            ftpClient.connect(server, port);
//            ftpClient.enterLocalPassiveMode();
//            int reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                throw new RuntimeException("Exception in connecting to FTP Server");
//            }
//
//            ftpClient.login(user, password);
//
//            File file = new File(filePath);
//
//            log.info("Will upload " + filePath);
//            ftpClient.storeFile(file.getName(), new FileInputStream(file));
//            log.info("Uploaded : " + filePath);
//
//            // check if file is uploaded
//            FTPFile[] files = ftpClient.listFiles("");
//
//            log.info("Ftp files " + files.length);
//
//            Arrays
//                    .stream(files)
//                    .forEach(
//                            f -> log.info("Ftp file : " + f)
//                    );
//            /*
//            Optional<String> uploadFileName = Arrays
//                    .stream(files)
//                    .map(FTPFile::getName)
//                    .filter(f -> f.equals(file.getName()))
//                    .findFirst();
//
//            if (uploadFileName.isPresent()) {
//                log.info(uploadFileName.get());
//            } else {
//                throw new RuntimeException("File was not uploaded");
//            }
//            */
//
//            if (transport.isFakeServer()) {
//                ftpServer.teardown();
//            }

//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    void close() throws IOException {
//        ftpClient.disconnect();
//    }

    public static class FtpClientIntegrationTest {

        private FakeFtpServer fakeFtpServer;

        private int port = 21;
        private String user = "FTP-User";
        private String password = "123456";

        public FtpClientIntegrationTest(int port, String user, String password) {
            this.port = port;
            this.user = user;
            this.password = password;
        }

        public int start() throws IOException {
            fakeFtpServer = new FakeFtpServer();
            fakeFtpServer.addUserAccount(new UserAccount(user, password, "/data"));

            FileSystem fileSystem = new UnixFakeFileSystem();
            fileSystem.add(new DirectoryEntry("/data"));
            fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
            fakeFtpServer.setFileSystem(fileSystem);
            fakeFtpServer.setServerControlPort(port);

            fakeFtpServer.start();

            return fakeFtpServer.getServerControlPort();
        }

        public void teardown() throws IOException {
            fakeFtpServer.stop();
        }
    }
}
