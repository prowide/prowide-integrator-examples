/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.utils.Lib;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.io.IOException;
import java.util.List;

/**
 * Example of SWIFT messages validation.<br>
 * Reading and validating a valid SWIFT message from disk.
 *
 * @since 7.7
 */
public class MessageValidation1Example {

    public static void main(String[] args) throws IOException {
        /*
         * Load a message from file
         */
        String msg = Lib.readResource("mt103.txt");

        /*
         * Create and initialize the validation engine
         */
        ValidationEngine engine = new ValidationEngine();
        engine.initialize();

        /*
         * Run the validation and print results
         */
        List<ValidationProblem> r = engine.validateMtMessage(msg);
        System.out.println(ValidationProblem.printout(r));

        /*
         * Release engine
         */
        engine.dispose();
    }
}
