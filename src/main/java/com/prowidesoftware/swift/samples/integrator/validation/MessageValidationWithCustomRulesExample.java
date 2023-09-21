/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationRule;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of SWIFT message validation with custom rules.
 *
 * <p>
 * Custom rules can be injected in the validation and will be evaluated after all standard rules.
 * Custom rules are implementing in your own code as normal Java classes, must extend from ValidationRule
 * and must override the eva(SwiftMessage, String) method to perform any check on the message without limitations.
 * </p>
 *
 * <p>
 * This example uses two custom rules. One very simple, implemented here as inner class. And another
 * more elaborated implemented in an external class @see {@link CustomAmountValidationRule}
 * </p>
 *
 * <p>
 * This example will produce the following output:
 * <pre>
 * MALFORMED MESSAGE, 2 VALIDATION PROBLEMS FOUND.
 * 1/2: BAD_REFERENCE tag index 0 --> the reference must start with MYREF
 * 2/2: AMOUNT_LIMIT_EXCEEDED --> Maximum amount is 1000 and found 100000 in field 32A
 * </pre>
 * </p>
 *
 * @since 7.8.4
 */
public class MessageValidationWithCustomRulesExample {

    public static void main(String[] args) {

        MT103 mt = MT103.parse("{1:F01BICFOOY0AXXX8683499999}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{4:\n"
                + ":20:0061350113089908\n"
                + ":13C:/RNCTIME/1534+0000\n"
                + ":23B:CRED\n"
                + ":23E:SDVA\n"
                + ":32A:061028EUR100000,\n"
                + ":33B:EUR100000,\n"
                + ":50K:/12345678\n"
                + "AGENTES DE BOLSA FOO AGENCIA\n"
                + "AV XXXXX 123 BIS 9 PL\n"
                + "12345 BARCELONA\n"
                + ":52A:/2337\n"
                + "FOOAESMMXXX\n"
                + ":53A:FOOAESMMXXX\n"
                + ":57A:BICFOOYYXXX\n"
                + ":59:/ES0123456789012345671234\n"
                + "FOO AGENTES DE BOLSA ASOC\n"
                + ":71A:OUR\n"
                + ":72:/BNF/TRANSF. BCO. FOO\n"
                + "-}");

        /*
         * Create and initialize the validation engine
         */
        ValidationEngine engine = new ValidationEngine();
        engine.initialize();

        /*
         * Add my custom validation rule to the engine configuration,
         * mapped to message type 940
         */
        engine.getConfig().addCustomRule("103", new MyRule());
        engine.getConfig().addCustomRule("103", new CustomAmountValidationRule(new BigDecimal("1000")));

        /*
         * Run the validation and print results
         */
        List<ValidationProblem> r = engine.validateMessage(mt);
        System.out.println(ValidationProblem.printout(r));

        /*
         * Release engine
         */
        engine.dispose();
    }

    /*
     * Very simple rule to validate the message reference starts with fixed prefix
     */
    public static class MyRule extends ValidationRule {
        @Override
        protected List<ValidationProblem> eval(SwiftMessage msg, String mt) {
            List<ValidationProblem> result = new ArrayList<>();
            if (msg.getBlock4() != null) {
                Tag reference = msg.getBlock4().getTagByName("20");
                if (reference == null || !StringUtils.startsWith(reference.getValue(), "MYREF")) {
                    result.add(new ValidationProblem("BAD_REFERENCE", "the reference must start with MYREF"));
                }
            }
            return result;
        }
    }
}
