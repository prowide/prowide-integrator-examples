/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.google.gson.Gson;
import com.prowidesoftware.swift.myformat.*;
import com.prowidesoftware.swift.myformat.Transformation.Key;

import java.util.List;

/**
 * Converts an MT564 into a proprietary JSON document, and from there into your own POJO model.
 *
 * <p>This is the reverse of {@link Json2MtExample}, and the sequenced (ISO 15022) counterpart of
 * {@link Mt2JsonExample}, which uses a simpler MT103 style message.
 *
 * <p>Source (MT) selectors follow the pattern {@code SEQUENCE/FIELD/QUALIFIER/COMPONENT}, where the
 * sequence and the qualifier are optional. This is the natural way to address content in messages
 * with repetitive sequences and qualified fields: {@code E2/19B/ENTL/3} reads the amount component
 * of the field {@code :19B::ENTL//USD1500,} located in sequence E2 (CASHMOVE). The keywords
 * {@code sender} and {@code receiver} read the message headers.
 *
 * <p>Target (JSON) selectors are dot separated paths, with optional array indexes such as
 * {@code corporateAction.options[1].type}.
 *
 * <p>If the JSON structure you need is the standard Prowide one (headers plus the list of fields
 * with their components) you do not need a mapping table at all, just call {@code toJson()} and
 * {@code fromJson(json)} on the message class. See {@code Mt564JsonPojoExample} in the sdk samples
 * package.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Mt2JsonExample2 {

    private static final String SOURCE = "{1:F01BANKBEBBAXXX0000000000}{2:I564BANKDEFFXXXXN}{4:\n" +
            ":16R:GENL\n" +
            ":20C::CORP//ABC123456789\n" +
            ":20C::SEME//DEF987654321\n" +
            ":23G:NEWM\n" +
            ":22F::CAEV//DVCA\n" +
            ":22F::CAMV//MAND\n" +
            ":98A::PREP//20260810\n" +
            ":16S:GENL\n" +
            ":16R:USECU\n" +
            ":35B:ISIN US1234567890\n" +
            "FOO CORP ORD SHS\n" +
            ":16R:ACCTINFO\n" +
            ":97A::SAFE//123456789\n" +
            ":93B::ELIG//UNIT/1000,\n" +
            ":16S:ACCTINFO\n" +
            ":16S:USECU\n" +
            ":16R:CADETL\n" +
            ":98A::XDTE//20260901\n" +
            ":98A::RDTE//20260902\n" +
            ":16S:CADETL\n" +
            ":16R:CAOPTN\n" +
            ":13A::CAON//001\n" +
            ":22F::CAOP//CASH\n" +
            ":11A::OPTN//USD\n" +
            ":16R:CASHMOVE\n" +
            ":22H::CRDB//CRED\n" +
            ":19B::ENTL//USD1500,\n" +
            ":98A::PAYD//20260915\n" +
            ":16S:CASHMOVE\n" +
            ":16S:CAOPTN\n" +
            "-}";

    public static void main(String[] args) {

        /*
         * Mapping definition, from the MT564 fields into the proprietary JSON structure
         */
        MappingTable t = new MappingTable(FileFormat.MT, FileFormat.JSON);

        // headers
        t.add(new MappingRule("sender", "corporateAction.sender"));
        t.add(new MappingRule("receiver", "corporateAction.receiver"));

        // sequence A - general information
        t.add(new MappingRule("A/20C/CORP/2", "corporateAction.corpReference"));
        t.add(new MappingRule("A/20C/SEME/2", "corporateAction.messageReference"));
        t.add(new MappingRule("A/23G/1", "corporateAction.function"));
        t.add(new MappingRule("A/22F/CAEV/3", "corporateAction.eventType"));
        t.add(new MappingRule("A/22F/CAMV/3", "corporateAction.eventMandatory"));
        t.add(new MappingRule("A/98A/PREP/2", "corporateAction.preparationDate",
                new Transformation(Key.formatDateTime, "yyyyMMdd", "yyyy-MM-dd")));

        // sequence B and B2 - underlying securities and account information
        t.add(new MappingRule("B/35B/2", "corporateAction.security.isin"));
        t.add(new MappingRule("B/35B/3", "corporateAction.security.description"));
        t.add(new MappingRule("B2/97A/SAFE/2", "corporateAction.security.safekeepingAccount"));
        t.add(new MappingRule("B2/93B/ELIG/5", "corporateAction.security.eligibleBalance",
                new Transformation(Key.formatDecimal)));

        // sequence D - corporate action details
        t.add(new MappingRule("D/98A/XDTE/2", "corporateAction.dates.exDate",
                new Transformation(Key.formatDateTime, "yyyyMMdd", "yyyy-MM-dd")));
        t.add(new MappingRule("D/98A/RDTE/2", "corporateAction.dates.recordDate",
                new Transformation(Key.formatDateTime, "yyyyMMdd", "yyyy-MM-dd")));
        t.add(new MappingRule("E2/98A/PAYD/2", "corporateAction.dates.paymentDate",
                new Transformation(Key.formatDateTime, "yyyyMMdd", "yyyy-MM-dd")));

        // sequences E and E2 - corporate action option and its cash movement
        t.add(new MappingRule("E/13A/CAON/2", "corporateAction.options[1].number"));
        t.add(new MappingRule("E/22F/CAOP/3", "corporateAction.options[1].type"));
        t.add(new MappingRule("E/11A/OPTN/2", "corporateAction.options[1].currency"));
        t.add(new MappingRule("E2/22H/CRDB/2", "corporateAction.options[1].creditDebit"));
        t.add(new MappingRule("E2/19B/ENTL/3", "corporateAction.options[1].grossAmount",
                new Transformation(Key.formatDecimal)));

        /*
         * Validate the mapping rules syntax
         */
        List<String> problems = t.validate();
        for (String problem : problems) {
            System.out.println(problem);
        }

        /*
         * Translation call
         *
         * The produced document follows the structure declared in the target selectors (shown here
         * formatted for readability, the engine produces it compact):
         *
         * {
         *   "corporateAction": {
         *     "sender": "BANKBEBBAXXX",
         *     "receiver": "BANKDEFFXXXX",
         *     "corpReference": "ABC123456789",
         *     "messageReference": "DEF987654321",
         *     "function": "NEWM",
         *     "eventType": "DVCA",
         *     "eventMandatory": "MAND",
         *     "preparationDate": "2026-08-10",
         *     "security": {
         *       "isin": "US1234567890",
         *       "description": "FOO CORP ORD SHS",
         *       "safekeepingAccount": "123456789",
         *       "eligibleBalance": "1000.0"
         *     },
         *     "dates": {
         *       "exDate": "2026-09-01",
         *       "recordDate": "2026-09-02",
         *       "paymentDate": "2026-09-15"
         *     },
         *     "options": [
         *       {
         *         "number": "001",
         *         "type": "CASH",
         *         "currency": "USD",
         *         "creditDebit": "CRED",
         *         "grossAmount": "1500.0"
         *       }
         *     ]
         *   }
         * }
         */
        String json = MyFormatEngine.translate(SOURCE, t);
        System.out.println(json);

        /*
         * And from the JSON into your own POJO model, with the JSON library of your choice
         *
         * Prints: DEF987654321 DVCA US1234567890 1500.0
         */
        Payload payload = new Gson().fromJson(json, Payload.class);
        System.out.println(payload.corporateAction.messageReference
                + " " + payload.corporateAction.eventType
                + " " + payload.corporateAction.security.isin
                + " " + payload.corporateAction.options.get(0).grossAmount);
    }

    /**
     * Minimal POJO model matching the JSON structure declared in the mapping table above.
     */
    public static class Payload {
        public CorporateAction corporateAction;
    }

    public static class CorporateAction {
        public String sender;
        public String receiver;
        public String corpReference;
        public String messageReference;
        public String function;
        public String eventType;
        public String eventMandatory;
        public String preparationDate;
        public Security security;
        public Dates dates;
        public List<Option> options;
    }

    public static class Security {
        public String isin;
        public String description;
        public String safekeepingAccount;
        public String eligibleBalance;
    }

    public static class Dates {
        public String exDate;
        public String recordDate;
        public String paymentDate;
    }

    public static class Option {
        public String number;
        public String type;
        public String currency;
        public String creditDebit;
        public String grossAmount;
    }
}
