/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.io.printout.PrintoutWriter;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import com.prowidesoftware.swift.model.mt.mt5xx.MT515;
import com.prowidesoftware.swift.model.mt.mt5xx.MT518;
import com.prowidesoftware.swift.model.mt.mt5xx.MT535;
import com.prowidesoftware.swift.model.mt.mt6xx.MT671;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * This example shows how to generate text printout of messages generically (for any message type)
 * using the expanded printout from Prowide Integrator SDK.
 *
 * <p>The output for an MT515 looks like this:</p>
 * <pre>
 * ------------------------- Instance Type and Transmission -------------------------
 * Copy sent to SWIFT
 * Priority/Delivery : Normal
 *
 * ------------------------- Message Header -----------------------------------------
 * Swift    : FIN 515 Client Confirmation of Purchase or Sale
 * Sender   : FOOWJPJ0BXXX
 * Receiver : FOOHJPJ0XMF1
 * MUR      : FOOB3926BE868XXX
 *
 * ------------------------- Message Text -------------------------------------------
 *
 * A - General Information
 * 20C: Reference
 * Qualifier: Sender's Message Reference
 * Reference: M1999999.1
 * 23G: Function of the Message
 * Function: NEWM
 * 22F: Indicator
 * Qualifier: Trade Transaction Type
 * Indicator: TRAD
 *
 * A1 - Linkages
 * 20C: Reference
 * Qualifier: Previous Message Reference
 * Reference: M1999999.1
 *
 * C - Confirmation Details
 * 98A: Date
 * Qualifier: Trade Date/Time
 * Date: Jul 12, 2016
 * 98A: Date
 * Qualifier: Settlement Date/Time
 * Date: Jul 15, 2016
 * 90A: Price
 * Qualifier: Deal Price
 * Percentage Type Code: PRCT
 * Price: 99
 * 22H: Indicator
 * Qualifier: Buy/Sell Indicator
 * Code: BUYI
 * 22H: Indicator
 * Qualifier: Payment Indicator
 * Code: APMT
 *
 * C1 - Confirmation Parties
 * 95P: Party
 * Qualifier: Seller
 * BIC: FOOHJPJ0MF1
 * 97A: Account
 * Qualifier: Safekeeping Account
 * Account: MGT10
 *
 * C1 - Confirmation Parties
 * 95P: Party
 * Qualifier: Buyer
 * BIC: FOOXUS30XXX
 * 36B: Quantity of Financial Instrument
 * Qualifier: Quantity of Financial Instrument Confirmed
 * Quantity Type Code: FAMT
 * Quantity: 15,000,000
 * 36B: Quantity of Financial Instrument
 * Qualifier: Quantity of Financial Instrument Ordered
 * Quantity Type Code: FAMT
 * Quantity: 640,843.05
 * 35B: Identification of the Financial Instrument
 * Qualifier: ISIN
 * ISIN: US99991UUT05
 * Description: FN 999999
 * Description 2: 5
 * Description 3: 2033/01/01
 *
 * D - Settlement Details
 * 22F: Indicator
 * Qualifier: Type of Settlement Transaction Indicator
 * Indicator: TRAD
 *
 * D1 - Settlement Parties
 * 95P: Party
 * Qualifier: Delivering Agent
 * BIC: FOOKUS30XXX
 *
 * D1 - Settlement Parties
 * 95P: Party
 * Qualifier: Deliverer's Custodian
 * BIC: FOOKJPJ0XXX
 *
 * D1 - Settlement Parties
 * 95P: Party
 * Qualifier: Seller
 * BIC: FOOHJPJ0MF1
 *
 * D3 - Amounts
 * 19A: Amount
 * Qualifier: Settlement Amount
 * Currency: USD
 * Amount: 234,567
 *
 * D3 - Amounts
 * 19A: Amount
 * Qualifier: Trade Amount
 * Currency: USD
 * Amount: 123,456
 *
 * D3 - Amounts
 * 19A: Amount
 * Qualifier: Accrued Interest Amount
 * Currency: USD
 * Amount: 1,246.5
 *
 * ------------------------- Message Trailer -----------------------------------------
 * CHK: 3916EF336FF7
 * </pre>
 *
 * @since 7.8.4
 */
public class ExpandedPrintoutExample {

