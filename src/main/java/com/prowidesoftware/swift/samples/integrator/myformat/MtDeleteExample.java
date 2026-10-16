/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.ReadMode;
import com.prowidesoftware.swift.myformat.RuleExecutionLog;
import com.prowidesoftware.swift.myformat.SetupCommand;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.TranslationOptions;
import com.prowidesoftware.swift.myformat.TranslationResult;
import com.prowidesoftware.swift.myformat.WriteMode;

/**
 * This example shows how to remove fields from an MT message with the DELETE write mode.
 * <p>
 * The table enables the {@code seedFromSource} setup command, so the target starts as a copy of the source
 * message and the rules only describe what must not go out. In a DELETE rule the target locates the element to
 * remove, with the same selectors used by UPDATE, and the source with its transformations is an optional guard:
 * <ul>
 *   <li>A blank source deletes unconditionally.</li>
 *   <li>Otherwise the delete runs only when the source pipeline produces a value. A transformation returning
 *   null, such as a failing {@code ifMatches}, skips the rule and the element stays.</li>
 *   <li>A condition combining several fields is first evaluated into a variable, then used as the guard.</li>
 * </ul>
 * Without an index every occurrence is removed ({@code 72}); a numeric index removes one ({@code 71F[2]}).
 * Block 3 and block 5 tags are addressed as {@code b3/108} and {@code b5/MAC}, and a delimited sequence of an
 * MT54x as a whole, for instance {@code E/E1[1]}.
 * <p>
 * The translation runs in verbose mode, so the execution log reports how many elements each DELETE rule
 * removed, and null for the rules whose guard skipped them.
 *
 * @since 10.3.18
 */
public class MtDeleteExample {

    public static final String fin = "{1:F01AAAAUSC0AXXX0000000000}{2:I103BBBBGB2LXXXXN}"
            + "{3:{108:MUR20261016}{121:f8a5e2c4-7f1d-4b8e-9a2c-3d6b1e0f4a7c}}{4:\n"
            + ":20:REF20261016\n"
            + ":13C:/SNDTIME/1200+0100\n"
            + ":23B:CRED\n"
            + ":32A:261016EUR1000,\n"
            + ":50K:/12345678\n"
            + "JOHN DOE\n"
            + ":59:/87654321\n"
            + "JANE SMITH\n"
            + ":71A:SHA\n"
            + ":71F:EUR10,\n"
            + ":71F:EUR5,\n"
            + ":72:/ACC/INSTRUCTION FOR THE\n"
            + "//CREDITOR AGENT\n"
            + "-}";

    public static void main(String[] args) {
        MappingTable table = new MappingTable(FileFormat.MT, FileFormat.MT);
        // the target starts as a copy of the source message
        table.add(new MappingRule(ReadMode.LITERAL, "true", SetupCommand.seedFromSource.name(), WriteMode.SETUP));

        // blank source: unconditional deletes
        table.add(new MappingRule(null, "13C", WriteMode.DELETE));
        table.add(new MappingRule(null, "b3/108", WriteMode.DELETE));

        // a numeric index removes that occurrence only
        table.add(new MappingRule(null, "71F[2]", WriteMode.DELETE));

        // guarded by the field itself: 72 goes only when it carries an /ACC/ line
        table.add(new MappingRule("72", "72", WriteMode.DELETE, new Transformation(Key.ifMatches, "(?s).*/ACC/.*")));

        // guarded by two fields: the remaining 71F goes for CRED payments with shared charges
        table.add(new MappingRule("CONCAT(23B, \"|\", 71A)", "$key", WriteMode.VARIABLE));
        table.add(new MappingRule("$key", "71F", WriteMode.DELETE, new Transformation(Key.ifMatches, "CRED\\|SHA")));

        // a guard that fails: the 23B is not SPAY, so the rule is skipped and the 59 stays
        table.add(new MappingRule("23B", "59", WriteMode.DELETE, new Transformation(Key.ifMatches, "SPAY")));

        // validate the rules before using the table (recommended for user authored tables)
        System.out.println("Validation problems: " + table.validate());

        TranslationResult result = MyFormatEngine.translate(fin, table, new TranslationOptions().verbose(true));
        System.out.println(result.getMessage());
        System.out.println();

        for (RuleExecutionLog log : result.getExecutionLogs()) {
            if (log.getWriteMode() == WriteMode.DELETE) {
                System.out.println("DELETE " + log.getTarget() + " -> deleted " + log.getDeletedCount());
            }
        }

        /*
         * The output keeps everything but the deleted elements:
         *
         * {1:F01AAAAUSC0AXXX0000000000}{2:I103BBBBGB2LXXXXN}{3:{121:f8a5e2c4-7f1d-4b8e-9a2c-3d6b1e0f4a7c}}{4:
         * :20:REF20261016
         * :23B:CRED
         * :32A:261016EUR1000,
         * :50K:/12345678
         * JOHN DOE
         * :59:/87654321
         * JANE SMITH
         * :71A:SHA
         * -}
         *
         * DELETE 13C -> deleted 1
         * DELETE b3/108 -> deleted 1
         * DELETE 71F[2] -> deleted 1
         * DELETE 72 -> deleted 1
         * DELETE 71F -> deleted 1
         * DELETE 59 -> deleted null
         */
    }
}
