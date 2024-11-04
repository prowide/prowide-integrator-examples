/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.mt5xx.MT564;
import com.prowidesoftware.swift.model.mx.MxSeev03900202;
import com.prowidesoftware.swift.translations.LogicalMessageCriteriaException;
import com.prowidesoftware.swift.translations.MT564_MxSeev03900202_Translation;
import com.prowidesoftware.swift.translations.PreconditionError;

import java.util.List;

/**
 * This example shows how to perform a translation from a MT to its correspondent MX
 * using API from Prowide Integrator Translations module. Checking preconditions by API.
 * <p>
 * Example checking preconditions.
 *
 * @since 7.7
 */
public class MtMxTranslationExample2 {

    public static final String sample =
            "{1:F01SFOOBEBB0XXX0001000001}{2:I564SFOOBEBBXBILN}{3:{108:495}}{4:\n" +
                    ":16R:GENL\n" +
                    ":20C::CORP//FOOBAR\n" +
                    ":20C::SEME//FOOVCAMANDSECU\n" +
                    ":23G:CANC/CODU\n" +
                    ":22F::CAEV//DVCA\n" +
                    ":22F::CAMV//MAND\n" +
                    ":98A::PREP//20150230\n" +
                    ":25D::PROC//ENTL\n" +
                    ":16R:LINK\n" +
                    ":20C::PREV//x\n" +
                    ":16S:LINK\n" +
                    ":16S:GENL\n" +

                    ":16R:USECU\n" +
                    ":35B:ISIN JAC9E9G9I9K9\n" +
                    ":16R:FIA\n" +
                    ":94B::PLIS//EXCH/ENXB\n" +
                    ":16S:FIA\n" +
                    ":16R:ACCTINFO\n" +

                    // precondition check happens here, with option C precondition fails
                    ":97A::SAFE//1234\n" +
                    //":97C::SAFE//GENR\n" +

                    ":93B::ELIG/8C/AMOR/250000,\n" +
                    ":93B::UNBA/8C/AMOR/10000,00\n" +
                    ":16S:ACCTINFO\n" +
                    ":16S:USECU\n" +

                    ":16R:INTSEC\n" +
                    ":35B:ISIN A2C9E9G9I9K9\n" +
                    ":36B::QINT//UNIT/1,3456789012345\n" +
                    ":22F::DISF/A9C9E9G9/DIST\n" +
                    ":22F::SELL/A9C9E9G9/NREN\n" +
                    ":92D::RTUN//2500,/5500,\n" +
                    ":98A::EXPI//20051231\n" +
                    ":98A::POST//20051101\n" +
                    ":69A::TRDP//20051001/20051020\n" +
                    ":16S:INTSEC\n" +

                    ":16R:CAOPTN\n" +
                    ":13A::CAON//001\n" +
                    ":22F::CAOP//SECU\n" +
                    ":22F::OPTF//QREC\n" +
                    ":22F::OPTF//QOVE\n" +
                    ":22F::OPTF//OPLF\n" +
                    ":17B::DFLT//Y\n" +
                    ":35B:ISIN A9C9E9G9I9K9\n" +
                    ":69A::PRIC//20051130/20150101\n" +
                    ":69A::REVO//20051130/20150101\n" +
                    ":69A::PWAL//20051130/20150101\n" +
                    ":69A::SUSP//20051130/20150101\n" +
                    ":69A::PARL//20051130/20150101\n" +
                    ":92A::TAXR//N1,3456789012345\n" +
                    ":92A::ATAX//N1,3456789012345\n" +
                    ":92A::INDX//10000,50\n" +
                    ":92F::GRSS//USD1,\n" +
                    ":92A::OVEP//N1,3456789012345\n" +
                    ":90A::CINL//DISC/1,345678901234\n" +
                    ":90A::OSUB//DISC/1,345678901234\n" +
                    ":36B::MIEX//AMOR/1,3456789012345\n" +
                    ":36B::MILT//AMOR/1,3456789012345\n" +
                    ":36B::NBLT//AMOR/1,3456789012345\n" +
                    ":36B::NEWD//AMOR/1,3456789012345\n" +

