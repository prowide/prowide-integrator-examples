/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field103;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.translations.CashClearingSystemCode;
import com.prowidesoftware.swift.translations.GenericTranslatorFactory;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactoryProvider;
import com.prowidesoftware.swift.translations.TranslatorStandard;
import java.io.IOException;
import org.apache.commons.lang3.EnumUtils;

/**
 * This example shows the dispatcher pattern for market specific translations: the FIN-Copy service
 * code in field 103 of block 3 is used to pick the translator factory of the corresponding clearing
 * system, falling back to plain ISO 20022 when the code is absent or unknown.
 * <p>
 * The sample MT103 carries service code TGT, so the message is translated with the TARGET2 (T2)
 * mappings. The {@code CashClearingSystemCode} enum lists all the supported clearing systems; each
 * factory applies the mappings, target versions, business services and header requirements of its
 * market infrastructure.
 *
 * @since 9.5.26
 */
public class ClearingSystemTranslationExample {

    public static final String sample = "{1:F01AAAADEFFAXXX0000000000}{2:I103BBBBFRPPXXXXN}{3:{103:TGT}"
            + "{121:00000000-0000-4000-8000-000000000003}}{4:\n"
            + ":20:REF20260817T2\n"
            + ":23B:CRED\n"
            + ":32A:260818EUR50000,\n"
            + ":50K:/DE75512108001245126199\n"
            + "ORDERING CUSTOMER GMBH\n"
            + "FRANKFURT\n"
            + ":59:/FR7630006000011234567890189\n"
            + "BENEFICIARY SARL\n"
            + "PARIS\n"
            + ":71A:SHA\n"
            + "-}";

    public static void main(String[] args) throws IOException {
        AbstractMT source = AbstractMT.parse(sample);

        // read the FIN-Copy service code from field 103 in block 3
        GenericTranslatorFactory factory = null;
        SwiftBlock3 block3 = source.getSwiftMessage().getBlock3();
        if (block3 != null) {
            Tag serviceCode = block3.getTagByName(Field103.NAME);
            if (serviceCode != null && EnumUtils.isValidEnum(CashClearingSystemCode.class, serviceCode.getValue())) {
                CashClearingSystemCode code = CashClearingSystemCode.valueOf(serviceCode.getValue());
                System.out.println("Dispatching to clearing system: " + code);
                factory = TranslatorFactoryProvider.getFactory(code);
            }
        }
        if (factory == null) {
            System.out.println("No clearing service code, using plain ISO 20022");
            factory = TranslatorFactoryProvider.getFactory(TranslatorStandard.ISO_20022);
        }

        @SuppressWarnings("unchecked")
        Translator<AbstractMT, AbstractMX> translator = factory.getTranslator(source);

        AbstractMX mx = translator.translate(source);
        System.out.println(mx.message());
    }
}
