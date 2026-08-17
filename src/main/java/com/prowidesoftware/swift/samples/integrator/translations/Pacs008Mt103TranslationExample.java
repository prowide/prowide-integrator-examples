/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.translations.AbstractMXTranslation;
import com.prowidesoftware.swift.translations.GenericTranslatorFactory;
import com.prowidesoftware.swift.translations.MxCoverageReport;
import com.prowidesoftware.swift.translations.MxCoverageReportElement;
import com.prowidesoftware.swift.translations.Translator;
import com.prowidesoftware.swift.translations.TranslatorFactoryProvider;
import com.prowidesoftware.swift.translations.TranslatorStandard;
import com.prowidesoftware.swift.translations.Truncation;

/**
 * This example translates an ISO 20022 pacs.008 into its MT equivalent MT103, showing the two
 * reports available in the MX to MT direction:
 * <ul>
 *   <li><b>Truncation report</b>: MX elements are usually longer than the MT field components, so
 *   content may be truncated. Truncated values end with '+' as evidence by default.</li>
 *   <li><b>Coverage report</b>: lists which elements of the source MX were actually mapped into the
 *   target MT and which were dropped, useful for compliance checks on the translation.</li>
 * </ul>
 * The instruction identification in the sample is longer than the 16 characters available in
 * field 20 on purpose, to produce a truncation.
 *
 * @since 9.3.40
 */
public class Pacs008Mt103TranslationExample {

    public static final String sample = "<RequestPayload>\n"
            + "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n"
            + "    <Fr><FIId><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></FIId></Fr>\n"
            + "    <To><FIId><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></FIId></To>\n"
            + "    <BizMsgIdr>REF20260817001</BizMsgIdr>\n"
            + "    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>\n"
            + "    <CreDt>2026-08-17T09:30:00+00:00</CreDt>\n"
            + "</AppHdr>\n"
            + "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n"
            + "  <FIToFICstmrCdtTrf>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>REF20260817001</MsgId>\n"
            + "      <CreDtTm>2026-08-17T09:30:00+00:00</CreDtTm>\n"
            + "      <NbOfTxs>1</NbOfTxs>\n"
            + "      <SttlmInf><SttlmMtd>INDA</SttlmMtd></SttlmInf>\n"
            + "    </GrpHdr>\n"
            + "    <CdtTrfTxInf>\n"
            + "      <PmtId>\n"
            + "        <InstrId>INSTRUCTIONID20260817001</InstrId>\n"
            + "        <EndToEndId>E2E20260817001</EndToEndId>\n"
            + "        <UETR>00000000-0000-4000-8000-000000000002</UETR>\n"
            + "      </PmtId>\n"
            + "      <IntrBkSttlmAmt Ccy=\"EUR\">125000.00</IntrBkSttlmAmt>\n"
            + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
            + "      <ChrgBr>SHAR</ChrgBr>\n"
            + "      <Dbtr>\n"
            + "        <Nm>INTERNATIONAL COMMODITY TRADING AND LOGISTICS CORPORATION LIMITED</Nm>\n"
            + "        <PstlAdr><TwnNm>BRUSSELS</TwnNm><Ctry>BE</Ctry></PstlAdr>\n"
            + "      </Dbtr>\n"
            + "      <DbtrAcct><Id><IBAN>BE71096123456769</IBAN></Id></DbtrAcct>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr>\n"
            + "        <Nm>FOO IMPORTS INC</Nm>\n"
            + "        <PstlAdr><TwnNm>NEW YORK</TwnNm><Ctry>US</Ctry></PstlAdr>\n"
            + "      </Cdtr>\n"
            + "      <CdtrAcct><Id><Othr><Id>1234567890</Id></Othr></Id></CdtrAcct>\n"
            + "      <RmtInf><Ustrd>INVOICE 2026-0554 GOODS DELIVERY JULY</Ustrd></RmtInf>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>\n"
            + "</RequestPayload>";

    public static void main(String[] args) {
        AbstractMX source = AbstractMX.parse(sample);

        GenericTranslatorFactory factory = TranslatorFactoryProvider.getFactory(TranslatorStandard.ISO_20022);

        @SuppressWarnings("unchecked")
        Translator<AbstractMX, AbstractMT> translator = factory.getTranslator(source);

        if (translator == null) {
            System.out.println(
                    "No translation available for " + source.getMxId().id());
            return;
        }

        AbstractMT mt = translator.translate(source);
        System.out.println(mt.message());

        for (Truncation truncation : translator.getTruncatedContent()) {
            System.out.println("Truncated at " + truncation.getTargetPath() + ": original [" + truncation.getOriginal()
                    + "] lost content [" + truncation.getTruncated() + "]");
        }

        // print the coverage report: which source elements were mapped and which were not
        MxCoverageReport report = ((AbstractMXTranslation) translator).getCoverageReport();
        System.out.println("Fully covered: " + report.isCovered());
        for (MxCoverageReportElement element : report.getDocumentNotCovered()) {
            System.out.println("Not mapped: " + element.getPath() + " = " + element.getValue());
        }
    }
}
