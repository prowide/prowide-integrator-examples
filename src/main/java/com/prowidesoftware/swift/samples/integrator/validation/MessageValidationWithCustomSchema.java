/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.scheme.Scheme;
import com.prowidesoftware.swift.scheme.SchemeXmlReader;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Example of SWIFT message validation with custom scheme.
 *
 * <p>Schemes for MT ar a proprietary and unique feature of Prowide integrator. A scheme defines the structure of any
 * MT message and it is used by the library to validate the message structure. The scheme is defined in XML or Java.
 *
 * <p>This example uses a custom XML scheme to run the validation. This kind of customization is specially suited for
 * example to define additional validation constraint onf top of the standard validations, as required for example
 * by a central bank in the implementation of a RGTS. This constraint may involve changing and optional field to be
 * mandatory, prohibiting some optional fields, or reducing the available letter options in a field.
 */
public class MessageValidationWithCustomSchema {

    public static void main(String[] args) throws IOException {

        String customScheme = "<scheme name=\"199\" description=\"Free Format Message\" release=\"2019\">\n" +
                "   <sequence minRepetitions=\"1\" maxRepetitions=\"1\">\n" +
                "      <field id=\"20\" minRepetitions=\"1\" maxRepetitions=\"1\"/>\n" +
                "      <field id=\"21\" minRepetitions=\"0\" maxRepetitions=\"1\"/>\n" +
                "      <field id=\"79\" minRepetitions=\"1\" maxRepetitions=\"1\" rules=\"3\"/>\n" +
                "      <field id=\"50\" minRepetitions=\"1\" maxRepetitions=\"1\" letterOptions=\"A\"/>\n" +
                "   </sequence>\n" +
                "</scheme>";
        Scheme scheme = new SchemeXmlReader().read(new ByteArrayInputStream(customScheme.getBytes()));

        String message = "{1:F01BICFARY0AXXX8683499999}{2:O1991535051028ESPBESMMAXXX54237522470510281535N}{4:\n"
                + ":20:0061350113089908\n"
                + ":21:1534+0000\n"
                + ":79:FOO\n"
                + ":50A:FOOAESMMXXX\n"
                + "-}";

        /*
         * Create and initialize the validation engine
         */
        ValidationEngine engine = new ValidationEngine();
        engine.initialize();

        /*
         * Call the validation using a custom schema
         */
        List<ValidationProblem> r = engine.validateMtMessage(message, scheme);
        System.out.println(ValidationProblem.printout(r));

        /*
         * Release engine
         */
        engine.dispose();
    }

}
