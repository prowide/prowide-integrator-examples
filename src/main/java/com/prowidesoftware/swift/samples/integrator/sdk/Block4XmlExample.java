/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mtxml.Block4Xml;
import com.prowidesoftware.swift.model.mt.mtxml.XmlBlock4;
import com.prowidesoftware.swift.utils.Lib;
import java.io.IOException;

/**
 * This example converts the text block of an MT message into a structured XML representation and
 * back.
 * <p>
 * The conversion uses the message scheme to organize the fields into their sequences, producing an
 * XML where each field is a proper element with its components split, instead of the flat FIN tag
 * list. This is useful to process MT content with standard XML tooling (XPath, XSLT, schema aware
 * editors) or to store it in XML databases.
 *
 * @since 9.2.18
 */
public class Block4XmlExample {

    public static void main(String[] args) throws IOException {
        SwiftMessage message = SwiftMessage.parse(Lib.readResource("mt103.txt"));

        // MT to XML
        String xml = new Block4Xml().getXml(message);
        System.out.println(xml);

        // and back: XML to MT
        SwiftMessage parsed = new XmlBlock4().getDocument(xml);
        System.out.println(parsed.getBlock4().getTags().size() + " fields recovered from the XML");
    }
}
