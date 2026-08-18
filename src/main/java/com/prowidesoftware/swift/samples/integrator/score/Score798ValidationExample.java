/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.score;

import com.prowidesoftware.swift.model.mt.MtTypeScore;
import com.prowidesoftware.swift.validator.MtValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;

/**
 * This example validates a SWIFT for Corporates (SCORE) message against the scheme of its specific
 * sub-message structure.
 * <p>
 * This is the key point when validating SCORE traffic: without the specific scheme only the MT798
 * envelope is validated (fields 20, 12 and 77E), so problems in the sub-message content pass
 * undetected. The {@link MtTypeScore} enum catalogs the available SCORE structures and provides
 * the scheme for each one.
 * <p>
 * The sample is missing the mandatory field 21A of the 763 structure, which is only reported when
 * validating with the specific scheme.
 *
 * @since 10.2.24
 */
public class Score798ValidationExample {

    public static final String sample = "{1:F01AAAADEM0AXXX0000000000}{2:I798BBBBITRRXMCEN}{4:\n"
            + ":20:TRE96372\n"
            + ":12:763\n"
            + ":77E:\n"
            + ":27A:1/2\n"
            + ":20:PGFFA0815\n"
            + ":13E:202608171433\n"
            + "-}";

    public static void main(String[] args) {
        MtValidationEngine engine = new MtValidationEngine();
        try {
            // default validation: only the MT798 envelope structure is checked
            ValidationResult envelopeOnly = engine.validateMessage(sample);
            System.out.println("Envelope only -> valid: " + envelopeOnly.isValid());

            // validation with the specific SCORE scheme for the 763 corporate to bank structure
            ValidationResult withScheme = engine.validateMessage(sample, MtTypeScore.MT798_763_LC_C2B.scheme());
            System.out.println("With SCORE scheme -> valid: " + withScheme.isValid());
            for (ValidationProblem problem : withScheme.getProblems()) {
                System.out.println("  " + problem.getErrorKey() + ": " + problem.getMessage());
            }
        } finally {
            engine.dispose();
        }
    }
}
