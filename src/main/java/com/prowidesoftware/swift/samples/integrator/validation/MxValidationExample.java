package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.mx.MxPain00100108;
import com.prowidesoftware.swift.model.mx.dic.CustomerCreditTransferInitiationV08;
import com.prowidesoftware.swift.model.mx.dic.GroupHeader48;
import com.prowidesoftware.swift.model.mx.dic.PartyIdentification43;
import com.prowidesoftware.swift.model.mx.dic.PaymentInstruction22;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

import java.math.BigDecimal;
import java.util.List;

/**
 * MX validation example
 */
public class MxValidationExample {

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
                        .setMsgId("MYREF")
                )
                .addPmtInf(pi);


        pain001001.setCstmrCdtTrfInitn(ccti);

        ValidationEngine engine = new ValidationEngine();
        engine.initialize();

        /*
         * Run the validation and print results
         */
        List<ValidationProblem> r = engine.validateMessage(pain001001);
        System.out.println(ValidationProblem.printout(r));

        /*
         * Release engine
         */
        engine.dispose();
    }
    
}
