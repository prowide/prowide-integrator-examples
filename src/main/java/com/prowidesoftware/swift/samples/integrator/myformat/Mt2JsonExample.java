/*
 * Copyright (c) 2023 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.*;

public class Mt2JsonExample {

    public static void main(String[] args) {
        // Mapping definition
        MappingTable t = new MappingTable(FileFormat.MT, FileFormat.JSON);

        t.add(new MappingRule("20", "document.header.foo", WriteMode.CREATE));
        t.add(new MappingRule("32A/3", "document.transaction.amount", new Transformation(Transformation.Key.formatDecimal)));

        // sample source message
        final String source = "{1:F01FOOXXXG0AXXX0000000000}{2:I202FOOXXXG0XXXXN}{3:{108:MXX2108235411166}{121:4f2f1ac3-c435-ddd2a-b81b-a6ac2ee00b89}}{4:\n" +
                ":20:DEF5240030930382\n" +
                ":21:ABCX2X0737SCXXGX\n" +
                ":32A:234705BWP68608,55\n" +
                ":53D:/12345678890\n" +
                "FOO BANK XXX\n" +
                ":57A:AAAAXXXG0XXX\n" +
                ":58A:/0987654321\n" +
                "BBBBXX20TSY\n" +
                "-}";

        // translation call passing a pre-filled JSON writer instance
        String json = MyFormatEngine.translate(source, t);
        System.out.println(json);
    }
}