                    ":16R:SECMOVE\n" +
                    ":22H::CRDB//CRED\n" +
                    ":35B:ISIN A9C9E9G9I9K9\n" +
                    ":16R:FIA\n" +
                    ":22F::MICO//A011\n" +
                    ":92A::PRFC//N1,3456789012345\n" +
                    ":92A::NWFC//N1,3456789012345\n" +
                    ":92A::INTR//N1,3456789012345\n" +
                    ":92A::NXRT//N1,3456789012345\n" +
                    ":36B::MINO//AMOR/1,3456789012345\n" +
                    ":36B::MIEX//AMOR/1,3456789012345\n" +
                    ":36B::MILT//AMOR/1,3456789012345\n" +
                    ":16S:FIA\n" +
                    ":36B::ENTL//AMOR/1,3456789012345\n" +
                    ":90A::INDC//DISC/1,345678901234\n" +
                    ":90A::CINL//DISC/1,345678901234\n" +
                    ":92D::ADSR//1,3456789012345/1,3456789012345\n" +
                    ":92D::NEWO//1,3456789012345/12345678901234,\n" +
                    ":98A::PAYD//20060101\n" +
                    ":16S:SECMOVE\n" +

                    ":16R:CASHMOVE\n" +
                    ":22H::CRDB//CRED\n" +
                    ":97A::CASH//x\n" +
                    ":19B::ENTL//USD1,34\n" +
                    ":19B::RESU//USD1,35\n" +
                    ":19B::OCMT//USD1,36\n" +
                    ":19B::CAPG//USD1,37\n" +
                    ":19B::INDM//USD1,38\n" +
                    ":19B::CINL//USD1,39\n" +
                    ":19B::CHAR//USD1,40\n" +
                    ":19B::FLFR//USD1,41\n" +
                    ":19B::UNFR//USD1,42\n" +
                    ":19B::TXFR//USD1,43\n" +
                    ":19B::TXDF//USD1,44\n" +
                    ":19B::SOIC//USD1,45\n" +
                    ":19B::GRSS//USD1,46\n" +
                    ":19B::INTR//USD1,47\n" +
                    ":19B::MKTC//USD1,48\n" +
                    ":19B::NETT//USD1,49\n" +
                    ":19B::PRIN//USD1,50\n" +
                    ":19B::REIN//USD1,51\n" +
                    ":19B::TAXC//USD1,52\n" +
                    ":19B::TAXR//USD1,53\n" +
                    ":19B::WITF//USD1,54\n" +
                    ":19B::WITL//USD1,55\n" +
                    ":19B::REDP//USD1,56\n" +
                    ":98A::PAYD//20150101\n" +
                    ":92B::EXCH//USD/BEF/1,3456789012345\n" +
                    ":16S:CASHMOVE\n" +
                    ":70E::ADTX//x\n" +
                    ":70E::TXNR//x\n" +
                    ":16S:CAOPTN\n" +
                    "-}";

    public static void main(String[] args) {
        /*
         * Parse the source message
         */
        final MT564 source = MT564.parse(sample);

        /*
         * Create an instance of the proper translator
         */
        MT564_MxSeev03900202_Translation translator = new MT564_MxSeev03900202_Translation();

        try {

            // The precondition being check is:
            // IF ((B2[*]\97a Account\SAFE\97C\Account Code ContainsString 'GENR') And ((B2[*]\95a Party\ACOW IsPresent) Or ((B2[*]\94a Place\SAFE IsPresent) Or (B2[*]\93a Balance IsPresent))))
            // this pseudo-code is available in the translator javadoc
            List<PreconditionError> p = translator.preconditionsCheck(source);

            if (p.isEmpty()) {
                /*
                 * Call the translation process
                 */
                final MxSeev03900202 mx = translator.translate(source);

                /*
                 * Print content from the translated message:
                 *
                 * Cancellation Reason Code: PROC
                 * Processing Completness: COMP
                 * Corporate Action Id: FOOBAR
                 * Event Type Code: DVCA
                 * Underlying Security ISIN: JAC9E9G9I9K9
                 */
                System.out.println("Cancellation Reason Code: " + mx.getCorpActnCxlAdvc().getCxlAdvcGnlInf().getCxlRsnCd().name());
                System.out.println("Processing Completness: " + mx.getCorpActnCxlAdvc().getCxlAdvcGnlInf().getPrcgSts().getEvtSts().getEvtCmpltnsSts().name());
                System.out.println("Corporate Action Id: " + mx.getCorpActnCxlAdvc().getCorpActnGnlInf().getCorpActnEvtId());
                System.out.println("Event Type Code: " + mx.getCorpActnCxlAdvc().getCorpActnGnlInf().getEvtTp().getCd().name());
                System.out.println("Underlying Security ISIN: " + mx.getCorpActnCxlAdvc().getCorpActnGnlInf().getUndrlygSctyId().getISIN());

            } else {
                /*
                 * Print precondition errors
                 */
                for (PreconditionError e : p) {
                    System.out.println("precondition error: " + e.getCode() + " " + e.getDescription());
                }
            }

        } catch (final LogicalMessageCriteriaException e1) {
            System.out.println("logical message criteria exception: " + e1.getMessage());
        }
    }
}
