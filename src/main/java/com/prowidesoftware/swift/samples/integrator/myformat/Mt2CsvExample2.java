/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
public class Mt2CsvExample2 {

    public static void main(String[] args) throws IOException {

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Xml2MtExample1.class.getResourceAsStream("/myformat/mt2csv.xls")));

        // Create a mapping table instance with source and target formats
        MappingTable table = loader.load("example 2");
        table.setSourceFormat(FileFormat.MT);
        table.setTargetFormat(FileFormat.CSV);

        // validate mapping table
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        String fin = Lib.readResource("mt940.txt");

        // call translation
        final String csv = MyFormatEngine.translate(fin, table);

        // print the created output
        System.out.println(csv);
        /*
            HR,1234567890,0987654321,21/1,EUR,12283841.0
            TX,Apr-26,423,EUR,125000.0,Inf-229183
            TX,Apr-27,423,EUR,125000.0,Inf-229183
            TX,Apr-28,423,EUR,125000.0,Inf-229183
            TX,Apr-29,423,EUR,125000.0,Inf-229183
            TX,Apr-30,423,EUR,125000.0,Inf-229183
            TX,May-01,423,EUR,125000.0,Inf-229183
            TX,May-02,423,EUR,125000.0,Inf-229183
         */
    }
}
