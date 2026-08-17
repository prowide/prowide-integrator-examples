/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.validator.MtValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;
import java.util.Locale;

/**
 * This example validates an MT message using the {@link MtValidationEngine}, the current entry
 * point for MT validation (the legacy {@code ValidationEngine} wrapper is deprecated).
 * <p>
 * The engine returns a {@link ValidationResult} that besides the list of problems provides an
 * overall valid flag, a JSON serialization, and per problem messages that can be localized (error
 * descriptions are available in English, Spanish, French, German and Italian).
 * <p>
 * The sample MT103 is missing the mandatory field 23B and has a malformed amount in field 32A, so
 * the validation reports structure and field problems.
 *
 * @since 10.2.24
 */
public class MtValidationEngineExample {

    public static final String sample = "{1:F01FOOSGBR0AXXX0000000000}{2:I103FOORECV0XXXXN}{4:\n"
            + ":20:REFERENCE\n"
            + ":32A:260818USD1234A56,\n"
            + ":50K:/12345678\n"
            + "ORDERING CUSTOMER\n"
            + ":59:/98765432\n"
            + "BENEFICIARY CUSTOMER\n"
            + ":71A:OUR\n"
            + "-}";

    public static void main(String[] args) {
        MtValidationEngine engine = new MtValidationEngine();
        try {
            // validating the FIN text directly is preferred over validating a parsed object,
            // since parse errors would be lost in the model
            ValidationResult result = engine.validateMessage(sample);

            System.out.println("valid: " + result.isValid());

            for (ValidationProblem problem : result.getProblems()) {
                System.out.println(problem.getErrorKey() + ": " + problem.getMessage(new Locale("es")));
            }

            System.out.println(result.toJson(Locale.getDefault()));

        } finally {
            engine.dispose();
        }
    }
}
