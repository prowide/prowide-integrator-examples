package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.List;

/**
 * This example shows how to convert and MT into a CSV using API from Prowide Integrator MyFormat module.
 * <p>
 * The mapping rules in this example are loaded from a spreadsheet.
 * <p>
 * The example takes an MT102 with repetitive content, and produces as result a CSV with different rows
 * for the repetitive information, creating also a row identifier. Data from sequence A will go to a header
 * line in the CSV, the repetitive sequences B will go to multiple transaction rows and finally data from
 * sequence C will go to a final footer row.
 */
public class Mt2CsvExample1 {

    public static void main(String[] args) {
        // create de mapping table instance with source and target formats
        MappingTable table = new MappingTable(FileFormat.MT, FileFormat.CSV);

        // load mapping rules from Excel
        MappingTable.loadFromSpreadsheet(Xml2MtExample1.class.getResourceAsStream("/myformat/mt2csv.xls"), "example 1", table);

        // validate mapping table
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // source message
        final String fin = "{1:F01FOOBVEC0AXXX5480000053}{2:I102FOOBARAAXXXXN}{3:{103:YVE}}{4:\n" +
                // sequence A
                ":20:5362/MPB\n" +
                ":23:CREDIT\n" +
                ":50K:/1234567890\n" +
                "FOOBAR CORP\n" +
                "FRIEDRICHSTRASSE, 234\n" +
                "8022-ZURICH\n" +
                ":71A:OUR\n" +
                ":36:1,6\n" +

                // sequence B
                ":21:ABC/123\n" +
                ":32B:EUR1250,\n" +
                ":59:/001161685134\n" +
                "JOE DOE\n" +
                "RUE JOSEPH II, 123\n" +
                "1040 BRUSSELS\n" +
                ":70:PENSION PAYMENT MAR 2019\n" +
                ":33B:CHF2000,\n" +
                ":71G:EUR5,\n" +

                // sequence B
                ":21:ABC/124\n" +
                ":32B:EUR1875,\n" +
                ":59:/510007547061\n" +
                "JOAN SURNAME\n" +
                "AVENUE LOUISE 456\n" +
                "1050 BRUSSELS\n" +
                ":70:PENSION PAYMENT MAR 2019\n" +
                ":33B:CHF3000,\n" +
                ":71G:EUR5,\n" +

                // sequence C
                ":32A:090828EUR3135,\n" +
                ":19:3125,\n" +
                ":71G:EUR10,\n" +

                "-}";

        // call translation
        final String csv = MyFormatEngine.translate(fin, table);

        // print the created output
        System.out.println(csv);
        /*
            HR,5362/MPB,1234567890,FOOBAR CORP
            TX,123,EUR,1250.0
            TX,124,EUR,1875.0
            FR,EUR,3135.0
         */
    }
}
