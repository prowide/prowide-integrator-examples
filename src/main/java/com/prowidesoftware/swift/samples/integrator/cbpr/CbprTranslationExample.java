/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.cbpr;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.BusinessAppHdrV02;
import com.prowidesoftware.swift.translations.GenericTranslatorFactory;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactoryProvider;
import com.prowidesoftware.swift.translations.TranslatorStandard;

/**
 * This example translates an MT103 into a CBPR+ compliant pacs.008 and back, using the translator
 * factory with the CBPR standard. It requires the translations and cbpr modules in the classpath.
 * <p>
 * Compared to the plain ISO 20022 translations, the CBPR+ factory produces messages on the CBPR+
 * restricted model, applies the CBPR+ mappings and target versions, and generates the Business
 * Application Header v2 with the corresponding CBPR+ business service.
 *
 * @since 9.5.26
 */
public class CbprTranslationExample {

    public static final String sample = "{1:F01AAAABEBBAXXX0000000000}{2:I103BBBBUS33XXXXN}{3:"
            + "{121:00000000-0000-4000-8000-000000000005}}{4:\n"
            + ":20:REF20260817CBPR\n"
            + ":23B:CRED\n"
            + ":32A:260818USD25000,\n"
            + ":50K:/BE71096123456769\n"
            + "ORDERING CUSTOMER\n"
            + "BRUSSELS\n"
            + ":59:/GB29NWBK60161331926819\n"
            + "BENEFICIARY CUSTOMER\n"
            + "LONDON\n"
            + ":71A:SHA\n"
            + "-}";

    public static void main(String[] args) throws Exception {
        AbstractMT source = AbstractMT.parse(sample);

        // MT to MX with the CBPR+ mappings
        GenericTranslatorFactory factory = TranslatorFactoryProvider.getFactory(TranslatorStandard.CBPR);

        @SuppressWarnings("unchecked")
        Translator<AbstractMT, AbstractMX> toMx = factory.getTranslator(source);
        AbstractMX mx = toMx.translate(source);

        System.out.println("Produced " + mx.getClass().getName());
        System.out.println("Business service: " + ((BusinessAppHdrV02) mx.getAppHdr()).getBizSvc());
        System.out.println(mx.message());

        // and back: MX to MT with the same factory
        @SuppressWarnings("unchecked")
        Translator<AbstractMX, AbstractMT> toMt = factory.getTranslator(mx);
        AbstractMT mt = toMt.translate(mx);
        System.out.println(mt.message());
    }
}
