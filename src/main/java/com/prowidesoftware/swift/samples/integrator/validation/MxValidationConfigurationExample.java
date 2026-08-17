/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;
import com.prowidesoftware.swift.validator.mx.MxValidationEngine;
import com.prowidesoftware.swift.validator.mx.MxValidationEngineFactory;
import com.prowidesoftware.swift.validator.mx.ValidationStandard;

/**
 * This example shows MX validation configuration options that extend the plain XSD schema check:
 * <ul>
 *   <li><b>External code sets</b>: elements typed as ISO 20022 external codes (category purpose,
 *   local instrument, etc.) are not restricted in the XSD; enabling this option validates their
 *   values against the published external code set lists.</li>
 *   <li><b>Missing data element rejection</b>: empty structures such as a party without content
 *   pass the XSD but are rejected by the SWIFT network; enabling this option reports them as
 *   S00001 problems.</li>
 * </ul>
 * The sample pacs.008 carries an invalid category purpose code and an empty postal address, both
 * undetected when the options are disabled.
 *
 * @since 9.4.32
 */
public class MxValidationConfigurationExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n"
            + "  <FIToFICstmrCdtTrf>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>REF20260817001</MsgId>\n"
            + "      <CreDtTm>2026-08-17T09:30:00+00:00</CreDtTm>\n"
            + "      <NbOfTxs>1</NbOfTxs>\n"
            + "      <SttlmInf><SttlmMtd>INDA</SttlmMtd></SttlmInf>\n"
            + "    </GrpHdr>\n"
            + "    <CdtTrfTxInf>\n"
            + "      <PmtId>\n"
            + "        <EndToEndId>E2E20260817001</EndToEndId>\n"
            + "        <UETR>00000000-0000-4000-8000-000000000008</UETR>\n"
            + "      </PmtId>\n"
            + "      <PmtTpInf><CtgyPurp><Cd>QQQQ</Cd></CtgyPurp></PmtTpInf>\n"
            + "      <IntrBkSttlmAmt Ccy=\"EUR\">125000.00</IntrBkSttlmAmt>\n"
            + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
            + "      <ChrgBr>SHAR</ChrgBr>\n"
            + "      <Dbtr><Nm>ORDERING CUSTOMER</Nm><PstlAdr></PstlAdr></Dbtr>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr><Nm>BENEFICIARY</Nm></Cdtr>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>";

    public static void main(String[] args) {
        MxValidationEngine engine = MxValidationEngineFactory.createEngine(ValidationStandard.ISO_20022);
        try {
            // default configuration: the message passes the XSD schema
            print("Default configuration", engine.validateMessage(sample));

            // enable the additional checks and validate again
            engine.getConfig().setExternalCodeSetValidationEnabled(true);
            engine.getConfig().setMissingDataElementRejectionEnabled(true);
            print("With external code sets and missing data element rejection", engine.validateMessage(sample));

        } finally {
            engine.dispose();
        }
    }

    private static void print(String title, ValidationResult result) {
        System.out.println(title + " -> valid: " + result.isValid());
        for (ValidationProblem problem : result.getProblems()) {
            System.out.println("  " + problem.getErrorKey() + " at " + problem.getPath() + ": " + problem.getMessage());
        }
    }
}
