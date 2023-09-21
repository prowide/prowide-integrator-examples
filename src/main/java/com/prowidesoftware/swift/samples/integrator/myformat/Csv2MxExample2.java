/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPacs00800102;
import com.prowidesoftware.swift.model.mx.MxTypePacs;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.csv.CsvFieldsDef;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.mx.MxWriter;

import java.util.List;
import java.util.Objects;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>Basic example with just a few mapping rules, translating a single CSV row into an incomplete MX output, with a
 * field definition configuration to use labels in the mapping rules selectors. This example also uses a custom
 * split separator for the columns in the CSV row
 */
public class Csv2MxExample2 {

    public static void main(String[] args) {

        // Create the field definitions configuration
        // This maps CSV indexes with custom labels, then the mapping rules can use the labels instead of the indexes
        CsvFieldsDef defs = new CsvFieldsDef()
                .addField("BIC", "0")
                .addField("IBAN", "1")
                .addField("ADDR1", "2")
                .addField("ADDR2", "3")
                .addField("ADDR3", "4");

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Csv2MxExample2.class.getResourceAsStream("/myformat/csv2mx.xls")));

        // Create a mapping table instance with source and target formats
        // There is no need to indicate the MX version because we will provide a specific writer to the translation call
        MappingTable table = loader.load("example2");
        table.setSourceFormat(FileFormat.CSV);
        table.setTargetFormat(FileFormat.MX);

        // Validate mapping rules syntax
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        // Source message sample
        String input = "ABCDUSXXXXX|FAB2019051402400300005|0037 0039 RUE BOISSIERE|75116|PARIS";

        // We create a reader for the input, providing the fields definition, and also customizing the separator
        CsvReader reader = CsvReader.builder(input).separator('|').fieldsDef(defs).build();

        // Create a specific writer and call translation providing the reader and writer instances
        MxWriter writer = new MxWriter(MxTypePacs.pacs_008_001_02);
        MyFormatEngine.translate(reader, writer, table.getRules());

        // Get the result directly from the writer
        MxPacs00800102 mx = (MxPacs00800102) writer.mx();
        System.out.println(mx.message());
    }

}
