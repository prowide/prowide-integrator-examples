package com.prowidesoftware.swift.samples.integrator.myformat.mt940;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftMessageFactory;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

import java.io.IOException;

import java.util.List;

public class Csv2MT940 {

    //
    // sample input
    //
    static String csvFileName      = "/MT940/MT940.csv";
    static String tableFileName    = "/MT940/CsvToMT940.xls";

    public static void main(String[] args) {

        // Create a mapping table instance with source and target formats and load rules from excel file
        MappingTable table = new MappingTable(FileFormat.CSV, FileFormat.MT);
        MappingTable.loadFromSpreadsheet(Csv2MT940.class.getResourceAsStream(tableFileName), "Mapping", table);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        if (problems.size() > 0) {

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
