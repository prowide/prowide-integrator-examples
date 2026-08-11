/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.MtSwiftMessage;
import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mx.MxPacs00800107;
import com.prowidesoftware.swift.utils.Lib;
import java.io.IOException;

/**
 * Demonstrates the JSON representations available for MT and MX messages.
 *
 * <p>The example produces four distinct JSON shapes: the parsed MT fields, the persistence
 * model for MT (FIN text plus metadata), the parsed MX content, and the persistence model
 * for MX. Useful when integrating with REST APIs or document stores.</p>
 *
 * <p>Requires the Prowide Integrator SDK module.</p>
 */
public class JsonExample {

    public static void main(String[] args) throws IOException {
        // Json for MTnnn classes with text block content parsed into individuals fields
        String json1 = MT103.parse(Lib.readResource("mt103.txt")).toJson();

        // Json for the persistence model for MT, including the raw FIN content plus metadata
        String json2 = new MtSwiftMessage(Lib.readResource("mt103.txt")).toJson();

        // Json with specific structure for elements of the parsed MX (does not includes header data)
        String json3 =
                MxPacs00800107.parse(Lib.readResource("pacs.008.001.07.xml")).toJson();

        // Json for the persistence model for MX, including the raw XML content plus metadata
        String json4 = new MxSwiftMessage(Lib.readResource("pacs.008.001.07.xml")).toJson();

        System.out.println("--- MT parsed into fields ---");
        System.out.println(json1);
        System.out.println("--- MT persistence model ---");
        System.out.println(json2);
        System.out.println("--- MX parsed content ---");
        System.out.println(json3);
        System.out.println("--- MX persistence model ---");
        System.out.println(json4);
    }
}
