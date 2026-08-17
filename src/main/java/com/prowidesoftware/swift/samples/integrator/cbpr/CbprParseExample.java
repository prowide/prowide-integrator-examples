/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.cbpr;

import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.cbpr.CbprMessageFactory;
import com.prowidesoftware.swift.model.mx.cbpr.CbprMessageType;
import com.prowidesoftware.swift.model.mx.cbpr.MxPacs00800108;

/**
 * This example parses a CBPR+ message with the CBPR restricted model, provided by the
 * pw-swift-integrator-cbpr module.
 * <p>
 * The restricted model classes live in {@code com.prowidesoftware.swift.model.mx.cbpr} and share
 * both the class names and the XML namespaces with the generic ISO 20022 model, so the import
 * package is what selects the model. The {@link CbprMessageFactory} detects the message type and
 * variant (STP, COV, ADV, etc.) from the Business Application Header, and returns null when the
 * header is missing, is not a BAH v2, or the business service is not CBPR+.
 * <p>
 * The {@link CbprMessageType} enum catalogs the supported types with their business service,
 * namespace and CBPR+ usage guideline schema.
 *
 * @since 2.0u4
 */
public class CbprParseExample {

    public static final String sample = "<RequestPayload>\n"
            + "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n"
            + "    <Fr><FIId><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></FIId></Fr>\n"
            + "    <To><FIId><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></FIId></To>\n"
            + "    <BizMsgIdr>REF20260817001</BizMsgIdr>\n"
            + "    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>\n"
            + "    <BizSvc>" + CbprMessageType.pacs_008_001_08.businessService() + "</BizSvc>\n"
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
            + "        <InstrId>REF20260817001</InstrId>\n"
            + "        <EndToEndId>E2E20260817001</EndToEndId>\n"
            + "        <UETR>00000000-0000-4000-8000-000000000004</UETR>\n"
            + "      </PmtId>\n"
            + "      <IntrBkSttlmAmt Ccy=\"USD\">1000000.00</IntrBkSttlmAmt>\n"
            + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
            + "      <ChrgBr>SHAR</ChrgBr>\n"
            + "      <InstgAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></InstgAgt>\n"
            + "      <InstdAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></InstdAgt>\n"
            + "      <Dbtr>\n"
            + "        <Nm>ABC COMPANY</Nm>\n"
            + "        <PstlAdr><TwnNm>TORONTO</TwnNm><Ctry>CA</Ctry></PstlAdr>\n"
            + "      </Dbtr>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr>\n"
            + "        <Nm>123 SUPPLIER</Nm>\n"
            + "        <PstlAdr><TwnNm>LONDON</TwnNm><Ctry>GB</Ctry></PstlAdr>\n"
            + "      </Cdtr>\n"
            + "      <CdtrAcct><Id><IBAN>GB29NWBK60161331926819</IBAN></Id></CdtrAcct>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>\n"
            + "</RequestPayload>";

    public static void main(String[] args) {
        // the factory resolves the CBPR+ type and variant from the header
        AbstractMX mx = CbprMessageFactory.createMessage(sample);
        if (mx == null) {
            System.out.println("Not a CBPR+ message");
            return;
        }
        System.out.println("Parsed as: " + mx.getClass().getName());

        // the restricted model exposes the CBPR+ cardinality: a single transaction, not a list
        MxPacs00800108 pacs = (MxPacs00800108) mx;
        System.out.println("Amount: "
                + pacs.getFIToFICstmrCdtTrf()
                        .getCdtTrfTxInf()
                        .getIntrBkSttlmAmt()
                        .getCcy() + " "
                + pacs.getFIToFICstmrCdtTrf()
                        .getCdtTrfTxInf()
                        .getIntrBkSttlmAmt()
                        .getValue());

        // when serializing, the CBPR+ date time adapters produce explicit offsets (+00:00, never Z)
        System.out.println(mx.message());
    }
}
