/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat.mt940;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvWriter;
import com.prowidesoftware.swift.myformat.mt.MtReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert and MT940 into a CSV using API from Prowide Integrator MyFormat module.
 * Each transaction in the input report is written into a row in the output CSV
 */
public class MT9402Csv {

    //
    // sample input
    //
    static String mtFileName = "/myformat/MT940/MT940.txt";
    static String tableFileName = "/myformat/MT940/MT940ToCsv.xls";

    public static void main(String[] args) {

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(MT9402Csv.class.getResourceAsStream(tableFileName)));

        // Create a mapping table instance with source and target formats
        MappingTable table = loader.load("Mapping");
        table.setSourceFormat(FileFormat.MT);
        table.setTargetFormat(FileFormat.CSV);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        if (!problems.isEmpty()) {

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
        while ((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }

        // return the string
        return out.toString();
    }

}
