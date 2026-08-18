/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.AbstractSwiftMessage;
import com.prowidesoftware.swift.model.AbstractSwiftMessageFactory;
import com.prowidesoftware.swift.model.FileFormat;
import com.prowidesoftware.swift.utils.Lib;
import java.io.IOException;
import java.util.List;

/**
 * This example shows the universal message factory, the recommended entry point when the content
 * format is not known in advance: it detects whether the source is FIN, RJE, MT core XML, SAA
 * XML v2 (DataPDU), MX (with or without envelope) or JSON, and creates the corresponding message
 * objects.
 * <p>
 * The same content is processed here in three formats: a FIN MT103, an RJE batch with two
 * messages, and an MX pacs.008. The factory returns {@code AbstractSwiftMessage} instances
 * (MtSwiftMessage or MxSwiftMessage), the model used for persistence and generic processing.
 *
 * @since 7.8.4
 */
public class SwiftMessageFactoryFormatsExample {

    public static void main(String[] args) throws IOException {
        String fin = Lib.readResource("mt103.txt");
        String rje = fin + "\n$\n" + fin;
        String mx = Lib.readResource("pacs.008.001.07.xml");

        for (String content : new String[] {fin, rje, mx}) {

            FileFormat format = AbstractSwiftMessageFactory.detectFormat(content);
            System.out.println("Detected format: " + format);

            // an RJE or DataPDU source can produce several messages
            List<AbstractSwiftMessage> messages = AbstractSwiftMessageFactory.createMessages(content);
            for (AbstractSwiftMessage message : messages) {
                System.out.println("  " + message.getClass().getSimpleName() + " " + message.getIdentifier() + " from "
                        + message.getSender() + " to " + message.getReceiver());
            }
        }
    }
}
