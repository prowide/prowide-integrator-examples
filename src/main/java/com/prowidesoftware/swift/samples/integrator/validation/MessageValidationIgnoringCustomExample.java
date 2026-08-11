/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.validator.*;
import java.util.List;

/**
 * Example of SWIFT message validation ignoring specific errors.
 *
 * <p>
 * The example will mark to ignore several kind of validation rules, including
 * general, structure, field, and semantic checks.
 * In a real use case scenario this is use to filter just a few specific constraints
 * depending on the nature of the messages being validation and the needs for some
 * flexibility over the default standard constraints.
 * </p>
 *
 * @since 7.7
 */
public class MessageValidationIgnoringCustomExample {

    public static void main(String[] args) {
        /*
         * Load a message
         */
        String msg =
                "{1:X01FOOXGBY0AXXX0000000000}{2:O1031535051028FOOOESMMAXXX00000000000510281535N}{3:{113:ROMF}{108:0000000000000001}{119:STP}{121:00000000-0000-4000-8000-000000000001}}{4:\n"
                        + ":20:0000000000000011\n"
                        + ":13C:/RNCTIME/1534+0000\n"
                        + ":23B:FOO\n"
                        + ":23E:SDVA\n"
                        + ":32A:061028FOO100000,\n"
                        + ":33B:EUR100000,\n"
                        + ":50K:/12345678\n"
                        + "AGENTES DE BOLSA FOO AGENCIA\n"
                        + "AV XXXXX 123 BIS 9 PL\n"
                        + "12345 BARCELONA\n"
                        + ":52A:/2337\n"
                        + "FOOAESMMXXX\n"
                        + ":53A:FOOAESMMXXXAAAAA\n"
                        + ":57A:FOOXGBYYXXX\n"
                        + ":59:/ES0123456789012345671234\n"
                        + "FOO AGENTES DE BOLSA ASOC\n"
                        + ":71A:OUR\n"
                        + ":72:/BNF/TRANSF. BCO. FOO\n"
                        + "-}{5:{MAC:22222222}{CHK:333333333333}}foo";

        /*
         * Create the validation engine
         */
        ValidationEngine engine = new ValidationEngine();

        /*
         *  Several validation problems are reported by default when
         *  all standard rules are applied to the message
         */
        List<ValidationProblem> r = engine.validateMtMessage(msg);
        System.out.println("Results without ignore configuration");
        System.out.println(ValidationProblem.printout(r));

        /*
         * Configuration is set to ignore the reported errors.
         * As result of this configuration, the message will be reported to be valid.
         */
        engine.getConfig().addIgnoredValidationProblem(StructureProblem.P16);
        engine.getConfig().addIgnoredValidationProblem(StructureProblem.H02);
        engine.getConfig().addIgnoredValidationProblem(StructureProblem.H10);
        engine.getConfig().addIgnoredValidationProblem(StructureProblem.KNN);
        engine.getConfig().addIgnoredValidationProblem(FieldProblem.T34);
        engine.getConfig().addIgnoredValidationProblem(FieldProblem.T52);
        engine.getConfig().addIgnoredValidationProblem(FieldProblem.T27);
        engine.getConfig().addIgnoredValidationProblem(SemanticProblem.D75);
        r = engine.validateMtMessage(msg);
        System.out.println("Results after ignore configuration");
        System.out.println(ValidationProblem.printout(r));
    }
}
