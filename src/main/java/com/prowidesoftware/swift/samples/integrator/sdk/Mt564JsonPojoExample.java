/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt5xx.MT564;

/**
 * Round trip between MT564, its POJO model and its canonical JSON representation.
 *
 * <p>This example uses only the message model API (no mapping table involved), and it is the
 * simplest option when the JSON structure can be the standard one produced by Prowide, meaning a
 * JSON with the same structure as the MT (headers plus the list of fields with their components):
 *
 * <ol>
 *     <li>FIN to POJO with {@link MT564#parse(String)}, including typed access to sequences and fields</li>
 *     <li>POJO to JSON with {@link MT564#toJson()}</li>
 *     <li>JSON back to POJO with {@link MT564#fromJson(String)}</li>
 *     <li>POJO to FIN with {@code message()}</li>
 *     <li>Creation of a new MT564 from scratch with the sequences API</li>
 * </ol>
 *
 * <p>If instead of this canonical JSON you need to read or produce your own proprietary JSON
 * structure, use a mapping table from the MyFormat module. See {@code Json2MtExample4} and
 * {@code Mt2JsonExample2} in the myformat samples package.
 *
 * <p>Requires the Prowide Integrator SDK module.
 */
public class Mt564JsonPojoExample {

    private static final String FIN = "{1:F01BANKBEBBAXXX0000000000}{2:I564BANKDEFFXXXXN}{4:\n" + ":16R:GENL\n"
            + ":20C::CORP//ABC123456789\n"
            + ":20C::SEME//DEF987654321\n"
            + ":23G:NEWM\n"
            + ":22F::CAEV//DVCA\n"
            + ":22F::CAMV//MAND\n"
            + ":98A::PREP//20260810\n"
            + ":16S:GENL\n"
            + ":16R:USECU\n"
            + ":35B:ISIN US1234567890\n"
            + "FOO CORP ORD SHS\n"
            + ":16R:ACCTINFO\n"
            + ":97A::SAFE//123456789\n"
            + ":93B::ELIG//UNIT/1000,\n"
            + ":16S:ACCTINFO\n"
            + ":16S:USECU\n"
            + ":16R:CADETL\n"
            + ":98A::XDTE//20260901\n"
            + ":98A::RDTE//20260902\n"
            + ":16S:CADETL\n"
            + ":16R:CAOPTN\n"
            + ":13A::CAON//001\n"
            + ":22F::CAOP//CASH\n"
            + ":11A::OPTN//USD\n"
            + ":16R:CASHMOVE\n"
            + ":22H::CRDB//CRED\n"
            + ":19B::ENTL//USD1500,\n"
            + ":98A::PAYD//20260915\n"
            + ":16S:CASHMOVE\n"
            + ":16S:CAOPTN\n"
            + "-}";

    public static void main(String[] args) {

        /*
         * 1) FIN to POJO
         */
        MT564 mt = MT564.parse(FIN);

        /*
         * The POJO gives typed access to the message headers, sequences and fields.
         *
         * Prints:
         *  sender: BANKBEBBAXXX
         *  receiver: BANKDEFFXXXX
         *  function: NEWM
         *  reference: DEF987654321
         *  ISIN: US1234567890
         *  ex date: 20260901
         *  entitled amount: USD 1500,
         */
        System.out.println("sender: " + mt.getSender());
        System.out.println("receiver: " + mt.getReceiver());
        System.out.println("function: " + mt.getField23G().getFunction());

        MT564.SequenceA sequenceA = mt.getSequenceA();
        Field20C reference = (Field20C) sequenceA.getFieldByName(Field20C.NAME, "SEME");
        System.out.println("reference: " + reference.getReference());

        Field35B security = (Field35B) mt.getSequenceB().getFieldByName(Field35B.NAME);
        System.out.println("ISIN: " + security.getISIN());

        Field98A exDate = (Field98A) mt.getSequenceD().getFieldByName(Field98A.NAME, "XDTE");
        System.out.println("ex date: " + exDate.getDate());

        for (MT564.SequenceE2 cashMove : mt.getSequenceE2List()) {
            Field19B entitled = (Field19B) cashMove.getFieldByName(Field19B.NAME, "ENTL");
            System.out.println("entitled amount: " + entitled.getCurrencyCode() + " " + entitled.getAmount());
        }

        /*
         * 2) POJO to JSON
         *
         * The canonical JSON contains the three headers plus the text block fields, with each field
         * split into its components. For example the field 20C above is serialized as:
         *
         *  {
         *    "name": "20C",
         *    "qualifier": "SEME",
         *    "reference": "DEF987654321"
         *  }
         */
        String json = mt.toJson();
        System.out.println(json);

        /*
         * 3) JSON to POJO, and 4) POJO back to FIN
         *
         * Prints: round trip preserved: true
         */
        MT564 fromJson = MT564.fromJson(json);
        // the generated FIN uses CRLF as line separator, thus the normalization before comparing
        System.out.println(
                "round trip preserved: " + FIN.equals(fromJson.message().replace("\r\n", "\n")));

        /*
         * 5) Creating a new MT564 from your own model objects
         *
         * The sequence classes create the 16R/16S boundaries for you. Notice the fields are added
         * as tags with Field#asTag, so the sequence is created with its content in a single call.
         *
         * Prints:
         *  {1:F01BANKBEBBAXXX0000000000}{2:I564BANKDEFFXXXXN}{4:
         *  :16R:GENL
         *  :20C::CORP//ABC123456789
         *  :20C::SEME//DEF987654321
         *  :23G:NEWM
         *  :22F::CAEV//DVCA
         *  :16S:GENL
         *  :16R:USECU
         *  :35B:ISIN US1234567890
         *  FOO CORP ORD SHS
         *  :16S:USECU
         *  -}
         */
        MT564 created = new MT564("BANKBEBBAXXX", "BANKDEFFXXXX");
        created.append(MT564.SequenceA.newInstance(
                new Field20C().setQualifier("CORP").setReference("ABC123456789").asTag(),
                new Field20C().setQualifier("SEME").setReference("DEF987654321").asTag(),
                new Field23G().setFunction("NEWM").asTag(),
                new Field22F().setQualifier("CAEV").setIndicator("DVCA").asTag()));
        created.append(MT564.SequenceB.newInstance(new Field35B()
                .setQualifier("ISIN")
                .setISIN("US1234567890")
                .setDescription("FOO CORP ORD SHS")
                .asTag()));
        System.out.println(created.message());
    }
}
