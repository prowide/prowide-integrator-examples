/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.utils.MtSanitizer;
import java.io.IOException;

/**
 * This example cleans up an MT message that would be rejected by the SWIFT network, using the
 * sanitizer utilities. This is typical when messages are produced from back office data that does
 * not comply with the FIN constraints:
 * <ul>
 *   <li>characters outside the SWIFT X character set are replaced (here the accented name and the
 *   ampersand)</li>
 *   <li>lines starting with ':' or '-' inside a field value are escaped, since they would break
 *   the FIN block 4 parsing</li>
 * </ul>
 * The sanitizers modify the message in place. Each one accepts an optional replacement character
 * or default value.
 *
 * @since 9.2.30
 */
public class MtSanitizerExample {

    public static final String sample = "{1:F01FOOSGBR0AXXX0000000000}{2:I199FOORECV0XXXXN}{4:\n"
            + ":20:SANITIZE-TEST\n"
            + ":79:CAF\u00C9 & BAR PAYMENT DETAILS\n"
            + ":REASON LINE STARTING WITH COLON\n"
            + "-DASH LINE\n"
            + "-}";

    public static void main(String[] args) throws IOException {
        SwiftMessage message = SwiftMessage.parse(sample);
        System.out.println("Before:\n" + message.message());

        // replace invalid charset characters with the default '.'
        MtSanitizer.sanitizeCharset(message);

        // escape ':' and '-' at the beginning of value lines with the default '.'
        MtSanitizer.sanitizeStartingLineCharacter(message);

        System.out.println("After:\n" + message.message());
    }
}
