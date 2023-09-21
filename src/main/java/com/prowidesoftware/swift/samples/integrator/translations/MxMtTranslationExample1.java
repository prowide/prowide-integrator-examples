/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.MxId;
import com.prowidesoftware.swift.model.mt.mt5xx.MT567;
import com.prowidesoftware.swift.model.mx.AppHdrFactory;
import com.prowidesoftware.swift.model.mx.BusinessAppHdrV02;
import com.prowidesoftware.swift.model.mx.MxSeev03400202;
import com.prowidesoftware.swift.model.mx.dic.*;
import com.prowidesoftware.swift.translations.LogicalMessageCriteriaException;
import com.prowidesoftware.swift.translations.MxSeev03400202_MT567_Translation;
import com.prowidesoftware.swift.translations.TranslationPreconditionException;

import java.math.BigDecimal;

/**
 * This example shows how to perform a translation from a MX to its correspondent MT
 * using API from Prowide Integrator Translations module.
 * <br>
 * Example using specific translation class.
 *
 * @since 7.7
 */
public class MxMtTranslationExample1 {

    public static void main(String[] args) {
        /*
         * Create the source MX message
         */
        MxSeev03400202 source = new MxSeev03400202();
        /*
         * Set source message header content
         */
        BusinessAppHdrV02 hdr = AppHdrFactory.createBusinessAppHdrV02("AAAAUSXX", "BBBBUSXX", "MYREF1234", new MxId("seev.034.002.02"));
        hdr.setBizSvc("foo.bar.01");
        source.setAppHdr(hdr);
        /*
         * Set source message document content
         */
        source.setCorpActnInstrStsAdvc(new CorporateActionInstructionStatusAdviceV02Subset());
        source.getCorpActnInstrStsAdvc().setCorpActnGnlInf(new CorporateActionGeneralInformation13());
        source.getCorpActnInstrStsAdvc().getCorpActnGnlInf().setCorpActnEvtId("777");
        source.getCorpActnInstrStsAdvc().getCorpActnGnlInf().setEvtTp(new CorporateActionEventType4Choice());
        source.getCorpActnInstrStsAdvc().getCorpActnGnlInf().getEvtTp().setCd(CorporateActionEventType6Code.BONU);
        source.getCorpActnInstrStsAdvc().setCorpActnInstr(new CorporateActionOption29());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().setOptnNb(new OptionNumber1Choice());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getOptnNb().setCd(OptionNumber1Code.UNSO);
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().setOptnTp(new CorporateActionOption7Choice());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getOptnTp().setCd(CorporateActionOption6Code.ABST);
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().setInstdBal(new SignedQuantityFormat3());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getInstdBal().setShrtLngPos(ShortLong1Code.SHOR);
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getInstdBal().setQtyChc(new Quantity7Choice());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getInstdBal().getQtyChc().setQty(new FinancialInstrumentQuantity15Choice());
        source.getCorpActnInstrStsAdvc().getCorpActnInstr().getInstdBal().getQtyChc().getQty().setAmtsdVal(new BigDecimal("123"));
        source.getCorpActnInstrStsAdvc().getInstrPrcgSts().add(new InstructionProcessingStatus8Choice());
        source.getCorpActnInstrStsAdvc().getInstrPrcgSts().get(0).setAccptd(new AcceptedStatus2Choice());
        source.getCorpActnInstrStsAdvc().getInstrPrcgSts().get(0).getAccptd().setNoSpcfdRsn(NoReasonCode.NORE);

        /*
         * Create an instance of the proper translator
         */
        MxSeev03400202_MT567_Translation translator = new MxSeev03400202_MT567_Translation();

        System.out.println(source.message());
        try {
            /*
             * Call the translation process
             */
            final MT567 mt = translator.translate(source);

            /*
             * Print content from the translated message
             *
             * {1:F01TESTUS00AXXX0000000000}{2:I567TESTUS00XXXXN}{4:
             * 	:16R:GENL
             * 	:20C::CORP//777
             * 	:20C::SEME//MYID
             * 	:23G:INST
             * 	:22F::CAEV//BONU
             * 	:16R:STAT
             * 	:25D::IPRC//PACK
             * 	:16S:STAT
             * 	:16S:GENL
             * 	:16R:CADETL
             * 	:13A::CAON//UNS
             * 	:22F::CAOP//ABST
             * 	:93B::INBA//AMOR/N123,
             * 	:16S:CADETL
             * -}
             */
            System.out.println(mt.message());

        } catch (final LogicalMessageCriteriaException e1) {
            System.out.println("logical message criteria exception: " + e1.getMessage());
        } catch (final TranslationPreconditionException e2) {
            System.out.println("precondition exception: " + e2.getMessage());
        }
    }
}
