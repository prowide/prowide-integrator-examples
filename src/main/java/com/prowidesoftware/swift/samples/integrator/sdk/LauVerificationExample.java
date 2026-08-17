/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.lau.AuthParameters;
import com.prowidesoftware.swift.lau.LAU;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * This example verifies LAU signatures on messages received from Alliance Access or Alliance Lite2.
 * <p>
 * While {@link Mt_LAU_DOSPPC_Example} and {@link Mx_DataPDU_LAU_Example} show the signing side,
 * this one covers the receiving side: the LAU trailer computed by the counterparty is checked with
 * the shared keys, detecting any tampering of the message in transit. The same API pair exists for
 * DataPDU XML v2 ({@code signXmlV2}/{@code verifyXmlV2}), FileAct and binary prefixed payloads.
 *
 * @since 7.10.8
 */
public class LauVerificationExample {

    // LAU is computed over the raw content, so the FIN must use CR/LF line breaks
    public static final String sample = "{1:F01FOOSGBR0AXXX0000000000}{2:I103FOORECV0XXXXN}{4:\r\n"
            + ":20:REFERENCE\r\n"
            + ":23B:CRED\r\n"
            + ":32A:260818USD100,\r\n"
            + ":50K:/12345678\r\n"
            + "ORDERING CUSTOMER\r\n"
            + ":59:/98765432\r\n"
            + "BENEFICIARY\r\n"
            + ":71A:OUR\r\n"
            + "-}";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // the two key halves shared with the SAA / Lite2 instance
        AuthParameters keys = new AuthParameters("Abcd1234Abcd1234", "Efgh5678Efgh5678");

        LAU lau = new LAU();

        // sign, simulating the message as it would be received with its LAU trailer
        AbstractMT signed = lau.sign(AbstractMT.parse(sample), keys);
        System.out.println(signed.message());

        // verify the signature with the same keys
        System.out.println("Signature valid: " + lau.verify(signed, keys));

        // any modification of the signed content invalidates the signature
        signed.getSwiftMessage().getBlock4().getTagByName("32A").setValue("260818USD900000,");
        System.out.println("Signature valid after tampering: " + lau.verify(signed, keys));
    }
}
