/*
 * Copyright (c) http://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.math.BigDecimal;

import com.prowidesoftware.swift.model.mx.MxPain00100105;
import com.prowidesoftware.swift.model.mx.dic.CustomerCreditTransferInitiationV05;
import com.prowidesoftware.swift.model.mx.dic.GroupHeader48;
import com.prowidesoftware.swift.model.mx.dic.PartyIdentification43;
import com.prowidesoftware.swift.model.mx.dic.PaymentInstruction9;

/**
 * Outputs this:
 * 
 * <pre>
<?xml version="1.0" encoding="UTF-8"?>
 <Doc:Document xmlns:Doc="urn:iso:std:iso:20022:tech:xsd:pain.001.001.05" xmlns:xsi="{http://www.w3.org/2000/xmlns/}Doc">
  <Doc:CstmrCdtTrfInitn>
   <Doc:GrpHdr>
    <Doc:CtrlSum>100</Doc:CtrlSum>
   </Doc:GrpHdr>
  
   <Doc:PmtInf>
    <Doc:Dbtr>
     <Doc:Nm>foo</Doc:Nm>
    </Doc:Dbtr>
   </Doc:PmtInf>
  </Doc:CstmrCdtTrfInitn>
 </Doc:Document>
 * </pre>
 *
 * @since 7.6
 */
public class MxCreationExample {

    public static void main(String[] args) {
	MxPain00100105 pain001001 = new MxPain00100105();

	PaymentInstruction9 pi = new PaymentInstruction9()
		.setDbtr(new PartyIdentification43()
			.setNm("foo")
		);
	
	CustomerCreditTransferInitiationV05 ccti = new CustomerCreditTransferInitiationV05()
		.setGrpHdr(new GroupHeader48()
			.setCtrlSum(new BigDecimal(100))
		)
		.addPmtInf(pi);

	pain001001.setCstmrCdtTrfInitn(ccti);

	System.out.println(pain001001.message());
    }
}
