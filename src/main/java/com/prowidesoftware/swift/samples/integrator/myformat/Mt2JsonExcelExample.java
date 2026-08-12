/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.util.Objects;

/**
 * Converts an MT message to a JSON document using the MT-to-JSON mapping table defined in
 * {@code /myformat/mt2json.xls}.
 *
 * <p>Each row of the spreadsheet pairs an MT tag (or sub-component, e.g. {@code 32B/2}) with a
 * destination JSON path, optionally applying transformations that normalize the values during
 * the conversion. This is the spreadsheet driven counterpart of {@link Mt2JsonExample1}, which
 * declares the same kind of mapping table programmatically.</p>
 *
 * <p>Requires the Prowide Integrator MyFormat module.</p>
 */
public class Mt2JsonExcelExample {

    public static void main(String[] args) {
        // Mapping definition
        String fin = "{1:F01TESTARZZAXXX0000000000}{2:I740FOOYATSWXXXXN}{3:{108:0000000000000002}}{4:\n"
                + ":20:REFERENCE1234\n"
                + ":40F:URR LATEST VERSION\n"
                + ":31D:191219Singapore\n"
                + ":59:/123412341234123\n"
                + "Joe Doe\n"
                + "10 Street 123 of 9383\n"
                + "1000001 NY\n"
                + ":32B:USD15000,23\n"
                + ":39A:12/15\n"
                + ":39C:Test Text for Narrative\n"
                + "More Lines in Narrative\n"
                + ":41A:HGFDUSXXXXX\n"
                + "BY ACCEPTANCE\n"
                + ":42M:Mixed Payment details\n"
                + "Another line of payment details\n"
                + "Yet another line of test text\n"
                + ":71A:CLM\n"
                + ":71D:Narrative for other charges\n"
                + ":72Z:Sender to receiver Information\n"
                + "More info for receiver\n"
                + "-}";
        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Mt2JsonExcelExample.class.getResourceAsStream("/myformat/mt2json.xls")));
        MappingTable table = loader.load("SIMPLE");

        // Validate mapping rules syntax
        for (String problem : table.validate()) {
            System.out.println(problem);
        }

        String json = MyFormatEngine.translate(fin, table);
        JsonElement jsonElement = JsonParser.parseString(json);
        String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
        System.out.println(formattedJson);
    }
}
