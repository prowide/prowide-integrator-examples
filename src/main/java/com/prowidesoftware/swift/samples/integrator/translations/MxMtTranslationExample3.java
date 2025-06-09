/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import java.util.Optional;
import com.prowidesoftware.swift.model.MxId;
import com.prowidesoftware.swift.model.mx.MxParseUtils;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.BusinessAppHdrV02;
import com.prowidesoftware.swift.model.mx.MxPacs00900108;
import com.prowidesoftware.swift.model.mx.cbpr.MxPacs00900108ADV;
import com.prowidesoftware.swift.translations.cbpr.MxPacs00900108ADV_MT202_Translation;
import com.prowidesoftware.swift.translations.MxPacs00900108_MT202COV_Translation;
import com.prowidesoftware.swift.translations.MxPacs00900108_MT205COV_Translation;
import com.prowidesoftware.swift.translations.MxPacs00900108_MT202_Translation;
import com.prowidesoftware.swift.translations.MxPacs00900108_MT205_Translation;
import com.prowidesoftware.swift.translations.MxPacs00900108_MT200_Translation;



/**
 * Translation Samples for MxPacs00900108 to MT200/202/205 including ADV/COV
 *
 * @author msubrama
 * @since 7.9.6
 */
public class MxMtTranslationExample3 {
    /**
     * source xml : xml payload of MX message
     */
    private static final String xml =
            "<Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:swift:xsd:envelope\"\n"
                    + "          xsi:schemaLocation=\"urn:swift:xsd:envelope ../../../../Schemas/Translator_envelope.xsd\">\n"
                    + "    <AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n"
                    + "        <Fr>\n"
                    + "            <FIId>\n"
                    + "                <FinInstnId>\n"
                    + "                    <BICFI>FOOHGB2LXXX</BICFI>\n"
                    + "                </FinInstnId>\n"
                    + "            </FIId>\n"
                    + "        </Fr>\n"
                    + "        <To>\n"
                    + "            <FIId>\n"
                    + "                <FinInstnId>\n"
                    + "                    <BICFI>FOOPGB2L</BICFI>\n"
                    + "                </FinInstnId>\n"
                    + "            </FIId>\n"
                    + "        </To>\n"
                    + "        <BizMsgIdr>Y7X9493727291</BizMsgIdr>\n"
                    + "        <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n"
                    + "        <BizSvc>swift.cbprplus.02</BizSvc>\n"
                    + "        <CreDt>2021-05-03T12:45:41.960+00:00</CreDt>\n"
                    + "    </AppHdr>\n"
                    + "    <Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n"
                    + "        <FICdtTrf>\n"
                    + "            <GrpHdr>\n"
                    + "                <MsgId>Y7X9493727291</MsgId>\n"
                    + "                <CreDtTm>2021-05-03T12:45:41.960+00:00</CreDtTm>\n"
                    + "                <NbOfTxs>1</NbOfTxs>\n"
                    + "                <SttlmInf>\n"
                    + "                    <SttlmMtd>INDA</SttlmMtd>\n"
                    + "                    <SttlmAcct>\n"
                    + "                        <Id>\n"
                    + "                            <Othr>\n"
                    + "                                <Id>001 1 949625</Id>\n"
                    + "                            </Othr>\n"
                    + "                        </Id>\n"
                    + "                    </SttlmAcct>\n"
                    + "                </SttlmInf>\n"
                    + "            </GrpHdr>\n"
                    + "            <CdtTrfTxInf>\n"
                    + "                <PmtId>\n"
                    + "                    <InstrId>Y7X9493727291</InstrId>\n"
                    + "                    <EndToEndId>V7V95955493728605</EndToEndId>\n"
                    + "                    <UETR>825d754a-7317-4708-ac32-7f58b469dc72</UETR>\n"
                    + "                </PmtId>\n"
                    + "                <PmtTpInf>\n"
                    + "                    <SvcLvl>\n"
                    + "                        <Cd>G003</Cd>\n"
                    + "                    </SvcLvl>\n"
                    + "                </PmtTpInf>\n"
                    + "                <IntrBkSttlmAmt Ccy=\"GBP\">1658000</IntrBkSttlmAmt>\n"
                    + "                <IntrBkSttlmDt>2021-05-03</IntrBkSttlmDt>\n"
                    + "                <InstgAgt>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOHGB2LXXX</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </InstgAgt>\n"
                    + "                <InstdAgt>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOPGB2L</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </InstdAgt>\n"
                    + "                <Dbtr>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOHUS33XXX</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </Dbtr>\n"
                    + "                <DbtrAgt>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOHGB2LXXX</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </DbtrAgt>\n"
                    + "                <CdtrAgt>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOHGB2L</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </CdtrAgt>\n"
                    + "                <Cdtr>\n"
                    + "                    <FinInstnId>\n"
                    + "                        <BICFI>FOOHGB2LXXX</BICFI>\n"
                    + "                    </FinInstnId>\n"
                    + "                </Cdtr>\n"
                    + "                <RmtInf>\n"
                    + "                    <Ustrd>Conract Ref LKHLK987</Ustrd>\n"
                    + "                </RmtInf>\n"
                    + "            </CdtTrfTxInf>\n"
                    + "        </FICdtTrf>\n"
                    + "    </Document>\n"
                    + "</Envelope>";
    /**
     * Force generation of MT205 over MT202
     */

    protected static boolean translateToMT205=false;
    /**
     * target message type
     */
    protected static String targetMTType;

    /**
     * target message
     */
    protected static AbstractMT targetMT;


