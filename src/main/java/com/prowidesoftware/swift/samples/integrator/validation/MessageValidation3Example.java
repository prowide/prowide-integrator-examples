/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;
import java.util.List;

/**
 * Simple validation example forcing several semantic problems, to see how
 * the semantic validations are detected are reported.
 *
 * <p>
 * Each method below builds one MT103 that breaks a specific rule, so the printout shows which
 * rule fired for which message:
 * <pre>
 * SEMANTIC RULE 2    C02 (71G and 32A currency mismatch) and E15
 * SEMANTIC RULE 150  D50 (71A is SHA, so 71G is not allowed)
 * SEMANTIC RULE 151  D51 (71G present, so 33B is mandatory)
 * SEMANTIC RULE 157  E15 (71A is BEN, so 71F is mandatory) and D57 (71G cannot be zero)
 * SEMANTIC RULE 175  D75 (33B currency differs from 32A, so 36 is mandatory)
 * SEMANTIC RULE 197  T47 (bad codeword in 23E), E02, D97 and E15
 * </pre>
 * </p>
 */
public class MessageValidation3Example {
    public static final String B1 = "{1:F01FOOSGBR0AXXX0000000000}";
    public static final String B2_103 = "{2:I103FOORECV0XXXXN}";
    // the UETR is mandatory in an MT103; without it every case below would also report U13,
    // burying the semantic rule each one is meant to demonstrate
    public static final String B3 = "{3:{121:00000000-0000-4000-8000-000000000004}}";

    public static void main(String[] args) throws Exception {
        final MessageValidation3Example main = new MessageValidation3Example();
        System.out.println("SEMANTIC RULE 2");
        main.showSemantic2();
        System.out.println("SEMANTIC RULE 150");
        main.showSemantic150();
        System.out.println("SEMANTIC RULE 151");
        main.showSemantic151();
        System.out.println("SEMANTIC RULE 157");
        main.showSemantic157();
        System.out.println("SEMANTIC RULE 175");
        main.showSemantic175();
        System.out.println("SEMANTIC RULE 197");
        main.showSemantic197();
    }

    void showSemantic150() throws Exception {
        String mt = B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
                + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
                + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:SHA\n"
                + ":71G:USD10,\n"
                + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";

        SwiftMessage msg = new SwiftParser(mt).message();
        List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
        System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic151() throws Exception {
        String mt = B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
                +
                // ":33B:ARS200,00\n"+
                ":50K:TRUST BANK\n"
                + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:OUR\n"
                + ":71G:USD10,\n"
                + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";

        SwiftMessage msg = new SwiftParser(mt).message();
        List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
        System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic157() throws Exception {
        String mt = B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
                + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
                + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:BEN\n"
                + ":71G:USD0,\n"
                + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";

        SwiftMessage msg = new SwiftParser(mt).message();
        List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
        System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic175() throws Exception {
        String mt = B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
                + ":33B:ARS200,00\n" + ":50K:TRUST BANK\n"
                + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:OUR\n"
                + ":72:/TELE/ IN FAVOUR OF\n"
                + "//A/C R-000000\n-}";

        SwiftMessage msg = new SwiftParser(mt).message();
        List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
        System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic197() throws Exception {
        String mt = B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":23E:FOOO/1234567\n"
                + ":32A:010113USD200,00\n" + ":33B:USD200,00\n"
                + ":50K:TRUST BANK\n" + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n"
                + "JP\n" + ":71A:BEN\n" + ":71G:USD10,\n"
                + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";

        SwiftMessage msg = new SwiftParser(mt).message();
        List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
        System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic2() throws Exception {
        MT103 mt = MT103.parse(B1 + B2_103 + B3 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
                + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
                + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:FOOKUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:BEN\n"
                + ":71G:EUR10,\n"
                + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}");

        List<ValidationProblem> r = new ValidationEngine().validateMessage(mt);
        System.out.println(ValidationProblem.printout(r));
    }
}
