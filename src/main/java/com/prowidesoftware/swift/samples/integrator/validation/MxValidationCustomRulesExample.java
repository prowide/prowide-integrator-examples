/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.MxBusinessProcess;
import com.prowidesoftware.swift.model.MxId;
import com.prowidesoftware.swift.validator.MxCustomValidationRule;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationResult;
import com.prowidesoftware.swift.validator.mx.MxValidationEngine;
import com.prowidesoftware.swift.validator.mx.MxValidationEngineFactory;
import com.prowidesoftware.swift.validator.mx.ValidationStandard;
import com.prowidesoftware.xml.XmlNode;
import com.prowidesoftware.xml.XmlParser;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This example adds a custom validation rule for MX messages, evaluated after all the standard
 * validations.
 * <p>
 * The rule checks that payment identifications include the UETR, a common institution specific
 * requirement on top of the standard. It is registered with a wildcard {@link MxId} holding just
 * the business process, so it applies to every pacs message regardless of the type, variant and
 * version. A fully populated MxId can be used instead to target one specific message type.
 *
 * @since 7.10.7
 */
public class MxValidationCustomRulesExample {

    /**
     * Custom rules implement {@link MxCustomValidationRule} and return the problems found, or an
     * empty set when the message is fine.
     */
    public static class UetrPresentRule implements MxCustomValidationRule {
        @Override
        public Set<ValidationProblem> eval(String xml, MxId id) {
            XmlNode root = XmlParser.parse(xml);
            if (root != null && root.findFirst("//PmtId/UETR") == null) {
                Set<ValidationProblem> problems = new HashSet<>();
                problems.add(new ValidationProblem("MISSING_UETR", "The payment identification must include the UETR"));
                return problems;
            }
            return Collections.emptySet();
        }
    }

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
            + "      <DbtrAgt><FinInstnId><BICFI>AAAABEBBXXX</BICFI></FinInstnId></DbtrAgt>\n"
            + "      <CdtrAgt><FinInstnId><BICFI>BBBBUS33XXX</BICFI></FinInstnId></CdtrAgt>\n"
            + "      <Cdtr><Nm>BENEFICIARY</Nm></Cdtr>\n"
            + "    </CdtTrfTxInf>\n"
            + "  </FIToFICstmrCdtTrf>\n"
            + "</Document>";

    public static void main(String[] args) {
        MxValidationEngine engine = MxValidationEngineFactory.createEngine(ValidationStandard.ISO_20022);
        try {
            // apply the custom rule to all pacs messages, any type and version
            engine.getConfig()
                    .addCustomMxRule(new MxId().setBusinessProcess(MxBusinessProcess.pacs), new UetrPresentRule());

            ValidationResult result = engine.validateMessage(sample);

            System.out.println("valid: " + result.isValid());
            for (ValidationProblem problem : result.getProblems()) {
                System.out.println(problem.getErrorKey() + ": " + problem.getMessage());
            }
        } finally {
            engine.dispose();
        }
    }
}
