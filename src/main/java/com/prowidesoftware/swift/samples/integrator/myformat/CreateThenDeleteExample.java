/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableCSVLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * This example shows the create then delete pattern: a rule writes an element, and a later DELETE rule removes
 * it when a condition the first rule cannot express turns out to be true.
 * <p>
 * Here a JSON payment is mapped into an MT103. The sender to receiver information (field 72) is always mapped,
 * but it must not go out for domestic payments, that is when the debtor and creditor countries are equal. A guard
 * on the 72 rule itself can only look at one value, so the two countries are concatenated into a variable and a
 * DELETE rule placed after the 72 rule removes the field when they match.
 * <p>
 * Rules run in table order, so the DELETE rule must come after the rule creating the field: the delete is
 * resolved against the content of the message being built, never against the source. The table has no
 * {@code seedFromSource}, so it builds a new message from an empty writer.
 * <p>
 * In a spreadsheet, CSV or database table the DELETE rules are plain rows with {@code DELETE} in the mode column
 * and, for an unconditional delete, an empty source cell. The CSV loader is used here to keep the table visible in
 * the code; see {@link ConditionalMappingExample} for more on guards and variables.
 *
 * @since 10.3.18
 */
public class CreateThenDeleteExample {

    public static final String mapping = "Source,Transformation,Target,Mode\n"
            + "\"MT103\",,mtType,SETUP\n"
            + "payment.reference,,20,\n"
            + "\"LITERAL(\"\"CRED\"\")\",,23B,\n"
            + "\"date\",\"formatDateTime(\"\"yyyy-MM-dd\"\",\"\"yyMMdd\"\")\",32A/1,UPDATE\n"
            + "payment.currency,,32A/2,UPDATE\n"
            + "payment.amount,formatMTDecimal(),32A/3,UPDATE\n"
            + "payment.debtor.name,,50K/2,UPDATE\n"
            + "payment.creditor.name,,59/2,UPDATE\n"
            + "\"LITERAL(\"\"SHA\"\")\",,71A,\n"
            + "payment.note,,72,\n"
            // the condition spans two fields: evaluate it into a variable
            + "\"CONCAT(payment.debtor.country,\"\"|\"\",payment.creditor.country)\",,$COUNTRIES,VARIABLE\n"
            // remove the 72 just created when both countries are the same; arguments in a table cell are
            // unescaped as the table loads, so the regex backslashes are doubled in the cell: ^(..)\\|\\1$
            + "$COUNTRIES,\"ifMatches(\"\"^(..)\\\\|\\\\1$\"\")\",72,DELETE\n";

    public static void main(String[] args) {
        MappingTableCSVLoader loader =
                new MappingTableCSVLoader(new ByteArrayInputStream(mapping.getBytes(StandardCharsets.UTF_8)));
        MappingTable table = loader.load("createThenDelete", FileFormat.JSON, FileFormat.MT);

        if (!loader.getProblems().isEmpty()) {
            System.out.println("Problems loading the table: " + loader.getProblems());
            return;
        }
        System.out.println("Validation problems: " + table.validate());

        // cross border payment: the 72 stays
        System.out.println(MyFormatEngine.translate(source("DE"), table));
        System.out.println();
        // domestic payment: the 72 is created and then deleted
        System.out.println(MyFormatEngine.translate(source("FR"), table));

        /*
         * The two messages are identical but for the last field: the cross border one ends with
         *
         * :71A:SHA
         * :72:/BNF/INVOICE 4711
         * -}
         *
         * and the domestic one with
         *
         * :71A:SHA
         * -}
         */
    }

    private static String source(String creditorCountry) {
        return "{\n"
                + "  \"date\": \"2026-10-16\",\n"
                + "  \"payment\": {\n"
                + "    \"reference\": \"REF20261016\",\n"
                + "    \"currency\": \"EUR\",\n"
                + "    \"amount\": \"1000.00\",\n"
                + "    \"debtor\": {\"name\": \"JOHN DOE\", \"country\": \"FR\"},\n"
                + "    \"creditor\": {\"name\": \"JANE SMITH\", \"country\": \"" + creditorCountry + "\"},\n"
                + "    \"note\": \"/BNF/INVOICE 4711\"\n"
                + "  }\n"
                + "}";
    }
}
