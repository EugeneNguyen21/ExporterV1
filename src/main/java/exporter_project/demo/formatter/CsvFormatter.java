package exporter_project.demo.formatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exporter_project.demo.IFormatter;
import exporter_project.demo.extractor.Row;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class CsvFormatter implements IFormatter {

    private static final Logger log = LogManager.getLogger(CsvFormatter.class);

    List<String> inputColumns;
    String[] headers;
    boolean includeHeaders;

    FileWriter out;

    CSVPrinter printer;

    public String init(
            List<String> inputColumns,
            String[] headers,
            boolean includeHeaders,
            String name,
            String fileNamePattern,
            String outputFolder
    ) {


        log.info("Init Csv Converter");

        this.inputColumns = inputColumns;
        this.headers = headers;
        this.includeHeaders = includeHeaders;

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hh_mm");
        String extractDate = dateFormat.format(date);
        System.out.println("extract date is " + extractDate);


        // TODO fileNamePattern
        String fileName = "EVENT_V3_"+ name+ "_FR_" + extractDate + ".csv";
        String filePath = outputFolder + File.separator + fileName;


        try {

            out = new FileWriter(filePath);

        } catch(IOException e) {

            throw new RuntimeException(e);
        }

        try  {

            printer = includeHeaders?
                    new CSVPrinter(out, CSVFormat.POSTGRESQL_CSV.withHeader(headers).withDelimiter('|'))
                    :
                    new CSVPrinter(out, CSVFormat.POSTGRESQL_CSV);

            log.info("CSV file: " + fileName + " was created at " + LocalDateTime.now());
            log.trace("trace logger");
            log.error("error");
            log.warn("warn");
            log.fatal("fatal");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }

    @Override
    public void format(ArrayList row) {

    }

    public void format(
            Row row
    ) {
        try {
            log.info("the row info is " + row.toString());
            printer.printRecord(row.rowValues(Arrays.asList(headers)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            printer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
