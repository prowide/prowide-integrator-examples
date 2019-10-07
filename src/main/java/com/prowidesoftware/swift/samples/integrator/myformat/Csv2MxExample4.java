package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.model.mx.MxType;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvFileReader;
import com.prowidesoftware.swift.myformat.mx.MxWriter;

import java.util.List;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>In this example each row in the source CSV is used to produce a single message output; conversion is 1 to n.
 * In this scenario the reader iteration is part of the host application program.
 *
 * <p>The mapping rules also introduce the use of literals as source selectors.
 */
public class Csv2MxExample4 {

    public static void main(String[] args) {
        // Create de mapping table instance with source and target formats
        MappingTable table = new MappingTable(FileFormat.CSV, FileFormat.MX);

        // Load mapping rules from Excel
        MappingTable.loadFromSpreadsheet(Xml2MtExample.class.getResourceAsStream("/myformat/csv2mx.xls"), "example4", table);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // Source message sample
        String input =
            "ABCDUSXXXXX,FOOBARXXXXX,1,71237456,Joe Doe,USD,9505.02\n" +
            "ABCDUSXXXXX,EDFGITXXXXX,1,83736212,Bill Smith,EUR,3325.16";

        // Since we will create many output messages, we need to iterate the source ourselves
        CsvFileReader reader = new CsvFileReader(input);
        while (reader.hasNext()) {

            // A new writer instance must be provided on each iteration
            MxWriter writer = new MxWriter(MxType.pain_001_001_03);
            MyFormatEngine.translate(reader.next(), writer, table.getRules());

            // Each iteration produces a single MX output
            MxPain00100103 mx = (MxPain00100103) writer.mx();
            System.out.println(mx.message("message"));
        }

        reader.close();
    }

}
