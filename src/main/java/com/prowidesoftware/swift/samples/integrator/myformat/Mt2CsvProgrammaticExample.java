/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.*;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.csv.CsvFileWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This example shows how to convert and MT into a CSV using API from Prowide Integrator MyFormat module.
 *
 * <p>The mapping rules in this example are defined programmatically.
 *
 * <p>The example takes a couple of MT300 as input, and produces as result an a CSV  with information gathered
 * from the MTs, one CSV row per source MT.
 */
public class Mt2CsvProgrammaticExample {
    /*
     * source message
     */
    static String msg = "{1:F01ABCBUS33AXXX3768156193}{2:O3001139050822XYZBUS33AFXO29569650200508221139N}{3:{108:FC003105ded7970a}}{4:\n" +
            ":15A:\n" +
            ":20:QCOUCN111\n" +
            ":21:NEW111\n" +
            ":22A:CANC\n" +
            ":22C:ABCB334209XYZB33\n" +
            ":82A:XYZBUS33FXO\n" +
            ":87A:ABCBUS33XXX\n" +
            ":77D:/VALD/20040509\n" +
            "/SETC/USD\n" +
            ":15B:\n" +
            ":30T:20070422\n" +
            ":30V:20070513\n" +
            ":36:542,09\n" +
            ":32B:CLP3794630000,\n" +
            ":53A:XYZBUS33FXO\n" +
            ":57D:NET SETTLEMENT\n" +
            ":33B:USD7000000,00\n" +
            ":53A:ABCBUS33XXX\n" +
            ":57D:NET SETTLEMENT\n" +
            ":58A:/9301011483\n" +
            "ABCBUS33XXX\n" +
            ":15C:\n" +
            ":24D:BROK\n" +
            ":88D:GFI-NY\n" +
            ":72:/ACC/GTMS:\n" +
            "//L1710833-1-1\n" +
            "-}";
    /*
     * source message
     */
    static String msg2 = "{1:F01ABCBUS33AXXX3768156193}{2:O3001139050822XYZBUS33AFXO29569650200508221139N}{3:{108:FC003105ded7970a}}{4:\n" +
            ":15A:\n" +
            ":20:QCOUCN222\n" +
            ":21:NEW222\n" +
            ":22A:CANC\n" +
            ":22C:ABCB334209XYZB33\n" +
            ":82A:XYZBUS33FXO\n" +
            ":87A:ABCBUS33XXX\n" +
            ":77D:/VALD/20040509\n" +
            "/SETC/USD\n" +
            ":15B:\n" +
            ":30T:20070422\n" +
            ":30V:20070513\n" +
            ":36:542,09\n" +
            ":32B:CLP1234530000,\n" +
            ":53A:XYZBUS33FXO\n" +
            ":57D:NET SETTLEMENT\n" +
            ":33B:USD7000000,00\n" +
            ":53A:ABCBUS33XXX\n" +
            ":57D:NET SETTLEMENT\n" +
            ":58A:/848473332\n" +
            "ABCBUS33XXX\n" +
            ":15C:\n" +
            ":24D:BROK\n" +
            ":88D:GFI-NY\n" +
            ":72:/ACC/GTMS:\n" +
            "//L1710999-2-2\n" +
            "-}";

    public static void main(String[] args) throws IOException {

        // programmatic mapping rules (this rules could also be loaded from Excel spreadsheet)
        MappingTable t = new MappingTable(FileFormat.MT, FileFormat.CSV);
        t.add(new MappingRule("20", "0"));
        t.add(new MappingRule("21", "1"));
        t.add(new MappingRule("B/32B/1", "2"));
        t.add(new MappingRule("B/32B/2", "3", new Transformation(Key.replace, ",", ".")));
        t.add(new MappingRule("B/58A/2", "4", new Transformation(Key.prepend, "#")));
        t.add(new MappingRule("C/72/Line[2]", "5", new Transformation(Key.stripStart, "/")));

        // create the CSV writer.
        StringWriter out = new StringWriter();
        CsvFileWriter writer = new CsvFileWriter(out);

        // load the source messages
        List<String> messages = new ArrayList<>();
        messages.add(msg);
        messages.add(msg2);

        // iterate source messages calling translation
        for (String msg : messages) {

            // translation call
            final String line = MyFormatEngine.translate(msg, t);

            // append row to writer
            writer.write(line);
        }

        // close the writer
        writer.close();

        /*
         * print output
         * QCOUCN111,NEW111,CLP,3794630000.,#9301011483,L1710833-1-1
         * QCOUCN222,NEW222,CLP,1234530000.,#848473332,L1710999-2-2
         */
        System.out.println(out);
    }
}
