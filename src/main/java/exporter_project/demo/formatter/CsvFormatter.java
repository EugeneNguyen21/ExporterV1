package exporter_project.demo.formatter;


import exporter_project.demo.IFormatter;
import exporter_project.demo.extractor.ResultSetObject;
import exporter_project.demo.extractor.Row;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class CsvFormatter implements IFormatter {

    static final Logger log = LogManager.getLogger("MyFile");

    List<String> inputColumns;
    String[] headers;
    boolean includeHeaders;

    FileWriter out;

    CSVPrinter printer;

    public String init(
            List<String> inputColumns,
            String[] headers,
            boolean includeHeaders,
            String fileName,
            String fileNamePattern,
            String outputFolder
    ) {

        this.inputColumns = inputColumns;
        this.headers = headers;
        this.includeHeaders = includeHeaders;

        // TODO fileNamePattern
        String filePath = outputFolder + File.separator + fileName;

        log.info("Create empty CSV file " + "[" + fileName + "]");

        try {

            out = new FileWriter(filePath);
            System.out.println("");

        } catch(IOException e) {

            throw new RuntimeException(e);
        }

        try  {

            printer = includeHeaders?
                    new CSVPrinter(out, CSVFormat.POSTGRESQL_CSV.withHeader(headers).withDelimiter('|'))
                    :
                    new CSVPrinter(out, CSVFormat.POSTGRESQL_CSV);

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
//            log.info("the row info is " + row.toString());
            printer.printRecord(row.rowValues(Arrays.asList(headers)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(ResultSetObject resultSetObject) {
        try {
            printer.close();
            resultSetObject.getValues().clear();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
