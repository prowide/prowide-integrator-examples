/*
 * Copyright (c) http://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.math.BigDecimal;

import com.prowidesoftware.swift.model.mx.MxPain00100105Iso20022;
import com.prowidesoftware.swift.model.mx.dic.CustomerCreditTransferInitiationV05;
import com.prowidesoftware.swift.model.mx.dic.GroupHeader48;
import com.prowidesoftware.swift.model.mx.dic.PartyIdentification43;
import com.prowidesoftware.swift.model.mx.dic.PaymentInstruction9;

/**
 * Outputs this:
 * <pre>
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.05">
    <CstmrCdtTrfInitn>
        <GrpHdr>
            <CtrlSum>100</CtrlSum>
        </GrpHdr>
        <PmtInf>
            <Dbtr>
                <Nm>foo</Nm>
            </Dbtr>
        </PmtInf>
    </CstmrCdtTrfInitn>
</Document>
</pre>
 *
 * @since 7.6
 */
public class MxCreationExample {

    public static void main (String[] args) {
    	MxPain00100105Iso20022 pain001001 = new MxPain00100105Iso20022();

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

        System.out.println(pain001001.message());
    }
}
