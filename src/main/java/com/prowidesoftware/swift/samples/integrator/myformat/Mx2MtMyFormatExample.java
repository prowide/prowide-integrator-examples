/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.MtType;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.WriteMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This example converts an MX message into an MT with an ad-hoc MyFormat mapping, the counterpart
 * of {@link Mt2MxExample}.
 * <p>
 * This is not the SWIFT standard translation (that is what the Translations module provides, with
 * complete mappings and preconditions); MyFormat is the right tool when only a specific subset of
 * elements is needed, when the pair is not covered by the standard translations, or to apply an
 * institution specific mapping.
 *
 * @since 7.8
 */
public class Mx2MtMyFormatExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n"
            + "  <FIToFICstmrCdtTrf>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>REF20260817001</MsgId>\n"
            + "      <CreDtTm>2026-08-17T09:30:00+00:00</CreDtTm>\n"
            + "      <NbOfTxs>1</NbOfTxs>\n"
            + "      <SttlmInf><SttlmMtd>INDA</SttlmMtd></SttlmInf>\n"
            + "    </GrpHdr>\n"
            + "    <CdtTrfTxInf>\n"
            + "      <PmtId><EndToEndId>E2E20260817001</EndToEndId></PmtId>\n"
            + "      <IntrBkSttlmAmt Ccy=\"EUR\">125000.00</IntrBkSttlmAmt>\n"
            + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
            + "      <ChrgBr>SHAR</ChrgBr>\n"
            + "      <Dbtr><Nm>ORDERING CUSTOMER</Nm></Dbtr>\n"
            + "      <DbtrAcct><Id><IBAN>BE71096123456769</IBAN></Id></DbtrAcct>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr><Nm>BENEFICIARY CUSTOMER</Nm></Cdtr>\n"
            + "      <CdtrAcct><Id><Othr><Id>1234567890</Id></Othr></Id></CdtrAcct>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>";

    public static void main(String[] args) {
        AbstractMX source = AbstractMX.parse(sample);

        List<MappingRule> rules = new ArrayList<>();
        rules.add(new MappingRule("/Document/FIToFICstmrCdtTrf/GrpHdr/MsgId", "20"));
        // component targets use UPDATE so the three values land in the same 32A field
        rules.add(new MappingRule(
                "/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmDt",
                "32A/1",
                WriteMode.UPDATE,
                new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyMMdd")));
        rules.add(new MappingRule(
                "/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmAmt/@Ccy", "32A/2", WriteMode.UPDATE));
        rules.add(new MappingRule(
                "/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmAmt",
                "32A/3",
                WriteMode.UPDATE,
                new Transformation(Key.formatMTDecimal)));
        rules.add(new MappingRule("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Dbtr/Nm", "50K/2"));
        rules.add(new MappingRule("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Cdtr/Nm", "59/2"));

        MT103 mt = (MT103) MyFormatEngine.translate(source, MtType.MT103, rules);

        System.out.println(mt.message());
    }
}
