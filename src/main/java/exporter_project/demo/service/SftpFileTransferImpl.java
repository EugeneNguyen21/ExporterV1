//package thymleaf_project.demo.services;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//
//
//@Service
//public class SftpFileTransferImpl implements SftpFileTransfer {
//
//    private Logger logger = LoggerFactory.getLogger(SftpFileTransferImpl.class);
//
//    private String remoteHost = "home668831122.1and1-data.host";
//    private String username = "u88250653-eventv3";
//    private String password = "BJin6UYcW@yy87n";
//    private int port = 22;
//
//
//    @Override
//    public Boolean uploadFile(String localFilePath, String remoteFilePath) throws JSchException {
//        ChannelSftp channelSftp = setupJsch();
//        channelSftp.connect();
//        try {
//            channelSftp.put(localFilePath, remoteFilePath);
//            return true;
//        } catch(SftpException ex) {
//            logger.error("Error upload file", ex);
//        } finally {
//            disconnectChannelSftp(channelSftp);
//        }
//        return false;
//
//    }
//
//    @Override
//    public boolean downloadFile(String localFilePath, String remoteFilePath) {
////        ChannelSftp channelSftp = createChannelSftp();
////        OutputStream outputStream;
////        try {
////            File file = new File(localFilePath);
////            outputStream = new FileOutputStream(file);
////            channelSftp.get(remoteFilePath, outputStream);
////            file.createNewFile();
////            return true;
////        } catch(SftpException | IOException ex) {
////            logger.error("Error download file", ex);
////        } finally {
////            disconnectChannelSftp(channelSftp);
////        }
//
//        return false;
//    }
//
//    private ChannelSftp setupJsch() throws JSchException {
//        JSch jsch = new JSch();
////        jsch.setKnownHosts("C:\\Users\\Admin\\Desktop\\exported files");
//        Session jschSession = jsch.getSession(username, remoteHost, port);
//        jschSession.setPassword(password);
//        jschSession.connect();
//        return (ChannelSftp) jschSession.openChannel("sftp");
//    }
//
//    private void disconnectChannelSftp(ChannelSftp channelSftp) {
//        try {
//            if( channelSftp == null)
//                return;
//
//            if(channelSftp.isConnected())
//                channelSftp.disconnect();
//
//            if(channelSftp.getSession() != null)
//                channelSftp.getSession().disconnect();
//
//        } catch(Exception ex) {
//            logger.error("SFTP disconnect error", ex);
//        }
//    }
//
//}
