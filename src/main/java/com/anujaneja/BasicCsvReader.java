package com.anujaneja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasicCsvReader {

    private static final String CSV_FILE_PATH = "/Users/admin/Downloads/Channel_Item_Type.csv";
    private static final String OUT_FILE = "output.csv";

    public static void main(String[] args) throws IOException {
        ChannelItemTypeCSVProcessor channelItemTypeCSVProcessor = new ChannelItemTypeCSVProcessor(OUT_FILE);
        channelItemTypeCSVProcessor.processCSV(CSV_FILE_PATH);
    }


}

