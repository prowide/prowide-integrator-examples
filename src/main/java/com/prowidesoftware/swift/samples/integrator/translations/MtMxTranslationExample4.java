/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.translations.PreconditionError;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactory;
import com.prowidesoftware.swift.translations.TranslatorFactoryConfiguration;

import java.io.IOException;
import java.util.List;

/**
 * This example shows how to perform automatic translation from a MT to its correspondent MX
 * using API from Prowide Integrator Translations module. Using the generic interface.
 *
 * <p>In this example the preconditions check is not delegated to the factory, but switch off in the factory and
 * verified manually afterwards. This is useful to propagate any precondition error to the user.
 *
 * @since 7.7
 */
public class MtMxTranslationExample4 {

    public static final String sample = "{1:F01ABCDBEBBD36A6535371188}{2:O5090202140827FOOOLULLCISI56822627991408270202N}{3:{108:OMS0992390016000}}{4:\n" +
            ":16R:GENL\n" +
            ":20C::SEME//LUX0012345\n" +
            ":23G:INST\n" +
            ":98C::PREP//20190827020231\n" +
            ":16R:LINK\n" +
            ":20C::RELA//0045912379625314\n" +
            ":16S:LINK\n" +
            ":16R:STAT\n" +
            ":25D::IPRC//COSE\n" +
            ":16S:STAT\n" +
            ":16S:GENL\n" +
            ":16R:TRADE\n" +
            ":98A::SETT//20190829\n" +
            ":98A::TRAD//20190826\n" +
            ":11A::FXIS//USD\n" +
            ":22H::BUSE//SUBS\n" +
            ":22H::PAYM//APMT\n" +
            ":16R:TRADPRTY\n" +
            ":95Q::BUYR//FBB/FDS/BE/MAIN\n" +
            ":97A::SAFE//1050000642H\n" +
            ":70C::PACO//Settled\n" +
            "Fully paid 1234.00 USD\n" +
            ":16S:TRADPRTY\n" +
            ":36B::ORDR//UNIT/2400,\n" +
            ":35B:ISIN LU123456789\n" +
            "FOO CORP GL.STR.AAAA.MKT.EQ.AC\n" +
            ":16S:TRADE\n" +
            "-}";

    public static void main(String[] args) throws IOException {

        // parse the unknown source message
        final AbstractMT source = AbstractMT.parse(sample);

        // get a translator for the available equivalent MX, with the precondition check in the factory switched off
        Translator<AbstractMT, AbstractMX> t = TranslatorFactory.getTranslator(source, new TranslatorFactoryConfiguration().setEvaluatePreconditions(false));

        // check the translator exist
        if (t != null) {

            // check preconditions
            List<PreconditionError> errors = t.preconditionsCheck(source);

            if (!errors.isEmpty()) {
                for (PreconditionError e : errors) {
                    System.out.println(e);
                }
            } else {
                AbstractMX mx = t.translate(source);
                System.out.println(mx.message());
            }
        }

    }

}
