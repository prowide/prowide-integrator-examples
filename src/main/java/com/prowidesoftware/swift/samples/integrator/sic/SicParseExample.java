/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sic;

import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.sic.v5_2.MxPacs00800108Ch02;
import com.prowidesoftware.swift.model.mx.sic.v5_2.SicMessageFactory;
import com.prowidesoftware.swift.model.mx.sic.v5_2.SicMessageType;

/**
 * This example parses a Swiss SIC (Swiss Interbank Clearing RTGS) message with the SIC restricted
 * model, provided by the pw-swift-integrator-sic module.
 * <p>
 * SIC messages use proprietary namespaces from SIX (such as
 * {@code http://www.six-interbank-clearing.com/de/pacs.008.001.08.ch.02}) instead of the standard
 * ISO 20022 ones, which is why a dedicated model is required. The model is versioned per SIC
 * release in separate packages; {@code v5_2} is the current one, and older releases remain
 * available as deprecated packages for backwards compatibility.
 * <p>
 * The {@link SicMessageFactory} resolves the message class from the document namespace, and the
 * {@link SicMessageType} enum catalogs the supported types with their namespace and schema.
 *
 * @since 10.3.3
 */
public class SicParseExample {

    public static final String sample =
            "<Document xmlns=\"http://www.six-interbank-clearing.com/de/pacs.008.001.08.ch.02\">\n"
                    + "  <FIToFICstmrCdtTrf>\n"
                    + "    <GrpHdr>\n"
                    + "      <MsgId>MSGID-pacs008-20260817-0001</MsgId>\n"
                    + "      <CreDtTm>2026-08-17T08:30:47.000Z</CreDtTm>\n"
                    + "      <NbOfTxs>1</NbOfTxs>\n"
                    + "      <SttlmInf>\n"
                    + "        <SttlmMtd>CLRG</SttlmMtd>\n"
                    + "        <ClrSys><Cd>SIC</Cd></ClrSys>\n"
                    + "      </SttlmInf>\n"
                    + "    </GrpHdr>\n"
                    + "    <CdtTrfTxInf>\n"
                    + "      <PmtId>\n"
                    + "        <EndToEndId>NOTPROVIDED</EndToEndId>\n"
                    + "        <TxId>20260817-1-0001</TxId>\n"
                    + "        <UETR>00000000-0000-4000-8000-000000000006</UETR>\n"
                    + "      </PmtId>\n"
                    + "      <PmtTpInf><LclInstrm><Prtry>CSTPMT</Prtry></LclInstrm></PmtTpInf>\n"
                    + "      <IntrBkSttlmAmt Ccy=\"CHF\">1111</IntrBkSttlmAmt>\n"
                    + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
                    + "      <ChrgBr>SHAR</ChrgBr>\n"
                    + "      <InstgAgt><FinInstnId><ClrSysMmbId><ClrSysId><Cd>CHSIC</Cd></ClrSysId>"
                    + "<MmbId>099991</MmbId></ClrSysMmbId></FinInstnId></InstgAgt>\n"
                    + "      <InstdAgt><FinInstnId><ClrSysMmbId><ClrSysId><Cd>CHSIC</Cd></ClrSysId>"
                    + "<MmbId>099992</MmbId></ClrSysMmbId></FinInstnId></InstdAgt>\n"
                    + "      <Dbtr><Nm>DEBTOR COMPANY</Nm></Dbtr>\n"
                    + "      <DbtrAcct><Id><IBAN>CH4089999001234567890</IBAN></Id></DbtrAcct>\n"
                    + "      <DbtrAgt><FinInstnId><ClrSysMmbId><ClrSysId><Cd>CHSIC</Cd></ClrSysId>"
                    + "<MmbId>099991</MmbId></ClrSysMmbId></FinInstnId></DbtrAgt>\n"
                    + "      <CdtrAgt><FinInstnId><ClrSysMmbId><ClrSysId><Cd>CHSIC</Cd></ClrSysId>"
                    + "<MmbId>099992</MmbId></ClrSysMmbId></FinInstnId></CdtrAgt>\n"
                    + "      <Cdtr><Nm>CREDITOR COMPANY</Nm></Cdtr>\n"
                    + "      <CdtrAcct><Id><IBAN>CH5604835012345678009</IBAN></Id></CdtrAcct>\n"
                    + "    </CdtTrfTxInf>\n"
                    + "  </FIToFICstmrCdtTrf>\n"
                    + "</Document>";

    public static void main(String[] args) {
        // the factory resolves the model class from the proprietary namespace
        AbstractMX mx = SicMessageFactory.createMessage(sample);
        if (mx == null) {
            System.out.println("Not a SIC message");
            return;
        }
        System.out.println("Parsed as: " + mx.getClass().getName());
        System.out.println("Namespace: " + mx.getNamespace());
        System.out.println("Type: " + SicMessageType.ofNamespace(mx.getNamespace()));

        // the restricted model exposes the SIC cardinality: a single transaction, not a list
        MxPacs00800108Ch02 pacs = (MxPacs00800108Ch02) mx;
        System.out.println("Amount: "
                + pacs.getFIToFICstmrCdtTrf()
                        .getCdtTrfTxInf()
                        .getIntrBkSttlmAmt()
                        .getCcy() + " "
                + pacs.getFIToFICstmrCdtTrf()
                        .getCdtTrfTxInf()
                        .getIntrBkSttlmAmt()
                        .getValue());
    }
}
