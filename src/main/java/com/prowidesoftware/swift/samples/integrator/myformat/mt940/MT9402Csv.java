package com.prowidesoftware.swift.samples.integrator.myformat.mt940;

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.csv.CsvWriter;
import com.prowidesoftware.swift.myformat.mt.MtReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MT9402Csv {

    //
    // sample input
    //
    static String mtFileName       = "/MT940/MT940.txt";
    static String tableFileName    = "/MT940/MT940ToCsv.xls";

    public static void main(String[] args) {

        // Create a mapping table instance with source and target formats and load rules from excel file
        MappingTable table = new MappingTable(FileFormat.MT, FileFormat.CSV);
        MappingTable.loadFromSpreadsheet(MT9402Csv.class.getResourceAsStream(tableFileName), "Mapping", table);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        if (problems.size() > 0) {

            System.out.println("ERROR: found " + problems.size() + " problems in the mapping table:");
            for (String s : problems) {
                System.out.println("PROBLEM: " + s);
            }
            return;
        }

        // create the mt reader with the message
        String mtStr;
        MtReader reader;
        try {
            mtStr = readFromResource(MT9402Csv.class.getResourceAsStream(mtFileName));
            reader = new MtReader(mtStr);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // create the writer
        CsvWriter.CsvWriterBuilder builder = CsvWriter.builder();
        builder.addFieldNames(true).smartEscapes(false).smartQuotes(false);
        builder.fieldsDef("Block1,Block2,TransactionReferenceNumber,RelatedReference,StatementOrSequenceNumber,OpeningBalance,StatementLine,TransactionDetail,ClosingBalance,AvailableBalance");
        CsvWriter writer = builder.build();


        // process the csv and generate the mt output
        MyFormatEngine.translate(reader, writer, table.getRules());
        String out = writer.message();

        // print the generated csv
        System.out.println(out);
    }

    private static String readFromResource(InputStream stream) throws IOException {

        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);

        // read all input and append to out builder
        int charsRead;
        final StringBuilder out = new StringBuilder();
        while ( (charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }

        // return the string
        return out.toString();
    }

}
