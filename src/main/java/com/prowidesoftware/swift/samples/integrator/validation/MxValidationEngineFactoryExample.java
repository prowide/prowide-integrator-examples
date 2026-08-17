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
 * This example validates MX messages using the {@link MxValidationEngineFactory}, the current entry
 * point for MX validation (the legacy {@code ValidationEngine} wrapper is deprecated).
 * <p>
 * The factory creates engines for the plain ISO 20022 standard or for restricted market practices:
 * {@link ValidationStandard} selects ISO 20022, CBPR+ or IAP, and there is another factory method
 * accepting a {@code CashClearingSystemCode} for market infrastructures such as the Australian RITS
 * or the Swiss SIC.
 * <p>
 * The validation includes the XSD schema check, cross-element rules, and BIC, IBAN, country and
 * currency checks. The sample pacs.008 is missing the mandatory NbOfTxs element and the payment
 * identification lacks both the transaction id and the UETR (cross-element rule X00420); each
 * reported problem indicates the XML path and location.
 *
 * @since 10.2.24
 */
public class MxValidationEngineFactoryExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n"
            + "  <FIToFICstmrCdtTrf>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>REF20260817001</MsgId>\n"
            + "      <CreDtTm>2026-08-17T09:30:00+00:00</CreDtTm>\n"
            + "      <SttlmInf><SttlmMtd>INDA</SttlmMtd></SttlmInf>\n"
            + "    </GrpHdr>\n"
            + "    <CdtTrfTxInf>\n"
            + "      <PmtId><EndToEndId>E2E20260817001</EndToEndId></PmtId>\n"
            + "      <IntrBkSttlmAmt Ccy=\"EUR\">125000.00</IntrBkSttlmAmt>\n"
            + "      <IntrBkSttlmDt>2026-08-18</IntrBkSttlmDt>\n"
            + "      <ChrgBr>SHAR</ChrgBr>\n"
            + "      <Dbtr><Nm>ORDERING CUSTOMER</Nm></Dbtr>\n"
            + "      <DbtrAcct><Id><IBAN>BE71096123456760</IBAN></Id></DbtrAcct>\n"
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr><Nm>BENEFICIARY</Nm></Cdtr>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>";

    public static void main(String[] args) {
        MxValidationEngine engine = MxValidationEngineFactory.createEngine(ValidationStandard.ISO_20022);
        try {
            // validating the XML directly is preferred over validating a parsed object, since
            // schema errors could be normalized away by the parse
            ValidationResult result = engine.validateMessage(sample);

            System.out.println("valid: " + result.isValid());
            for (ValidationProblem problem : result.getProblems()) {
                System.out.println(problem.getErrorKey() + " at " + problem.getPath() + " (line " + problem.getLine()
                        + "): " + problem.getMessage());
            }
        } finally {
            engine.dispose();
        }
    }
}
