/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field16R;
import com.prowidesoftware.swift.model.field.Field16S;
import com.prowidesoftware.swift.model.mt.MtType;
import com.prowidesoftware.swift.model.mt.mt5xx.MT564;
import com.prowidesoftware.swift.myformat.*;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.json.JsonReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a proprietary JSON document into an MT564 using API from Prowide Integrator MyFormat module.
 *
 * <p>This is the reverse of {@link Mt2JsonExample2}. The mapping rules are defined programmatically,
 * but they could also be loaded from an Excel spreadsheet or a database as in {@code Xml2MtExample1}.
 *
 * <p>Source (JSON) selectors are dot separated paths into the document, with optional index or filter
 * predicates for arrays: {@code corporateAction.options[1].type}, {@code options[*].type},
 * {@code options[&lt;type='CASH'&gt;].currency}.
 *
 * <p>Target (MT) selectors follow the pattern {@code SEQUENCE/FIELD/QUALIFIER/COMPONENT}, where the
 * sequence and the qualifier are optional. This is what makes ISO 15022 messages such as the MT564
 * straightforward to write: since the writer is created for a specific message type, it knows the
 * message structure and creates the 16R/16S boundaries of each sequence for you, and it also fills
 * the qualifier component of the created fields. For example the rule targeting {@code E2/19B/ENTL/3}
 * creates the CASHMOVE sequence (if not yet created) with the field {@code :19B::ENTL//USD1500,}
 * inside. Headers are addressed with the block prefix, for instance {@code b1/LogicalTerminal} or
 * {@code b2/ReceiverAddress}.
 *
 * <p>Note the writer creates each sequence as a top level one, meaning nested subsequences such as
 * B2 (ACCTINFO, inside B) or E2 (CASHMOVE, inside E) end up as siblings of their parent sequence.
 * The last step of this example relocates them with the message model API.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MtExample4 {

    private static final String SOURCE = "{\n" + "  \"corporateAction\": {\n"
            + "    \"sender\": \"BANKBEBBAXXX\",\n"
            + "    \"receiver\": \"BANKDEFFXXXX\",\n"
            + "    \"corpReference\": \"ABC123456789\",\n"
            + "    \"messageReference\": \"DEF987654321\",\n"
            + "    \"function\": \"NEWM\",\n"
            + "    \"eventType\": \"DVCA\",\n"
            + "    \"eventMandatory\": \"MAND\",\n"
            + "    \"preparationDate\": \"2026-08-10\",\n"
            + "    \"security\": {\n"
            + "      \"isin\": \"US1234567890\",\n"
            + "      \"description\": \"FOO CORP ORD SHS\",\n"
            + "      \"safekeepingAccount\": \"123456789\",\n"
            + "      \"eligibleBalance\": \"1000.00\"\n"
            + "    },\n"
            + "    \"dates\": {\n"
            + "      \"announcementDate\": \"2026-08-05T10:30:00\",\n"
            + "      \"announcementUtcOffset\": \"-05:00\",\n"
            + "      \"exDate\": \"2026-09-01\",\n"
            + "      \"recordDate\": \"2026-09-02\",\n"
            + "      \"paymentDate\": \"2026-09-15\"\n"
            + "    },\n"
            + "    \"options\": [\n"
            + "      {\n"
            + "        \"number\": \"001\",\n"
            + "        \"type\": \"CASH\",\n"
            + "        \"currency\": \"USD\",\n"
            + "        \"creditDebit\": \"CRED\",\n"
            + "        \"grossAmount\": \"1500.00\"\n"
            + "      }\n"
            + "    ]\n"
            + "  }\n"
            + "}";

    public static void main(String[] args) {

        /*
         * Mapping definition, from the proprietary JSON structure into the MT564 fields
         */
        MappingTable t = new MappingTable(FileFormat.JSON, FileFormat.MT);

        // headers
        t.add(new MappingRule("corporateAction.sender", "b1/LogicalTerminal"));
        t.add(new MappingRule("corporateAction.receiver", "b2/ReceiverAddress"));

        // sequence A - general information
        t.add(new MappingRule("corporateAction.corpReference", "A/20C/CORP/2"));
        t.add(new MappingRule("corporateAction.messageReference", "A/20C/SEME/2"));
        t.add(new MappingRule("corporateAction.function", "A/23G/1"));
        t.add(new MappingRule("corporateAction.eventType", "A/22F/CAEV/3"));
        t.add(new MappingRule("corporateAction.eventMandatory", "A/22F/CAMV/3"));
        t.add(new MappingRule(
                "corporateAction.preparationDate",
                "A/98A/PREP/2",
                new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyyyMMdd")));

        // sequence B - underlying securities, with a literal for the 35B qualifier
        t.add(new MappingRule("\"ISIN\"", "B/35B/1"));
        t.add(new MappingRule("corporateAction.security.isin", "B/35B/2", WriteMode.UPDATE));
        t.add(new MappingRule("corporateAction.security.description", "B/35B/3", WriteMode.UPDATE));

        // sequence B2 - account information
        t.add(new MappingRule("corporateAction.security.safekeepingAccount", "B2/97A/SAFE/2"));
        t.add(new MappingRule("\"UNIT\"", "B2/93B/ELIG/3"));
        t.add(new MappingRule(
                "corporateAction.security.eligibleBalance",
                "B2/93B/ELIG/5",
                WriteMode.UPDATE,
                new Transformation(Key.formatMTDecimal)));

        // sequence D - corporate action details
        // Field 98a option E holds the date and the time (plus optional decimals and UTC offset) in
        // separate components, so a date-time source value is mapped with two rules targeting the
        // same field: the first creates :98E::ANOU// with the date component, and the second, in
        // UPDATE mode, completes the created field with the time component
        t.add(new MappingRule(
                "corporateAction.dates.announcementDate",
                "D/98E/ANOU/2",
                new Transformation(Key.formatDateTime, "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMdd")));
        t.add(new MappingRule(
                "corporateAction.dates.announcementDate",
                "D/98E/ANOU/3",
                WriteMode.UPDATE,
                new Transformation(Key.formatDateTime, "yyyy-MM-dd'T'HH:mm:ss", "HHmmss")));
        // the optional UTC offset goes in components 5 (sign, present only for negative offsets,
        // hence the ifMatches guard) and 6, producing :98E::ANOU//20260805103000/N0500. The offset
        // is a separate source field on purpose: formatDateTime would shift a date-time carrying
        // an embedded offset to the JVM default time zone
        t.add(new MappingRule(
                "corporateAction.dates.announcementUtcOffset",
                "D/98E/ANOU/5",
                WriteMode.UPDATE,
                new Transformation(Key.ifMatches, "^-"),
                new Transformation(Key.fixed, "N")));
        t.add(new MappingRule(
                "corporateAction.dates.announcementUtcOffset",
                "D/98E/ANOU/6",
                WriteMode.UPDATE,
                new Transformation(Key.removeAll, "[-+:]")));
        t.add(new MappingRule(
                "corporateAction.dates.exDate",
                "D/98A/XDTE/2",
                new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyyyMMdd")));
        t.add(new MappingRule(
                "corporateAction.dates.recordDate",
                "D/98A/RDTE/2",
                new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyyyMMdd")));

        // sequence E - corporate action option
        t.add(new MappingRule("corporateAction.options[1].number", "E/13A/CAON/2"));
        t.add(new MappingRule("corporateAction.options[1].type", "E/22F/CAOP/3"));
        t.add(new MappingRule("corporateAction.options[1].currency", "E/11A/OPTN/2"));

        // sequence E2 - cash movement
        t.add(new MappingRule("corporateAction.options[1].creditDebit", "E2/22H/CRDB/2"));
        t.add(new MappingRule("corporateAction.options[1].currency", "E2/19B/ENTL/2"));
        t.add(new MappingRule(
                "corporateAction.options[1].grossAmount",
                "E2/19B/ENTL/3",
                WriteMode.UPDATE,
                new Transformation(Key.formatMTDecimal)));
        t.add(new MappingRule(
                "corporateAction.dates.paymentDate",
                "E2/98A/PAYD/2",
                new Transformation(Key.formatDateTime, "yyyy-MM-dd", "yyyyMMdd")));

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
         * The writer is created for a specific message type, meaning the created message will have
         * its type and structure, with the fields sorted as expected by the MT564 scheme.
         */
        JsonReader reader = new JsonReader(SOURCE);
        MtWriter writer = new MtWriter(MtType.MT564);
        MyFormatEngine.translate(reader, writer, t.getRules());

        /*
         * The result is available both as an MT564 POJO and as FIN text. The subsequences ACCTINFO
         * and CASHMOVE are created next to their parent sequence, so they are relocated below.
         */
        MT564 mt = (MT564) writer.mt();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        nest(block4, "ACCTINFO", "USECU");
        nest(block4, "CASHMOVE", "CAOPTN");

        /*
         * Final message:
         *
         * {1:F01BANKBEBBAXXX0000000000}{2:I564BANKDEFFXXXXN}{4:
         * :16R:GENL
         * :20C::CORP//ABC123456789
         * :20C::SEME//DEF987654321
         * :23G:NEWM
         * :22F::CAEV//DVCA
         * :22F::CAMV//MAND
         * :98A::PREP//20260810
         * :16S:GENL
         * :16R:USECU
         * :35B:ISIN US1234567890
         * FOO CORP ORD SHS
         * :16R:ACCTINFO
         * :97A::SAFE//123456789
         * :93B::ELIG//UNIT/1000,
         * :16S:ACCTINFO
         * :16S:USECU
         * :16R:CADETL
         * :98E::ANOU//20260805103000/N0500
         * :98A::XDTE//20260901
         * :98A::RDTE//20260902
         * :16S:CADETL
         * :16R:CAOPTN
         * :13A::CAON//001
         * :22F::CAOP//CASH
         * :11A::OPTN//USD
         * :16R:CASHMOVE
         * :22H::CRDB//CRED
         * :19B::ENTL//USD1500,
         * :98A::PAYD//20260915
         * :16S:CASHMOVE
         * :16S:CAOPTN
         * -}
         */
        System.out.println(mt.message());

        /*
         * From here on it is a regular MT564 POJO, for example to set the UETR, to validate the
         * message with the Validation module, or to read its content with the typed API.
         *
         * Prints:
         *  function: NEWM
         *  options: 1
         *  cash movements: 1
         *  account information: 1
         */
        System.out.println("function: " + mt.getField23G().getFunction());
        System.out.println("options: " + mt.getSequenceEList().size());
        System.out.println("cash movements: " + mt.getSequenceE2List().size());
        System.out.println("account information: " + mt.getSequenceB2List().size());
    }

    /**
     * Moves a subsequence into its parent sequence, right before the parent closing 16S boundary.
     *
     * @param block4 the message text block to update
     * @param child  the 16R/16S qualifier of the sequence to relocate, for example ACCTINFO
     * @param parent the 16R/16S qualifier of the containing sequence, for example USECU
     */
    private static void nest(SwiftBlock4 block4, String child, String parent) {
        List<Tag> childTags = new ArrayList<>();
        List<Tag> result = new ArrayList<>();
        boolean inChild = false;
        for (Tag tag : block4.getTags()) {
            if (Field16R.NAME.equals(tag.getName()) && child.equals(tag.getValue())) {
                inChild = true;
            }
            if (inChild) {
                childTags.add(tag);
                if (Field16S.NAME.equals(tag.getName()) && child.equals(tag.getValue())) {
                    inChild = false;
                }
            } else {
                result.add(tag);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            Tag tag = result.get(i);
            if (Field16S.NAME.equals(tag.getName()) && parent.equals(tag.getValue())) {
                result.addAll(i, childTags);
                break;
            }
        }
        block4.setTags(result);
    }
}
