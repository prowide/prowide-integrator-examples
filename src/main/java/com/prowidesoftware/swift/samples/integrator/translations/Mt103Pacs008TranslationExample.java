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
import com.prowidesoftware.swift.translations.Truncation;
import com.prowidesoftware.swift.utils.Lib;
import java.io.IOException;

/**
 * This example translates a customer credit transfer MT103 into its ISO 20022 equivalent pacs.008,
 * which is the most common translation pair in payments.
 * <p>
 * It uses the {@link TranslatorFactoryProvider}, the recommended entry point to gather translators,
 * selecting the plain ISO 20022 standard. Other constants of {@link TranslatorStandard} or a
 * {@code CashClearingSystemCode} can be used to get factories producing market specific translations
 * (CBPR+, SIC, TARGET2, etc.).
 * <p>
 * After the translation, the truncation report is printed. The report contains an entry for each
 * source content that did not fit in the target message and was truncated (with evidence, meaning
 * the value ends with '+' when truncated).
 *
 * @since 9.3.40
 */
public class Mt103Pacs008TranslationExample {

    public static void main(String[] args) throws IOException {
        AbstractMT source = AbstractMT.parse(Lib.readResource("mt103.txt"));

        GenericTranslatorFactory factory = TranslatorFactoryProvider.getFactory(TranslatorStandard.ISO_20022);

        // let the factory pick the proper translator for the source message
        @SuppressWarnings("unchecked")
        Translator<AbstractMT, AbstractMX> translator = factory.getTranslator(source);

        if (translator == null) {
            System.out.println("No translation available for " + source.getMessageType());
            return;
        }
        System.out.println("Translating " + translator.getMtId() + " -> "
                + translator.getMxId().id());

        AbstractMX mx = translator.translate(source);
        System.out.println(mx.message());

        // print the truncation report (empty when all content fitted in the target message)
        for (Truncation truncation : translator.getTruncatedContent()) {
            System.out.println("Truncated at " + truncation.getTargetPath() + ": original [" + truncation.getOriginal()
                    + "] lost content [" + truncation.getTruncated() + "]");
        }
    }
}
