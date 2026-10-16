/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.ReadMode;
import com.prowidesoftware.swift.myformat.SetupCommand;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.WriteMode;

/**
 * This example shows how to remove members and array items from a JSON document with the DELETE write mode.
 * <p>
 * The table enables {@code seedFromSource}, so the output is the source document minus the deleted elements. A
 * DELETE target is a JSON path to an object member or an array item:
 * <ul>
 *   <li>{@code order.internal} removes a member with all its content.</li>
 *   <li>{@code order.notes[1]} removes one array item. The array keeps its shape: one item left is still
 *   serialized as an array.</li>
 *   <li>{@code order.items.discount} removes that member from every item of the array.</li>
 *   <li>An object left without members is removed too, here the {@code audit} object of the meta section, up to
 *   the root object, which always stays.</li>
 * </ul>
 * Member names are matched exactly, JSON keys being case sensitive. Repetition variables and filter predicates
 * select elements when reading and are rejected as a DELETE target.
 * <p>
 * Rules run in table order, so a delete shifts the indexes seen by the later rules: after removing
 * {@code order.notes[1]}, the former second note is {@code order.notes[1]}.
 *
 * @since 10.3.18
 */
public class JsonDeleteExample {

    public static final String json = "{\n"
            + "  \"order\": {\n"
            + "    \"id\": \"ORD-4711\",\n"
            + "    \"internal\": {\"costCenter\": \"CC-12\", \"approver\": \"jdoe\"},\n"
            + "    \"notes\": [\"internal draft\", \"deliver before noon\"],\n"
            + "    \"items\": [\n"
            + "      {\"sku\": \"A-100\", \"qty\": 2, \"discount\": 5},\n"
            + "      {\"sku\": \"B-200\", \"qty\": 1, \"discount\": 0}\n"
            + "    ],\n"
            + "    \"meta\": {\"channel\": \"web\", \"audit\": {\"user\": \"jdoe\"}},\n"
            + "    \"status\": \"TEST\"\n"
            + "  }\n"
            + "}";

    public static void main(String[] args) {
        MappingTable table = new MappingTable(FileFormat.JSON, FileFormat.JSON);
        table.add(new MappingRule(ReadMode.LITERAL, "true", SetupCommand.seedFromSource.name(), WriteMode.SETUP));

        // a whole object member
        table.add(new MappingRule(null, "order.internal", WriteMode.DELETE));
        // one array item; the remaining note is still an array
        table.add(new MappingRule(null, "order.notes[1]", WriteMode.DELETE));
        // the same member in every item
        table.add(new MappingRule(null, "order.items.discount", WriteMode.DELETE));
        // the audit object is left empty and goes with its only member
        table.add(new MappingRule(null, "order.meta.audit.user", WriteMode.DELETE));
        // guarded: the status goes only when it is TEST
        table.add(new MappingRule(
                "order.status", "order.status", WriteMode.DELETE, new Transformation(Key.ifMatches, "TEST")));

        System.out.println("Validation problems: " + table.validate());
        System.out.println(MyFormatEngine.translate(json, table));

        /*
         * Output:
         *
         * {"order":{"id":"ORD-4711","notes":["deliver before noon"],"items":[{"sku":"A-100","qty":2},
         * {"sku":"B-200","qty":1}],"meta":{"channel":"web"}}}
         */
    }
}
