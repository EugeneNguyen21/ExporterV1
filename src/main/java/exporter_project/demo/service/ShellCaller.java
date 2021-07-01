package exporter_project.demo.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Service
public class ShellCaller {
    public void callShell(String... command) {
//        ProcessBuilder pb = new ProcessBuilder("cmd","/c","src/main/resources/test.sh");
//
//        pb.redirectErrorStream(true);
//        Process shell = null;
//        try {
//            shell = pb.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        InputStream shellin = shell.getInputStream();
//        try {
//            int shellExitStatus = shell.waitFor();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try{
//            shellin.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
        String password = "DA#2Lqx825#PGJ1oH";
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);

        try {
            Process process = processBuilder.start();

            //read the output
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
//            bw.write(password + "\n");
//            bw.flush();
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println("shell caller output is " + output);
            }

            //wait for the process to complete
            boolean exitCode = process.waitFor(30000, TimeUnit.MILLISECONDS);
            System.out.println("exit code is " + exitCode);

//            close the resources
            bufferedReader.close();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
