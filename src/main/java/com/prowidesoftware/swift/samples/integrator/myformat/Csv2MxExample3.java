/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.model.mx.MxTypePain;
import com.prowidesoftware.swift.myformat.*;

import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>In this example each row in the source CSV is used to produce part of a repetitive element in the message output;
 * conversion is 1 to 1. In this scenario the source selectors uses a FOREACH function.
 *
 * <p>The mapping rules also include a couple of simple transformations. Many transformation functions can be combined
 * in a single rule. For a complete list of the available transformation functions check the developer guide.</p>
 */
public class Csv2MxExample3 {

    public static void main(String[] args) {

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Xml2MtExample1.class.getResourceAsStream("/myformat/csv2mx.xls")));

        // Create a mapping table instance with source and target formats
        // There is no need to indicate the MX version because we will provide a specific writer to the translation call
        MappingTable table = loader.load("example3");
        table.setSourceFormat(FileFormat.CSV);
        table.setTargetFormat(FileFormat.MX);

        //indicating the specific MX version to create
        table.add(new MappingRule(MxTypePain.pain_001_001_03.mxId().id(), SetupCommand.mxType.name(), WriteMode.SETUP));

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // Source message sample
        String input =
                "71237456,EUR,18505.40,ABCDUSXXXXX,AA763000301100000232232333,Joe Doe,PAYMENT OF INVOICE,OUR,123457,1,SBI,Chennai,Navallur,Ags,123,BICFOOYYYYY,55555,Remittance Info,2019-03-25,2000\n" +
                        "71237457,USD,23000.99,FOOBARXXXXX,BB763000301100000203453444,Bill Smith,GOODS DELIVERY,BTH,123456,2,PNB,Batanagar,Kolkata,Sreekanan,FT1,BICFOOXXXXX,333333,Remittance Info1,2019-07-18,7000";

        String out = MyFormatEngine.translate(input, table);
        MxPain00100103 mx = MxPain00100103.parse(out);

        // The output message contains one PmtInf element per row in the source CSV
        System.out.println(mx.message());
    }

}
