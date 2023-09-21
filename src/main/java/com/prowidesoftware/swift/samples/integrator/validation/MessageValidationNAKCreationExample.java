/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.SwiftMessageFactory;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field59A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.util.Calendar;
import java.util.List;

/**
 * Example of how to return the validation result as an ACK/NAK message.
 * <p>
 * This program generates the following output
 * <pre>
 *  {1:F21FOOSEDR0AXXX0000000000}{4:{177:2205051127}{451:1}{405:H10}{108:REFERENCE}}{1:F01FOOSEDR0AXXX0000000000}{2:I103FOORECV0XXXXN}{3:{121:3cd7a573-1743-4d84-8b62-d4265e0c98c3}}{4:
 * :20:REFERENCE
 * :32A:220505USD
 * :59A:ABCDZZXXXX
 * -}
 * </pre>
 */
public class MessageValidationNAKCreationExample {

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
        //System.out.println(m.message());

        /*
         * Create and initialize the validation engine
         */
        ValidationEngine engine = new ValidationEngine();
        engine.initialize();
        List<ValidationProblem> r = engine.validateMessage(m);

        if (r.isEmpty()) {
            /*
             * If message is valid we could also generate the ACK
             */
            String ack = SwiftMessageFactory.ACK(m.getSwiftMessage(), SwiftMessageFactory.Type.ACK, null, SwiftMessageFactory.PositionOptions.ack_then_message);
            System.out.println(ack);
        } else {
            /*
             * The result is a list that could contain many errors, however the NAK supports reporting just one
             */
            String nak = SwiftMessageFactory.ACK(m.getSwiftMessage(), SwiftMessageFactory.Type.NACK, r.get(0).getErrorKey(), SwiftMessageFactory.PositionOptions.ack_then_message);
            System.out.println(nak);
        }
    }
}
