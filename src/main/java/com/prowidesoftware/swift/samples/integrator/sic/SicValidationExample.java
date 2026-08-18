/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sic;

import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;
import com.prowidesoftware.swift.validator.mx.CashClearingSystemCode;
import com.prowidesoftware.swift.validator.mx.MxValidationEngine;
import com.prowidesoftware.swift.validator.mx.MxValidationEngineFactory;

/**
 * This example validates a Swiss SIC message against the SIC proprietary schemas.
 * <p>
 * The validation engine factory accepts a clearing system code besides the standards: for SIC it
 * resolves the schema registry from the pw-swift-integrator-sic module in the classpath (falling
 * back to the generic ISO engine when the module is absent). The validation is schema based, since
 * SIC restrictions are fully embedded in the SIX published XSDs.
 * <p>
 * The sample reuses the pacs.008 from {@link SicParseExample}, with the clearing system code
 * removed from the settlement information so the SIC schema reports the missing element.
 *
 * @since 10.2.24
 */
public class SicValidationExample {

    public static void main(String[] args) {
        // make the sample invalid for SIC: the schema requires SttlmInf/ClrSys
        String sample = SicParseExample.sample.replace("<ClrSys><Cd>SIC</Cd></ClrSys>\n", "");

        MxValidationEngine engine = MxValidationEngineFactory.createEngine(CashClearingSystemCode.SIC);
        try {
            ValidationResult result = engine.validateMessage(sample);

            System.out.println("valid: " + result.isValid());
            for (ValidationProblem problem : result.getProblems()) {
                System.out.println(problem.getErrorKey() + " at " + problem.getPath() + ": " + problem.getMessage());
            }
        } finally {
            engine.dispose();
        }
    }
}
