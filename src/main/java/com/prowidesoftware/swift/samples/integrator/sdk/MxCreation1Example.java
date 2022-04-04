/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.mx.MxPain00100108;
import com.prowidesoftware.swift.model.mx.dic.CustomerCreditTransferInitiationV08;
import com.prowidesoftware.swift.model.mx.dic.GroupHeader48;
import com.prowidesoftware.swift.model.mx.dic.PartyIdentification43;
import com.prowidesoftware.swift.model.mx.dic.PaymentInstruction22;

import java.math.BigDecimal;

/**
 * This example shows how to create a new MX message using the Java model to set its content.<br>
 * Outputs this:<br>
 *
 * <pre>
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Doc:Document xmlns:Doc="urn:iso:std:iso:20022:tech:xsd:pain.001.001.08" xmlns:xsi="{http://www.w3.org/2000/xmlns/}Doc">
 *  <Doc:CstmrCdtTrfInitn>
 *   <Doc:GrpHdr>
 *    <Doc:CtrlSum>100</Doc:CtrlSum>
 *   </Doc:GrpHdr>
 *
 *   <Doc:PmtInf>
 *    <Doc:Dbtr>
 *     <Doc:Nm>foo</Doc:Nm>
 *    </Doc:Dbtr>
 *   </Doc:PmtInf>
 *  </Doc:CstmrCdtTrfInitn>
 * </Doc:Document>
 * </pre>
 *
 * @since 7.6
 */
public class MxCreation1Example {

    public static void main(String[] args) {
        /*
         * Initialize the MX object
         */
        MxPain00100108 pain001001 = new MxPain00100108();

        /*
         * Construct element content using the business dictionary
         */
        PaymentInstruction22 pi = new PaymentInstruction22()
                .setDbtr(new PartyIdentification43()
                        .setNm("foo")
                );

        CustomerCreditTransferInitiationV08 ccti = new CustomerCreditTransferInitiationV08()
                .setGrpHdr(new GroupHeader48()
                        .setCtrlSum(new BigDecimal(100))
                )
                .addPmtInf(pi);

        pain001001.setCstmrCdtTrfInitn(ccti);

        /*
         * Print the generated message in its XML format
         */
        System.out.println(pain001001.message());
    }
}
