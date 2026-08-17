/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.BIC;
import com.prowidesoftware.swift.model.mt.mt1xx.MT101;
import com.prowidesoftware.swift.model.mx.MxPain00100109;
import com.prowidesoftware.swift.translations.MxPain00100109_MT101_BulkTranslation;
import com.prowidesoftware.swift.translations.TranslatorConfiguration;
import java.util.List;

/**
 * This example shows an M to N (bulk) translation, where a single source MX produces several target
 * MT messages: a pain.001 with multiple payment information blocks is translated into one MT101 per
 * block.
 * <p>
 * Bulk translation classes do not implement the {@code Translator} interface (which maps one to
 * one), so they are instantiated directly and are not returned by the translator factories.
 * <p>
 * The pain.001 does not carry sender and receiver BICs in a header, so they are provided in the
 * translator configuration to fill the MT101 headers.
 *
 * @since 9.2.19
 */
public class BulkTranslationExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.09\">\n"
            + "  <CstmrCdtTrfInitn>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>PAY2026081701</MsgId>\n"
            + "      <CreDtTm>2026-08-17T10:00:00+00:00</CreDtTm>\n"
            + "      <NbOfTxs>2</NbOfTxs>\n"
            + "      <InitgPty><Nm>FOO CORPORATE TREASURY</Nm></InitgPty>\n"
            + "    </GrpHdr>\n"
            + "    <PmtInf>\n"
            + "      <PmtInfId>BATCH-EUR-01</PmtInfId>\n"
            + "      <PmtMtd>TRF</PmtMtd>\n"
            + "      <ReqdExctnDt><Dt>2026-08-18</Dt></ReqdExctnDt>\n"
            + "      <Dbtr><Nm>FOO CORPORATE TREASURY</Nm></Dbtr>\n"
            + "      <DbtrAcct><Id><IBAN>BE71096123456769</IBAN></Id></DbtrAcct>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtTrfTxInf>\n"
            + "        <PmtId><EndToEndId>E2E-SUPPLIER-01</EndToEndId></PmtId>\n"
            + "        <Amt><InstdAmt Ccy=\"EUR\">2500.00</InstdAmt></Amt>\n"
            + "        <CdtrAgt><FinInstnId><BICFI>CCCCGB2LXXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "        <Cdtr><Nm>SUPPLIER ONE LTD</Nm></Cdtr>\n"
            + "        <CdtrAcct><Id><IBAN>GB29NWBK60161331926819</IBAN></Id></CdtrAcct>\n"
            + "      </CdtTrfTxInf>\n"
            + "    </PmtInf>\n"
            + "    <PmtInf>\n"
            + "      <PmtInfId>BATCH-USD-02</PmtInfId>\n"
            + "      <PmtMtd>TRF</PmtMtd>\n"
            + "      <ReqdExctnDt><Dt>2026-08-19</Dt></ReqdExctnDt>\n"
            + "      <Dbtr><Nm>FOO CORPORATE TREASURY</Nm></Dbtr>\n"
            + "      <DbtrAcct><Id><IBAN>BE71096123456769</IBAN></Id></DbtrAcct>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtTrfTxInf>\n"
            + "        <PmtId><EndToEndId>E2E-SUPPLIER-02</EndToEndId></PmtId>\n"
            + "        <Amt><InstdAmt Ccy=\"USD\">900.00</InstdAmt></Amt>\n"
            + "        <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "        <Cdtr><Nm>SUPPLIER TWO INC</Nm></Cdtr>\n"
            + "        <CdtrAcct><Id><Othr><Id>987654321</Id></Othr></Id></CdtrAcct>\n"
            + "      </CdtTrfTxInf>\n"
            + "    </PmtInf>\n"
            + "  </CstmrCdtTrfInitn>\n"
            + "</Document>";

    public static void main(String[] args) {
        MxPain00100109 source = MxPain00100109.parse(sample);

        // provide the sender and receiver for the MT101 headers
        TranslatorConfiguration conf =
                new TranslatorConfiguration().setSender(new BIC("FOOCUS33XXX")).setReceiver(new BIC("AAAABEBBXXX"));

        MxPain00100109_MT101_BulkTranslation translation = new MxPain00100109_MT101_BulkTranslation();
        translation.setConf(conf);
        List<MT101> result = translation.translate(source);

        System.out.println("Produced " + result.size() + " MT101 messages");
        for (MT101 mt : result) {
            System.out.println(mt.message());
            System.out.println();
        }
    }
}
