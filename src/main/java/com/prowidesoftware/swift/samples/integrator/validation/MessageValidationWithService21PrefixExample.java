/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.io.IOException;
import java.util.List;

/**
 * When running the validation for an ACK/NACK instead of the actual MT payload, the validation will return and
 * error similar to: "Validation of FIN service message is not supported".
 * <p>
 * Many SWIFT interfaces return the actual MT prefixed with an ACK (service 21 message) so this is a common pitfall when
 * calling our library and passing the ACK+MT instead of just the MT.
 * <p>
 * This example extracts the actual MT from a service 21 message prefix and then runs the validation.
 */
public class MessageValidationWithService21PrefixExample {

    public static void main(String[] args) throws IOException {
        ValidationEngine e = new ValidationEngine();
        e.initialize();

        final String fin = "{1:F21FOOLHKH0AXXX0304009999}{4:{177:1608140809}{451:0}}{1:F01FOOLHKH0AXXX0304009999}{2:O9401609160814FOOLHKH0AXXX03040027341608141609N}{4:\n" +
                ":20:USD940NO1\n" +
                ":21:123456/DEV\n" +
                ":25:USD234567\n" +
                ":28C:1/1\n" +
                ":60F:C160418USD672,\n" +
                ":61:160827C642,S1032\n" +
                ":86:ANDY\n" +
                ":61:160827D42,S1032\n" +
                ":86:BANK CHARGES\n" +
                ":62F:C160418USD1872,\n" +
                ":64:C160418USD1872,\n" +
                "-}{5:{CHK:0FEC1E4AEC53}{TNG:}}{S:{COP:S}}";

        SwiftMessage sm = SwiftMessage.parse(fin);
        if (sm.isServiceMessage()) {
            String mt = sm.getUnparsedTexts().getAsFINString();

            System.out.println("Validating MT: " + mt);
            List<ValidationProblem> p = e.validateMtMessage(mt);

            System.out.println(ValidationProblem.printout(p));
        }
    }
}
