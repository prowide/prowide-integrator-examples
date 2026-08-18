/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.translations.GenericTranslatorFactory;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactoryProvider;
import com.prowidesoftware.swift.translations.TranslatorStandard;
import java.io.IOException;
import java.util.Arrays;

/**
 * This example shows how to customize a translation with the two available configuration scopes:
 * <ul>
 *   <li><b>Factory configuration</b>: controls how translators are picked. Here the target version
 *   is pinned with a translation mapping, overriding the default. Wildcards such as
 *   "pacs.008.001.*" are also accepted.</li>
 *   <li><b>Translator configuration</b>: controls the translation behavior. Here structured
 *   addresses generation is disabled and truncation evidence is turned off (truncated content will
 *   not be marked with a trailing '+').</li>
 * </ul>
 *
 * @since 8.0.1
 */
public class TranslatorConfigurationExample {

    public static final String sample = "{1:F01AAAADEFFAXXX0000000000}{2:I103BBBBFRPPXXXXN}{4:\n"
            + ":20:REF20260817CONF\n"
            + ":23B:CRED\n"
            + ":32A:260818EUR7500,\n"
            + ":50K:/DE75512108001245126199\n"
            + "ORDERING CUSTOMER GMBH\n"
            + "MAINZER LANDSTRASSE 11-17\n"
            + "FRANKFURT AM MAIN\n"
            + ":59:/FR7630006000011234567890189\n"
            + "BENEFICIARY SARL\n"
            + "PARIS\n"
            + ":71A:SHA\n"
            + "-}";

    public static void main(String[] args) throws IOException {
        AbstractMT source = AbstractMT.parse(sample);

        GenericTranslatorFactory factory = TranslatorFactoryProvider.getFactory(TranslatorStandard.ISO_20022);

        // pin the target version, otherwise the factory default for MT103 is used
        factory.getConfiguration().withTranslationMappings(Arrays.asList("MT103:pacs.008.001.08"));

        @SuppressWarnings("unchecked")
        Translator<AbstractMT, AbstractMX> translator = factory.getTranslator(source);
        System.out.println("Selected target: " + translator.getMxId().id());

        // customize the translation behavior for this translator instance
        translator.getConf().setUseStructuredAddressesIfPossible(false);
        translator.getConf().setTruncateWithEvidence(false);

        AbstractMX mx = translator.translate(source);
        System.out.println(mx.message());
    }
}
