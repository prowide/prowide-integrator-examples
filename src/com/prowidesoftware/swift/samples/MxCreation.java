/*
 * Copyright (c) http://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.math.BigDecimal;

import com.prowidesoftware.swift.mx.model.MxPain001001Iso20022;
import com.prowidesoftware.swift.mx.model.dic.CustomerCreditTransferInitiationV05;
import com.prowidesoftware.swift.mx.model.dic.GroupHeader48;
import com.prowidesoftware.swift.mx.model.dic.PartyIdentification43;
import com.prowidesoftware.swift.mx.model.dic.PaymentInstruction9;

public class MxCreation {

    public static void main (String[] args) {
    	MxPain001001Iso20022 pain001001 = new MxPain001001Iso20022();

        CustomerCreditTransferInitiationV05 ccti = new CustomerCreditTransferInitiationV05();
        GroupHeader48 groupHeader = new GroupHeader48();
        groupHeader.setCtrlSum(new BigDecimal(100));

        ccti.setGrpHdr(groupHeader);


        PaymentInstruction9 pi = new PaymentInstruction9();
        PartyIdentification43 dbtr = new PartyIdentification43();
        dbtr.setNm("foo");
		pi.setDbtr(dbtr);
        ccti.getPmtInf().add(pi);

        pain001001.setCstmrCdtTrfInitn(ccti);

    }
}