    public static void main(String[] args) {
        /**
         * Generate Target as MT205 over MT202
         // 202COV/205COV: Not possible to distinguish 202/205 without custom handling.
         // Custom logic Handling to classify 202/205
         // MT 202 Scope:This message is sent by or on behalf of the ordering institution directly.
         // MT 205 Scope: This message is sent by the Receiver of a category 2 message.
         */
        String arg = "";
        if (args.length>1){
            arg=args[0];
            if (arg.contains("205")) translateToMT205=true;
        }

        // detect message type
        Optional<MxId> id = MxParseUtils.identifyMessage(xml);

        if (!id.isPresent() || !id.get().id().equalsIgnoreCase("pacs.009.001.08")) {
            throw new IllegalStateException("Invalid FICdtTrf Payload" + id);
        }

        // parse the source message
        final AbstractMX abstractMX = AbstractMX.parse(xml);
        //Handle pacs009 ADV
        //BizSvc contains .ADV  -> 202
        if (((BusinessAppHdrV02) abstractMX.getAppHdr()).getBizSvc()!=null && ((BusinessAppHdrV02) abstractMX.getAppHdr()).getBizSvc().contains("adv")){
            // TO-DO: Customer specific ADV Handling:
            // As there is no MT202 ADV in SWIFT, so pacs.009 ADV is translated to MT202 CORE
            targetMTType = "202ADV";
            MxPacs00900108ADV_MT202_Translation translatorMT202ADV = new MxPacs00900108ADV_MT202_Translation();
            targetMT = translatorMT202ADV.translate((MxPacs00900108ADV) abstractMX);
        }else {
            /**
             * Main entry point for the Pacs009 translation
             * if UndrlygCstmrCdtTrf present -> 202COV/205COV
             * else if BizSvc contains .ADV  -> 202ADV
             * else if FinancialInstitution Transfer for its Own Account -> 200
             * else -> 202
             **/
            MxPacs00900108 mx = (MxPacs00900108) abstractMX;
            if (isCOV(mx)) {
                if (translateToMT205) {
                    targetMTType = "205COV";
                    MxPacs00900108_MT205COV_Translation translatorMT205COV = new MxPacs00900108_MT205COV_Translation();
                    targetMT = translatorMT205COV.translate(mx);

                } else {
                    targetMTType = "202COV";
                    MxPacs00900108_MT202COV_Translation translatorMT202COV = new MxPacs00900108_MT202COV_Translation();
                    targetMT = translatorMT202COV.translate(mx);
                }
            } else if (isOWN(mx)) {
                targetMTType = "200";
                MxPacs00900108_MT200_Translation translatorMT200 = new MxPacs00900108_MT200_Translation();
                targetMT = translatorMT200.translate(mx);
            } else {
                if (translateToMT205) {
                    targetMTType = "205";
                    MxPacs00900108_MT205_Translation translatorMT205 = new MxPacs00900108_MT205_Translation();
                    targetMT = translatorMT205.translate((MxPacs00900108) abstractMX);
                } else {
                    targetMTType = "202";
                    MxPacs00900108_MT202_Translation translatorMT202 = new MxPacs00900108_MT202_Translation();
                    targetMT = translatorMT202.translate((MxPacs00900108) abstractMX);
                }
            }

            System.out.println("Source Message Type:" + id.get().id().toString());
            System.out.println("Translated MT Type:" + targetMTType);
            System.out.println("Translated MT :" + targetMT.message());
        }
    }

    /**
     * Check pacs009 is COV message
     * @return true/false
     */
    private static boolean isCOV(MxPacs00900108 mxPacs00900108) {
        if (mxPacs00900108!=null && mxPacs00900108.getFICdtTrf().getCdtTrfTxInf().get(0) != null && mxPacs00900108.getFICdtTrf().getCdtTrfTxInf().get(0).getUndrlygCstmrCdtTrf()!=null ) {
            return true;
        }
        return false;
    }

    /**
     * Check pacs009 is ADV message
     * @return true/false
     */
    private static boolean isADV(MxPacs00900108 mx) {
        BusinessAppHdrV02 hdr = (BusinessAppHdrV02) mx.getAppHdr();
        return hdr.getBizSvc().contains("adv");
    }

    /**
     * Check pacs009 for Own Account Transaction
     * @return true/false
     */
    private static boolean isOWN(MxPacs00900108 mx) {
        /**
         * Based on CBPR+ MT202 Translation Rules:
         * Dbtr- > Ordering Institution (52a) or AppHdr/Fr/FIId/FinInstnId/BICFI
         * DbtrAgt - > Sender to Receiver Info (:72:)
         * Cdtr- > Bene Institution (58a)
         * CdtrAgent->AcctWithInstitution (57a)
         * IntrmyAgt1-> Intermediary (56a)
         *
         * OwnAccount deals with 2 Scenario:
         * 1. Financial Institution Transfer for its Own Account with an Account With Institution
         * 2. Financial Institution Transfer for its Own Account with an Intermediary
         */
        String cdtr=mx.getFICdtTrf().getCdtTrfTxInf().get(0).getCdtr().getFinInstnId().getBICFI();
        //non-cbpr+
        String instgAgt=mx.getFICdtTrf().getCdtTrfTxInf().get(0).getInstgAgt().getFinInstnId().getBICFI();
        String dbtr=mx.getFICdtTrf().getCdtTrfTxInf().get(0).getDbtr().getFinInstnId().getBICFI();
        String dbtrAgt =mx.getFICdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getBICFI();
        if(cdtr.equalsIgnoreCase(dbtr) || cdtr.equalsIgnoreCase(dbtrAgt) || cdtr.equalsIgnoreCase(instgAgt) ) {
            return true;
        }else{
            return false;
        }
    }
}
