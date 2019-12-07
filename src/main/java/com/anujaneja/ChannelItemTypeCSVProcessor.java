package com.anujaneja;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChannelItemTypeCSVProcessor implements CSVProcessor {

    private String outFile;
    private FileWriter fileWriter;

    public ChannelItemTypeCSVProcessor(String outFile) {
        this.outFile = outFile;
    }

    @Override
    public void processCSV(String filePath) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                FileWriter fileWriter = new FileWriter(new File(outFile));
        ) {

            this.fileWriter= fileWriter;

            for (CSVRecord csvRecord : csvParser) {
                if(csvRecord.getRecordNumber()==1) {
                    continue;
                }
                processRow(csvRecord);
            }
        }

    }

    @Override
    public void processRow(CSVRecord csvRecord) throws IOException {

        // Accessing Values by Column Index
        String channelCode = csvRecord.get(0);
        String channelProductId = csvRecord.get(1);
        String sellerSkuCode = csvRecord.get(2);
        String uniwareSkuCode = csvRecord.get(3);

        System.out.println("Record No - " + csvRecord.getRecordNumber());
        System.out.println("---------------");
        System.out.println("channelCode : " + channelCode);
        System.out.println("channelProductId : " + channelProductId);
        System.out.println("sellerSkuCode : " + sellerSkuCode);
        System.out.println("uniwareSkuCode : " + uniwareSkuCode);

        StringBuilder builder = new StringBuilder();
        builder.append("update channel_item_type cit set cit.seller_sku_code=:sellerSkuCode,disabled_due_to_errors=0 where cit.channel_product_id=:channelProductId and "
                + " cit.channel_id=(select id from channel where code=:channelCode) and cit.tenant_id=:tenantId;");
        String query = builder.toString();
        query = query.replace(":sellerSkuCode", "'"+sellerSkuCode+"'")
                .replace(":channelProductId", "'"+channelProductId+"'")
                .replace(":channelCode", "'"+channelCode+"'")
                .replace(":tenantId", "1");
        System.out.println(query);
        this.fileWriter.write(query);
        this.fileWriter.write("\n");
        System.out.println("---------------\n\n");
    }

}
