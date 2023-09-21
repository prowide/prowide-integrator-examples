/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPacs00800102;
import com.prowidesoftware.swift.model.mx.MxTypePacs;
import com.prowidesoftware.swift.myformat.*;

import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>Basic example with just a few mapping rules, translating a single CSV row into an incomplete MX output.
 */
public class Csv2MxExample1 {

    public static void main(String[] args) {

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Csv2MxExample1.class.getResourceAsStream("/myformat/csv2mx.xls")));

        // Create a mapping table instance with source and target formats
        MappingTable table = loader.load("example1");
        table.setSourceFormat(FileFormat.CSV);
        table.setTargetFormat(FileFormat.MX);

        //indicating the specific MX version to create
        table.add(new MappingRule(MxTypePacs.pacs_008_001_02.mxId().id(), SetupCommand.mxType.name(), WriteMode.SETUP));

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
