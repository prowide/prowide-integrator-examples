/*
 * Copyright (c) 2023 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.List;
import java.util.Objects;

public class Mt2JsonExample2 {

    public static void main(String[] args) {
        // Mapping definition
        String fin = "{1:F01TESTARZZAXXX1204000001}{2:I740FOOYATSWXXXXN}{3:{108:1912041035060802}}{4:\n"
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
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Mt2JsonExample2.class.getResourceAsStream("/myformat/mt2json.xls")));
        List<MappingTable> tables = loader.load();
        assert(tables.size() == 1);
        String json = MyFormatEngine.translate(fin, tables.get(0));

        System.out.println(json);
    }
}
