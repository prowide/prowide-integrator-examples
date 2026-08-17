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
import com.prowidesoftware.swift.myformat.RuleExecutionLog;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.TranslationOptions;
import com.prowidesoftware.swift.myformat.TranslationResult;

/**
 * This example is a tour of the transformation functions available in the mapping rules, using a
 * representative function of each group (there are 90 built-in functions in total, see the
 * {@link Key} enum):
 * <ul>
 *   <li><b>Text manipulation</b>: upperCase, abbreviate, replace, prepend (functions can be
 *   chained in a pipeline, the output of one is the input of the next)</li>
 *   <li><b>Dates</b>: formatDateTime between arbitrary patterns</li>
 *   <li><b>Numbers</b>: formatMTDecimal for FIN amounts, divide for arithmetic</li>
 *   <li><b>Conditional logic</b>: map for code lookups, defaultString for missing values, ifElse
 *   on a regex match</li>
 *   <li><b>BIC handling</b>: bic8, bicCountry</li>
 *   <li><b>Value generation</b>: uetr and now, typically applied over a literal source</li>
 * </ul>
 * The translation runs in verbose mode, so besides the output message the per rule execution log
 * is printed: what each rule read, the transformations result and whether it was written. This is
 * the recommended way to debug a mapping table.
 *
 * @since 10.3.8
 */
public class TransformationsShowcaseExample {

    public static final String sample = "{\n"
            + "  \"customer\": { \"name\": \"international trading services ltd\" },\n"
            + "  \"payment\": {\n"
            + "    \"reference\": \"REF-000123\",\n"
            + "    \"date\": \"2026-08-17\",\n"
            + "    \"amount\": \"1250.50\",\n"
            + "    \"currency\": \"EUR\",\n"
            + "    \"bic\": \"AAAABEBBXXX\",\n"
            + "    \"iban\": \"BE71096123456769\"\n"
            + "  }\n"
            + "}";

    public static void main(String[] args) {
        MappingTable table = new MappingTable(FileFormat.JSON, FileFormat.JSON);

        // text manipulation, including a two function pipeline
        table.add(new MappingRule("customer.name", "document.name", new Transformation(Key.upperCase)));
        table.add(new MappingRule("customer.name", "document.shortName", new Transformation(Key.abbreviate, 12)));
        table.add(new MappingRule(
                "payment.reference",
                "document.reference",
                new Transformation(Key.replace, "REF-", ""),
                new Transformation(Key.prepend, "TX")));

        // dates
        table.add(new MappingRule(
                "payment.date", "document.valueDate", new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyMMdd")));

        // numbers
        table.add(new MappingRule("payment.amount", "document.mtAmount", new Transformation(Key.formatMTDecimal)));
        table.add(new MappingRule("payment.amount", "document.half", new Transformation(Key.divide, 2, 2)));

        // conditional logic
        table.add(new MappingRule(
                "payment.currency",
                "document.currencyName",
                new Transformation(Key.map, "EUR", "Euro", "USD", "US Dollar")));
        table.add(new MappingRule(
                "payment.priority", "document.priority", new Transformation(Key.defaultString, "NORM")));
        table.add(new MappingRule(
                "payment.iban",
                "document.scope",
                new Transformation(Key.ifElse, "^BE.*", "domestic", "international")));

        // BIC handling
        table.add(new MappingRule("payment.bic", "document.bic8", new Transformation(Key.bic8)));
        table.add(new MappingRule("payment.bic", "document.bicCountry", new Transformation(Key.bicCountry)));

        // value generation over a literal empty source
        table.add(new MappingRule("LITERAL(\"\")", "document.uetr", new Transformation(Key.uetr)));
        table.add(new MappingRule("LITERAL(\"\")", "document.createdAt", new Transformation(Key.now)));

        // run in verbose mode to get the execution log of each rule
        TranslationResult result = MyFormatEngine.translate(sample, table, new TranslationOptions().verbose(true));

        System.out.println(result.getMessage());
        System.out.println();
        for (RuleExecutionLog log : result.getExecutionLogs()) {
            System.out.println("rule " + log.getRuleIndex() + ": " + log.getSource() + " read ["
                    + log.getSourceResult() + "] wrote [" + log.getTransformationsResult() + "] to "
                    + log.getTarget());
        }
    }
}
