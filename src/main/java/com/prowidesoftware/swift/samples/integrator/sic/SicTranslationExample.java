/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sic;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.translations.CashClearingSystemCode;
import com.prowidesoftware.swift.translations.GenericTranslatorFactory;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactoryProvider;

/**
 * This example translates an MT103 into a Swiss SIC pacs.008, using the translator factory with
 * the SIC clearing system code. It requires the translations and sic modules in the classpath.
 * <p>
 * The SIC factory produces messages on the SIC restricted model with the SIX proprietary
 * namespaces, applying the SIC specific mappings. The supported SIC pairs are MT103 and pacs.008,
 * MT202/MT202COV and pacs.009, and pacs.004 to MT103 for returns.
 *
 * @since 9.5.26
 */
public class SicTranslationExample {

    public static final String sample = "{1:F01AAAACHZZAXXX0000000000}{2:I103BBBBCHZZXXXXN}{3:"
            + "{121:00000000-0000-4000-8000-000000000007}}{4:\n"
            + ":20:REF20260817SIC\n"
            + ":23B:CRED\n"
            + ":32A:260818CHF75000,\n"
            + ":50K:/CH4089999001234567890\n"
            + "ORDERING CUSTOMER\n"
            + "ZURICH\n"
            + ":59:/CH5604835012345678009\n"
            + "BENEFICIARY CUSTOMER\n"
            + "GENEVA\n"
            + ":71A:SHA\n"
            + "-}";

    public static void main(String[] args) throws Exception {
        AbstractMT source = AbstractMT.parse(sample);

        GenericTranslatorFactory factory = TranslatorFactoryProvider.getFactory(CashClearingSystemCode.SIC);

        @SuppressWarnings("unchecked")
        Translator<AbstractMT, AbstractMX> translator = factory.getTranslator(source);

        AbstractMX mx = translator.translate(source);

        System.out.println("Produced " + mx.getClass().getName());
        System.out.println("Namespace: " + mx.getNamespace());
        System.out.println(mx.message());
    }
}
