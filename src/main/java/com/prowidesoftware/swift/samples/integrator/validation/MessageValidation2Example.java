/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field59A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.util.Calendar;
import java.util.List;

/**
 * Example of SWIFT messages validation.<br>
 * Creating and validating an invalid SWIFT message.
 */
public class MessageValidation2Example {

    public static void main(String[] args) {
        /*
         * Create an invalid message (see MessageCreateExample)
         */
        final MT103 m = new MT103();
        m.setSender("FOOSEDR0AXXX");
        m.setReceiver("FOORECV0XXXX");
        m.addField(new Field20("REFERENCE"));
        m.addField(new Field32A().setDate(Calendar.getInstance()).setCurrency("USD"));
        m.addField(new Field59A().setIdentifierCode("ABCDZZXXXX"));
        System.out.println(m.message());

        /*
         * Create and initialize the validation engine
         */
        ValidationEngine engine = new ValidationEngine();
        engine.initialize();
        List<ValidationProblem> r = engine.validateMessage(m);
        System.out.println(ValidationProblem.printout(r));
    }
}
