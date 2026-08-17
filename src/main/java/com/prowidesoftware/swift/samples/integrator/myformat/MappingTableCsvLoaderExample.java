/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableCSVLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * This example loads a mapping table from CSV content, an alternative to the Excel spreadsheet
 * loader that is convenient to keep the mappings under version control as plain text.
 * <p>
 * The CSV requires the header row {@code Source,Transformation,Target,Mode} and one rule per line,
 * with the same four columns of the spreadsheet layout. Values containing commas or quotes are
 * escaped with double quotes as usual in CSV. The example maps a proprietary JSON into an MT202.
 *
 * @since 10.3.5
 */
public class MappingTableCsvLoaderExample {

    public static final String mapping = "Source,Transformation,Target,Mode\n"
            + "\"MT202\",,mtType,SETUP\n"
            + "reference,,20,\n"
            + "relatedReference,,21,\n"
            + "\"date\",\"formatDateTime(\"\"yyyy-MM-dd\"\",\"\"yyMMdd\"\")\",32A/1,UPDATE\n"
            + "amount.currency,,32A/2,UPDATE\n"
            + "amount.value,formatMTDecimal(),32A/3,UPDATE\n"
            + "note,\"prepend(\"\"/BNF/\"\")\",72/Line[1],\n";

    public static final String sample = "{\n"
            + "  \"reference\": \"REF20260817\",\n"
            + "  \"relatedReference\": \"RELATED01\",\n"
            + "  \"date\": \"2026-08-18\",\n"
            + "  \"amount\": { \"currency\": \"EUR\", \"value\": \"50000.00\" },\n"
            + "  \"note\": \"TRANSFER FOO BANK\"\n"
            + "}";

    public static void main(String[] args) {
        MappingTableCSVLoader loader =
                new MappingTableCSVLoader(new ByteArrayInputStream(mapping.getBytes(StandardCharsets.UTF_8)));

        MappingTable table = loader.load("json2mt202", FileFormat.JSON, FileFormat.MT);

        if (!loader.getProblems().isEmpty()) {
            System.out.println("Problems loading the table: " + loader.getProblems());
            return;
        }
        // validate the loaded rules before using the table
        System.out.println("Validation problems: " + table.validate());

        String fin = MyFormatEngine.translate(sample, table);
        System.out.println(fin);

        // the produced FIN content can be parsed with the model API
        MT202 mt = MT202.parse(fin);
        System.out.println("Amount: " + mt.getField32A().getCurrency() + " "
                + mt.getField32A().getAmount());
    }
}
