/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.model.mx.MxCamt05300108;
import com.prowidesoftware.swift.translations.MxCamt05300108_MT940_Translation;

/**
 * This example translates an ISO 20022 bank to customer statement camt.053 into its MT equivalent
 * MT940, the most common translation pair in cash management reporting.
 * <p>
 * A specific translator class is used here since the source message type is known in advance. For
 * an unknown source message use the {@code TranslatorFactoryProvider} instead, as shown in
 * {@link Pacs008Mt103TranslationExample}.
 * <p>
 * The account balances are mapped into fields 60F/62F and each statement entry into a field 61.
 *
 * @since 9.1.2
 */
public class Camt053Mt940TranslationExample {

    public static final String sample = "<RequestPayload>\n"
            + "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n"
            + "    <Fr><FIId><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></FIId></Fr>\n"
            + "    <To><FIId><FinInstnId><BICFI>CCCCGB2LXXX</BICFI></FinInstnId></FIId></To>\n"
            + "    <BizMsgIdr>STMT2026081701</BizMsgIdr>\n"
            + "    <MsgDefIdr>camt.053.001.08</MsgDefIdr>\n"
            + "    <CreDt>2026-08-17T18:00:00+00:00</CreDt>\n"
            + "</AppHdr>\n"
            + "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.053.001.08\">\n"
            + "  <BkToCstmrStmt>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>STMT2026081701</MsgId>\n"
            + "      <CreDtTm>2026-08-17T18:00:00+00:00</CreDtTm>\n"
            + "    </GrpHdr>\n"
            + "    <Stmt>\n"
            + "      <Id>STMT2026081701</Id>\n"
            + "      <ElctrncSeqNb>231</ElctrncSeqNb>\n"
            + "      <Acct><Id><IBAN>BE71096123456769</IBAN></Id></Acct>\n"
            + "      <Bal>\n"
            + "        <Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>\n"
            + "        <Amt Ccy=\"EUR\">15000.00</Amt>\n"
            + "        <CdtDbtInd>CRDT</CdtDbtInd>\n"
            + "        <Dt><Dt>2026-08-17</Dt></Dt>\n"
            + "      </Bal>\n"
            + "      <Bal>\n"
            + "        <Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>\n"
            + "        <Amt Ccy=\"EUR\">17750.50</Amt>\n"
            + "        <CdtDbtInd>CRDT</CdtDbtInd>\n"
            + "        <Dt><Dt>2026-08-17</Dt></Dt>\n"
            + "      </Bal>\n"
            + "      <Ntry>\n"
            + "        <Amt Ccy=\"EUR\">3000.50</Amt>\n"
            + "        <CdtDbtInd>CRDT</CdtDbtInd>\n"
            + "        <Sts><Cd>BOOK</Cd></Sts>\n"
            + "        <BookgDt><Dt>2026-08-17</Dt></BookgDt>\n"
            + "        <ValDt><Dt>2026-08-17</Dt></ValDt>\n"
            + "        <BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>\n"
            + "        <NtryDtls><TxDtls><Refs><EndToEndId>E2E-INCOMING-01</EndToEndId></Refs></TxDtls></NtryDtls>\n"
            + "      </Ntry>\n"
            + "      <Ntry>\n"
            + "        <Amt Ccy=\"EUR\">250.00</Amt>\n"
            + "        <CdtDbtInd>DBIT</CdtDbtInd>\n"
            + "        <Sts><Cd>BOOK</Cd></Sts>\n"
            + "        <BookgDt><Dt>2026-08-17</Dt></BookgDt>\n"
            + "        <ValDt><Dt>2026-08-17</Dt></ValDt>\n"
            + "        <BkTxCd><Prtry><Cd>NCHG</Cd></Prtry></BkTxCd>\n"
            + "        <NtryDtls><TxDtls><Refs><EndToEndId>E2E-FEES-02</EndToEndId></Refs></TxDtls></NtryDtls>\n"
            + "      </Ntry>\n"
            + "    </Stmt>\n"
            + "  </BkToCstmrStmt>\n"
            + "</Document>\n"
            + "</RequestPayload>";

    public static void main(String[] args) {
        MxCamt05300108 source = MxCamt05300108.parse(sample);

        MxCamt05300108_MT940_Translation translation = new MxCamt05300108_MT940_Translation();
        MT940 mt = translation.translate(source);

        System.out.println(mt.message());
    }
}
