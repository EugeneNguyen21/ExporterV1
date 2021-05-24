package exporter_project.demo.controller;

import exporter_project.demo.service.Exporter;
import exporter_project.demo.service.UploadFtp;
import exporter_project.demo.service.UploadToFtp;
import exporter_project.demo.transporter.Ftp;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ConvertController {
    @Autowired
    Exporter exporter;

    @RequestMapping(value = "/")
    public String homePage() {

        return "index";
    }


    @RequestMapping(value = "/convertFromDB", method = RequestMethod.GET)
    public JSONObject convertFromDB() throws IOException, SQLException, NoSuchAlgorithmException {
        return exporter.exportFromDB();
    }
}

