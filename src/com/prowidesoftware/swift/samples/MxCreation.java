/*
 * Copyright (c) http://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;


import iso.std.iso._20022.tech.xsd.pain_001_001.CustomerCreditTransferInitiationV05;
import iso.std.iso._20022.tech.xsd.pain_001_001.Document;
import iso.std.iso._20022.tech.xsd.pain_001_001.GroupHeader48;
import iso.std.iso._20022.tech.xsd.pain_001_001.PaymentInstruction9;
import swift.xsd.camt_006_001.PaymentInstruction1;

import java.math.BigDecimal;

public class MxCreation {

    public static void main (String[] args) {
        Document pain001001 = new Document();

        CustomerCreditTransferInitiationV05 ccti = new CustomerCreditTransferInitiationV05();
        GroupHeader48 groupHeader = new GroupHeader48();
        groupHeader.setCtrlSum(new BigDecimal(100));

        ccti.setGrpHdr(groupHeader);


        PaymentInstruction9 pi = new PaymentInstruction9();
        pi.setDbtr();
        ccti.getPmtInf().add(pi);

        pain001001.setCstmrCdtTrfInitn(ccti);

    }
}
