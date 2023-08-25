/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPacs00800102;
import com.prowidesoftware.swift.model.mx.MxType;
import com.prowidesoftware.swift.model.mx.MxTypePacs;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.List;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>Basic example with just a few mapping rules, translating a single CSV row into an incomplete MX output.
 */
public class Csv2MxExample1 {

    public static void main(String[] args) {
        // Create a mapping table instance with source and target formats, indicating the specific MX version to create
        MappingTable table = new MappingTable(FileFormat.CSV, FileFormat.MX.setMxType(MxTypePacs.pacs_008_001_02));

        // Load mapping rules from Excel
        MappingTable.loadFromSpreadsheet(Xml2MtExample1.class.getResourceAsStream("/myformat/csv2mx.xls"), "example1", table);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // Source message sample
        String input = "ABCDUSXXXXX,FAB2019051402400300005,0037 0039 RUE BOISSIERE,75116,PARIS";

        // Call translation
        String out = MyFormatEngine.translate(input, table);

        // Parse output as MX message
        MxPacs00800102 mx = MxPacs00800102.parse(out);
        System.out.println(mx.message());
    }

}
