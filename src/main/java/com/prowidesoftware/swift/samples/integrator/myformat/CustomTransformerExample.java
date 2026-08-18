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
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.TransformationRegistry;
import com.prowidesoftware.swift.myformat.Transformer;
import java.util.HashMap;
import java.util.Map;

/**
 * This example implements a custom transformation function, extending the 90 built-in ones with
 * institution specific logic; here a lookup that resolves internal branch codes into BICs.
 * <p>
 * The {@link Transformer} implementation can be used in two ways:
 * <ul>
 *   <li>Passed directly when creating a {@link Transformation} in a programmatic rule, as done
 *   below.</li>
 *   <li>Registered by name in the {@link TransformationRegistry}, so it can be referenced from
 *   mapping tables loaded from Excel, CSV or database, writing for instance
 *   {@code branchToBic()} in the transformation column.</li>
 * </ul>
 * By contract, a transformer should return the source unchanged when a processing step fails, or
 * null when a lookup fails (a null skips the rule, so nothing is written).
 *
 * @since 7.10.8
 */
public class CustomTransformerExample {

    public static class BranchToBicTransformer implements Transformer {
        private static final Map<String, String> BRANCHES = new HashMap<>();

        static {
            BRANCHES.put("BR-001", "AAAABEBBXXX");
            BRANCHES.put("BR-002", "BBBBUS33XXX");
        }

        @Override
        public String getTransformerName() {
            return "branchToBic";
        }

        @Override
        public String transform(String value, Object[] args) {
            // lookup contract: return null when not found, so the rule is skipped
            return BRANCHES.get(value);
        }

        @Override
        public boolean isValid(Object[] args) {
            // this transformer takes no arguments
            return args == null || args.length == 0;
        }
    }

    public static final String sample = "{ \"payment\": { \"branch\": \"BR-001\", \"reference\": \"REF1\" } }";

    public static void main(String[] args) {
        // register the transformer by name, for mapping tables loaded from Excel, CSV or database
        TransformationRegistry.register("branchToBic", new BranchToBicTransformer());
        System.out.println("registered: " + TransformationRegistry.isRegistered("branchToBic"));

        // and use it directly in a programmatic rule
        MappingTable table = new MappingTable(FileFormat.JSON, FileFormat.JSON);
        table.add(new MappingRule("payment.reference", "document.reference"));
        table.add(new MappingRule(
                "payment.branch", "document.agentBic", new Transformation(new BranchToBicTransformer())));

        System.out.println(MyFormatEngine.translate(sample, table));
    }
}
