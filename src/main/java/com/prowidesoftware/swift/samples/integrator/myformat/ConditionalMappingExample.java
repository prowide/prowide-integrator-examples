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
 * This example shows how to write conditional mappings in a mapping table, selecting one target
 * field or another depending on a source value; here the charges amount goes to field 71F or 71G
 * depending on the charges code.
 * <p>
 * The mapping table layout is fixed to the four columns Source, Transformation, Target and Mode
 * (in Excel, any extra column is ignored); there is no separate conditions column. Conditions are
 * expressed inside the transformation pipeline instead, combining two behaviors:
 * <ul>
 *   <li>When a transformation returns null the rule is skipped and nothing is written, so
 *   {@code ifMatches(regex)} and {@code ifNotMatches(regex)} act as rule guards. Two rules with
 *   mutually exclusive guards implement an if/else over different target fields.</li>
 *   <li>To condition a rule on a different element than the one being mapped, the deciding value
 *   is first stored in a variable (Mode VARIABLE, target {@code $NAME}) and then prefixed to the
 *   source with CONCAT, checked by the guard, and removed with {@code substringAfter} before
 *   writing.</li>
 * </ul>
 * The exact same rule syntax works in the Excel, CSV and database loaded tables; the CSV loader
 * (available since MyFormat 10.3.5) is used here just to keep the table visible in the code. Note
 * that an Excel sheet with an extra fifth column (for instance one holding comments or a homemade
 * "Condition" header) still loads without error, but that column is inert: it is never evaluated.
 * The same technique applies to any target pair, for instance routing an amount to 32A or 32B.
 *
 * @since 10.3.5
 */
public class ConditionalMappingExample {

    public static final String mapping = "Source,Transformation,Target,Mode\n"
            + "\"MT103\",,mtType,SETUP\n"
            + "payment.reference,,20,\n"
            + "\"LITERAL(\"\"CRED\"\")\",,23B,\n"
            + "\"date\",\"formatDateTime(\"\"yyyy-MM-dd\"\",\"\"yyMMdd\"\")\",32A/1,UPDATE\n"
            + "payment.currency,,32A/2,UPDATE\n"
            + "payment.amount,formatMTDecimal(),32A/3,UPDATE\n"
            // store the deciding value in a variable, then guard each conditional rule with it
            + "payment.chargesCode,,$CHARGES,VARIABLE\n"
            // charges code BEN -> field 71F (sender's charges)
            + "\"CONCAT($CHARGES,\"\"/\"\",payment.currency)\",\"ifMatches(\"\"^BEN\"\");substringAfter(\"\"/\"\")\",71F/1,UPDATE\n"
            + "\"CONCAT($CHARGES,\"\"/\"\",payment.chargesAmount)\",\"ifMatches(\"\"^BEN\"\");substringAfter(\"\"/\"\");formatMTDecimal()\",71F/2,UPDATE\n"
            // charges code OUR -> field 71G (receiver's charges)
            + "\"CONCAT($CHARGES,\"\"/\"\",payment.currency)\",\"ifMatches(\"\"^OUR\"\");substringAfter(\"\"/\"\")\",71G/1,UPDATE\n"
            + "\"CONCAT($CHARGES,\"\"/\"\",payment.chargesAmount)\",\"ifMatches(\"\"^OUR\"\");substringAfter(\"\"/\"\");formatMTDecimal()\",71G/2,UPDATE\n";

    public static void main(String[] args) {
        MappingTableCSVLoader loader =
                new MappingTableCSVLoader(new ByteArrayInputStream(mapping.getBytes(StandardCharsets.UTF_8)));
        MappingTable table = loader.load("conditional", FileFormat.JSON, FileFormat.MT);

        if (!loader.getProblems().isEmpty()) {
            System.out.println("Problems loading the table: " + loader.getProblems());
            return;
        }
        // validate the loaded rules before using the table (recommended for user authored sheets)
        System.out.println("Validation problems: " + table.validate());

        // the same table routes the charges to 71F or 71G depending on the source value
        System.out.println(MyFormatEngine.translate(source("BEN"), table));
        System.out.println();
        System.out.println(MyFormatEngine.translate(source("OUR"), table));

        /*
         * With chargesCode BEN the block 4 contains :71F:EUR10, and with OUR it contains
         * :71G:EUR10, while the rest of the message is identical:
         *
         * :20:REF20260817
         * :23B:CRED
         * :32A:260818EUR50000,
         * :71F:EUR10,
         */
    }

    private static String source(String chargesCode) {
        return "{\n"
                + "  \"date\": \"2026-08-18\",\n"
                + "  \"payment\": {\n"
                + "    \"reference\": \"REF20260817\",\n"
                + "    \"currency\": \"EUR\",\n"
                + "    \"amount\": \"50000.00\",\n"
                + "    \"chargesCode\": \"" + chargesCode + "\",\n"
                + "    \"chargesAmount\": \"10.00\"\n"
                + "  }\n"
                + "}";
    }
}
