/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.BIC;
import com.prowidesoftware.swift.model.IBAN;
import com.prowidesoftware.swift.myformat.mx.builder.MxPacs008Builder;
import com.prowidesoftware.swift.myformat.mx.builder.SwiftNetServicePacs008;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This example creates a complete pacs.008, header included, with the MyFormat message builder; a
 * higher level alternative to populating the JAXB model classes element by element as shown in
 * {@link com.prowidesoftware.swift.samples.integrator.sdk.MxCreation2Example}.
 * <p>
 * The builder is created for a SwiftNet service variant (CBPR+, SIC or TARGET2), which determines
 * the message version, the application header type and the business service. Business elements are
 * set with typed setters, and any element not covered by them can still be set with
 * {@code setElement(path, value)}.
 *
 * @since 9.4.8
 */
public class MxBuilderExample {

    public static void main(String[] args) {
        String xml = new MxPacs008Builder(SwiftNetServicePacs008.CBPR)
                .setSender(new BIC("AAAABEBBXXX"))
                .setReceiver(new BIC("BBBBUS33XXX"))
                .setBusinessMessageIdentifier("REF20260817001")
                .setMessageIdentification("REF20260817001")
                .setEndToEndIdentification("E2E20260817001")
                .setUETR()
                .setSettlementAmount("EUR", new BigDecimal("125000.00"))
                .setSettlementDate(LocalDate.of(2026, 8, 18))
                .setChargeBearer(MxPacs008Builder.ChargeBearer.SHAR)
                .setDebtorAgent(new BIC("AAAABEBBXXX"))
                .setDebtorAccountId(new IBAN("BE71096123456769"))
                .setCreditorAgent(new BIC("BBBBUS33XXX"))
                .setCreditorAccountId("1234567890")
                // elements without a typed setter can be set by path
                .setElement("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Dbtr/Nm", "ORDERING CUSTOMER")
                .setElement("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Cdtr/Nm", "BENEFICIARY CUSTOMER")
                .build();

        System.out.println(xml);
    }
}
