/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert a single CSV file row into an MT103 using API from Prowide Integrator MyFormat module.
 */
public class Csv2MtExample1 {
    public static void main(String[] args) {
        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Xml2MtExample1.class.getResourceAsStream("/myformat/csv2mt.xls")));

        // Create a mapping table instance with source and target formats
        MappingTable table = loader.load("example1");
        table.setSourceFormat(FileFormat.CSV);
        table.setTargetFormat(FileFormat.MT);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // Source message sample
        String source = "04/20/19,CITICATT,EFX-EPPAY,USD,1234.56,Joe Doe,14th Street Dep 87 Long Island PO 10002,Washington,USA,1234567890,Foo Corp LTD,International division NY 202099,,1234555,INV-12323";
        String out = MyFormatEngine.translate(source, table);

        // parse as MT
        MT103 mt = MT103.parse(out);

        // generate GPI UETR
        mt.getSwiftMessage().setUETR();

        // print message content as FIN
        System.out.println(mt.message());
    }
}
