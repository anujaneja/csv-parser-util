package com.anujaneja;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public interface CSVProcessor {

    public void processCSV(String filePath) throws IOException;

    public void processRow(CSVRecord csvRecord) throws IOException;
}