    /*
     * sample data
     */
    static MT103 mt103 = MT103.parse(
            "{1:F01FOOBARB0A0B20000000000}{2:I103FOOBNL20XXXXN}{3:{108:FOOB3926BE868XXX}}{4:\n" + ":20:REFERENCE\n"
                    + ":23B:CRED\n"
                    + ":32A:130204USD1234567,89\n"
                    + ":50A:/12345678901234567890\n"
                    + "FOOJHKH0XXX\n"
                    + ":59:/12345678901234567890\n"
                    + "JOE DOE\n"
                    + ":71A:OUR\n"
                    + "-}{5:{CHK:111111111111}}");
    static MT515 mt515 = MT515.parse(
            "{1:F01FOOWJPJ0BXXX0000000000}{2:I515FOOHJPJ0XMF1N}{3:{108:FOOB3926BE868XXX}}{4:\n" + ":16R:GENL\n"
                    + ":20C::SEME//M1999999.1\n"
                    + ":23G:NEWM\n"
                    + ":22F::TRTR//TRAD\n"
                    + ":16R:LINK\n"
                    + ":20C::PREV//M1999999.1\n"
                    + ":16S:LINK\n"
                    + ":16S:GENL\n"
                    + ":16R:CONFDET\n"
                    + ":98A::TRAD//20160712\n"
                    + ":98A::SETT//20160715\n"
                    + ":90A::DEAL//PRCT/99,00000000\n"
                    + ":22H::BUSE//BUYI\n"
                    + ":22H::PAYM//APMT\n"
                    + ":16R:CONFPRTY\n"
                    + ":95P::SELL//FOOHJPJ0MF1\n"
                    + ":97A::SAFE//MGT10\n"
                    + ":16S:CONFPRTY\n"
                    + ":16R:CONFPRTY\n"
                    + ":95P::BUYR//FOOXUS30XXX\n"
                    + ":16S:CONFPRTY\n"
                    + ":36B::CONF//FAMT/15000000,\n"
                    + ":36B::ORDR//FAMT/640843,05\n"
                    + ":35B:ISIN US99991UUT05\n"
                    + "FN 999999\n"
                    + "5\n"
                    + "2033/01/01\n"
                    + ":16S:CONFDET\n"
                    + ":16R:SETDET\n"
                    + ":22F::SETR//TRAD\n"
                    + ":16R:SETPRTY\n"
                    + ":95P::DEAG//FOOKUS30XXX\n"
                    + ":16S:SETPRTY\n"
                    + ":16R:SETPRTY\n"
                    + ":95P::DECU//FOOKJPJ0XXX\n"
                    + ":16S:SETPRTY\n"
                    + ":16R:SETPRTY\n"
                    + ":95P::SELL//FOOHJPJ0MF1\n"
                    + ":16S:SETPRTY\n"
                    + ":16R:AMT\n"
                    + ":19A::SETT//USD234567,00\n"
                    + ":16S:AMT\n"
                    + ":16R:AMT\n"
                    + ":19A::DEAL//USD123456,00\n"
                    + ":16S:AMT\n"
                    + ":16R:AMT\n"
                    + ":19A::ACRU//USD1246,50\n"
                    + ":16S:AMT\n"
                    + ":16S:SETDET\n"
                    + "-}{5:{CHK:111111111111}}");
    static MT940 mt940 = MT940.parse(
            "{1:F01ANASCH20AXXX0000000000}{2:I940FOOIGB20XEQUN}{3:{108:FOOB3926BE868XXX}}{4:\n" + ":20:123456\n"
                    + ":25:123-304958\n"
                    + ":28C:123/1\n"
                    + ":60F:C980622USD395212311,71\n"
                    + ":61:980623C50000000,NTRFNONREF//8951234\n"
                    + "ORDER FOO BANK WESTERN CASH RESERVE\n"
                    + ":61:980625C5700000,NFEX036960//8954321\n"
                    + ":61:980626C200000,NDIVNONREF//8846543\n"
                    + ":86:DIVIDEND FOO CORP\n"
                    + "PREFERRED STOCK 1ST QUARTER 1998\n"
                    + ":62F:C980623USD451112311,71\n"
                    + ":64:C980623USD445212311,71\n"
                    + ":65:C980625USD450912311,71\n"
                    + ":65:C980626USD451112311,71\n"
                    + ":86:PRIME RATE AS OF TODAY 11 PCT\n"
                    + "-}{5:{CHK:111111111111}}");
    static MT202 mt202 = MT202.parse(
            "{1:F01ANASCH20AXXX0000000000}{2:O2021300050901FOOSLUL0ALTA00000000000509011300N}{3:{108:FOOB3926BE868XXX}}{4:\n"
                    + ":20:0000000000000014\n"
                    + ":21:0000000000000014\n"
                    + ":13C:/RNCTIME/1356+0000\n"
                    + ":13C:/RNCTIME/1410+0000\n"
                    + ":32A:050901EUR19265,53\n"
                    + ":52A:FOOSLUL0LTA\n"
                    + ":53A:/D/1234A0123456ABC012345\n"
                    + "FOOXGBY0\n"
                    + ":54A:FOOPIT20AP8\n"
                    + ":56A:FOOFLBB0025\n"
                    + ":57A:FOOQGB20L17\n"
                    + ":58A:/ES12 1234 6789 1234 1111 1234\n"
                    + "FOOYUS30CAI\n"
                    + ":72:/BNF/00002695 0001 2005083130110\n"
                    + "-}{5:{CHK:111111111111}}");
    static MT518 mt518 = MT518.parse(
            "{1:F01ANASCH20AXXX0000000000}{2:O5180750040609LRLRXXX04A0400000000000406090954U}{4:\n" + ":16R:GENL\n"
                    + ":20C::SEME//900031\n"
                    + ":23G:NEWM\n"
                    + ":98C::PREP//19991207130605\n"
                    + ":22F::TRTR/FOOO/CASH\n"
                    + ":16S:GENL\n"
                    + ":16R:CONFDET\n"
                    + ":98C::TRAD//19991207130433\n"
                    + ":98A::SETT//19991208\n"
                    + ":90A::DEAL//PRCT/99,375\n"
                    + ":19A::SETT//USD14961933,38\n"
                    + ":22H::BUSE//BUYI\n"
                    + ":22F::PROC/FOOO/CMPR\n"
                    + ":22H::PAYM//APMT\n"
                    + ":16R:CONFPRTY\n"
                    + ":95R::BUYR/FOOO/PART0001\n"
                    + ":16S:CONFPRTY\n"
                    + ":16R:CONFPRTY\n"
                    + ":95R::SELL/FOOO/PART0002\n"
                    + ":20C::PROC//0000000000007\n"
                    + ":70E::DECL//FOOO/CORRSIMU\n"
                    + ":16S:CONFPRTY\n"
                    + ":36B::CONF//FAMT/15000000,\n"
                    + ":35B:/US/000000000\n"
                    + ":16S:CONFDET\n"
                    + "-}{5:{MAC:11111111}{CHK:222222222222}{TNG:}}");
    static MT535 mt535 = MT535.parse(
            "{1:F01FOOCUS30ADNC0000000000}{2:O5350837080313FOOKGB20IXXX00000000000803130437N}{3:{108:000000000000004}}{4:\n"
                    + ":16R:GENL\n"
                    + ":28E:6/MORE\n"
                    + ":20C::SEME//H000000000000001\n"
                    + ":23G:NEWM\n"
                    + ":98A::STAT//20080312\n"
                    + ":22F::SFRE//DAIL\n"
                    + ":22F::CODE//COMP\n"
                    + ":22F::STTY//CUST\n"
                    + ":22F::STBA//TRAD\n"
                    + ":97A::SAFE//S 02500\n"
                    + ":17B::ACTI//Y\n"
                    + ":17B::AUDT//N\n"
                    + ":17B::CONS//N\n"
                    + ":16S:GENL\n"
                    + ":16R:SUBSAFE\n"
                    + ":16R:FIN\n"
                    + ":35B:/US/AGGRAVAI\n"
                    + "AGGR=300, AVAI=200\n"
                    + ":16R:FIA\n"
                    + ":12A::CLAS/ISIT/STF\n"
                    + ":16S:FIA\n"
                    + ":93B::AGGR//FAMT/300,\n"
                    + ":93B::AVAI//FAMT/200,\n"
                    + ":16R:SUBBAL\n"
                    + ":93B::AGGR//FAMT/50,\n"
                    + ":94F::SAFE//CUST/FOOKUS30\n"
                    + ":70C::SUBB//REGISTRATION CODE FOOBAR\n"
                    + ":16S:SUBBAL\n"
                    + ":16S:FIN\n"
                    + ":16R:FIN\n"
                    + "-}{5:{MAC:11111111}{CHK:222222222222}}");
    static MT671 mt671 = MT671.parse(
            "{1:F01FOOVHUH0AXXX1000000000}{2:O6711639140102FOOLGB20DSWP00000000001401021739N}{3:{108:FOOL41847}}{4:\n"
                    + ":16R:GENL\n"
                    + ":20C::SEME//ICF0000000000001\n"
                    + ":23G:NEWM\n"
                    + ":95P::SUBM//20060913\n"
                    + ":22F::UDTP//CASH\n"
                    + ":97A::SAFE//F275\n"
                    + ":16S:GENL\n"
                    + ":16R:SSIDET\n"
                    + ":22H::SSIP//NEWS\n"
                    + ":11A::SETT//USD\n"
                    + ":98A::EFFD//20150827\n"
                    + ":22F::MARK//CASH\n"
                    + ":16R:CSHPRTY\n"
                    + ":95P::BENM//FOOSLUL0LTA\n"
                    + ":16S:CSHPRTY\n"
                    + ":16S:SSIDET\n"
                    + "-}{5:{MAC:11111111}{CHK:222222222222}}");

    public static void main(String[] args) {
        final PrintoutWriter writer = new PrintoutWriter();

        System.out.println("===== MT515 =====");
        System.out.println(writer.print(mt515));

        System.out.println("===== MT103 =====");
        System.out.println(writer.print(mt103));

        System.out.println("===== MT515 =====");
        System.out.println(writer.print(mt515));

        System.out.println("===== MT940 =====");
        System.out.println(writer.print(mt940));

        System.out.println("===== MT202 =====");
        System.out.println(writer.print(mt202));

        System.out.println("===== MT518 =====");
        System.out.println(writer.print(mt518));

        System.out.println("===== MT535 =====");
        System.out.println(writer.print(mt535));

        System.out.println("===== MT671 =====");
        System.out.println(writer.print(mt671));
    }
}
