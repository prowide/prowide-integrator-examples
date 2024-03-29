/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat.mt940;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftMessageFactory;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert a single CSV file row into an MT940 using API from Prowide Integrator MyFormat module.
 * THe CSV contains multiple rows with transactions that are added into a single MT940 report output.
 */
public class Csv2MT940 {

    //
    // sample input
    //
    static String csvFileName = "/myformat/MT940/MT940.csv";
    static String tableFileName = "/myformat/MT940/CsvToMT940.xls";

    public static void main(String[] args) {

        // Create a mapping table instance with source and target formats and load rules from excel file
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Csv2MT940.class.getResourceAsStream(tableFileName)));
        MappingTable table = loader.load("Mapping");
        table.setSourceFormat(FileFormat.CSV);
        table.setTargetFormat(FileFormat.MT);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        if (!problems.isEmpty()) {

            System.out.println("ERROR: found " + problems.size() + " problems in the mapping table:");
            for (String s : problems) {
                System.out.println("PROBLEM: " + s);
            }
            return;
        }

        // create the csv reader
        CsvReader reader;
        try {
            reader = CsvReader.builder(Csv2MT940.class.getResourceAsStream(csvFileName)).skipHeaderRows(1).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // create the mt writer prepared for output messages
        SwiftMessage sw = SwiftMessageFactory.toggleDirection(new MT940().getSwiftMessage());
        MtWriter writer = new MtWriter(new MT940(sw));

        // process the csv and generate the mt output
        MyFormatEngine.translate(reader, writer, table.getRules());
        String out = writer.message();

        // parse as MT
        MT940 mt = MT940.parse(out);

        // IF UETR (block 3) is NEEDED => uncomment the next line
        // mt.getSwiftMessage().setUETR();

        // print message content as FIN
        System.out.println(mt.message());
    }
}
