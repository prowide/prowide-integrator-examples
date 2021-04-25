/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.MtType;
import com.prowidesoftware.swift.model.mt.mt3xx.MT300;
import com.prowidesoftware.swift.myformat.*;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.csv.CsvFileReader;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

/**
 * This example shows how to convert a CSV file row into an MT300 using API from Prowide Integrator MyFormat module.
 *
 * <p>The mapping rules in this example are defined programmatically.
 *
 * <p>The example uses a CsvFileReader to iterate the input file lines, and for each CSV row an MT300 instance is
 * created.
 */
public class Csv2MtExample2 {
    public static void main(String[] args) {
        /*
         * programmatic mapping rules
         * notice this rules could also be loaded from Excel spreadsheet or database.
         */
        MappingTable t = new MappingTable(FileFormat.CSV, FileFormat.MT);
        t.add(new MappingRule("0", "20"));
        t.add(new MappingRule("1", "21"));
        t.add(new MappingRule("LITERAL(\"\")", "15B"));
        t.add(new MappingRule("2", "32B/1"));
        t.add(new MappingRule("3", "32B/2", WriteMode.APPEND, new Transformation(Key.replace, ".", ",")));
        t.add(new MappingRule("4", "58A/2", new Transformation(Key.stripStart, "#")));
        t.add(new MappingRule("LITERAL(\"\")", "15C"));
        t.add(new MappingRule("\"/ACC/GTMS:\"", "72/Line[1]"));
        t.add(new MappingRule("5", "72/Line[2]", WriteMode.APPEND, new Transformation(Key.prepend, "//")));

        /*
         * check if the mapping table is valid
         */
        if (t.validate().isEmpty()) {

            /*
             * create the file reader
             */
            CsvFileReader reader = new CsvFileReader(Csv2MtExample2.class.getResourceAsStream("/messages.csv"));

            while (reader.hasNext()) {
                /*
                 * read a row from the CSV file
                 */
                CsvReader line = reader.next();
                System.out.println("processing: " + line);

                /*
                 * translation call
                 */
                MtWriter writer = new MtWriter(MtType.MT300);
                MyFormatEngine.translate(line, writer, t.getRules());

                /*
                 * parse and print content from the created MT:
                 *
                 * Sender's Reference: QCOUCN
                 * Related Reference: NEW
                 * Transaction Amount: CLP3794630000,
                 * Account: /9301011483
                 */
                MT300 mt = (MT300) writer.mt();
                System.out.println("Sender's Reference: " + mt.getField20().getValue());
                System.out.println("Related Reference: " + mt.getField21().getValue());
                System.out.println("Transaction Amount: " + mt.getField32B().get(0).getValue());
                System.out.println("Account: " + mt.getField58A().get(0).getValue());
            }

            reader.close();
        }
    }
}
