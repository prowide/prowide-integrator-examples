/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.*;

import java.io.IOException;

/**
 * Test creating an MT from a simple JSON.
 */
class Json2MtExample1 {

    public static void main(String[] args) throws IOException {
        MappingTable table = new MappingTable(FileFormat.JSON, FileFormat.MT);
        table.add(new MappingRule(
                "document.foo",
                "20",
                new Transformation(Transformation.Key.replace, " ", ""),
                new Transformation(Transformation.Key.upperCase),
                new Transformation(Transformation.Key.prepend, "Hi ")));
        table.add(new MappingRule("\" 1234\"", "20", WriteMode.APPEND));
        table.add(new MappingRule("\"NOREF\"", "21"));
        table.add(new MappingRule("bad.path", "16"));
        assert (table.validate().isEmpty());

        final String jsonInput = "{'document': {'foo': 'hello world'}}";
        final String mt = MyFormatEngine.translate(jsonInput, table);
        System.out.println(mt);
    }
}
