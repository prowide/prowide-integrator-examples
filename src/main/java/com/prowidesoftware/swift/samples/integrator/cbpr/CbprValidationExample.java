/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.cbpr;

import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;
import com.prowidesoftware.swift.validator.mx.MxValidationEngine;
import com.prowidesoftware.swift.validator.mx.MxValidationEngineFactory;
import com.prowidesoftware.swift.validator.mx.ValidationStandard;

/**
 * This example validates a message against the CBPR+ usage guidelines, using the validation engine
 * factory with the CBPR standard. It requires both the validation and the cbpr modules in the
 * classpath: the restricted XSD schemas are resolved from the business service in the header.
 * <p>
 * The CBPR+ validation is stricter than plain ISO 20022: on top of the restricted schema it
 * applies cross-element rules and the CBPR+ textual and formal rules. The compliant sample from
 * {@link CbprParseExample} is validated first; then the instructing agent is changed so it no
 * longer matches the header, showing one of the CBPR+ specific rules in the report.
 *
 * @since 10.2.24
 */
public class CbprValidationExample {

    public static void main(String[] args) {
        MxValidationEngine engine = MxValidationEngineFactory.createEngine(ValidationStandard.CBPR);
        try {
            // a CBPR+ compliant pacs.008
            print("Compliant message", engine.validateMessage(CbprParseExample.sample));

            // break the BAH coherence rule: the instructing agent no longer matches the "From" BIC
            String broken = CbprParseExample.sample.replace(
                    "<InstgAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></InstgAgt>",
                    "<InstgAgt><FinInstnId><BICFI>CCCCGB2LXXX</BICFI></FinInstnId></InstgAgt>");
            print("Instructing agent not matching the header", engine.validateMessage(broken));
        } finally {
            engine.dispose();
        }
    }

    private static void print(String title, ValidationResult result) {
        System.out.println(title + " -> valid: " + result.isValid());
        for (ValidationProblem problem : result.getProblems()) {
            System.out.println("  " + problem.getErrorKey() + " at " + problem.getPath() + ": " + problem.getMessage());
        }
    }
}
