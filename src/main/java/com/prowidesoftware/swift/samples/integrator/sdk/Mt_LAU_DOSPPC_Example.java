/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.io.PPCWriter;
import com.prowidesoftware.swift.lau.AuthParameters;
import com.prowidesoftware.swift.lau.LAU;
import com.prowidesoftware.swift.model.mt.AbstractMT;

import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * This example uses the SDK API add the LAU signature to a couple of MT messages.
 * The signed messages are then written into a DOS-PPC batch file for SAA.
 */
public class Mt_LAU_DOSPPC_Example {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        List<AbstractMT> messages = new ArrayList<>();
        messages.add(AbstractMT.parse(fin1));
        messages.add(AbstractMT.parse(fin2));

        LAU signer = new LAU();
        AuthParameters authParameters = new AuthParameters();
        authParameters.setLeftKey("Abcd1234abcd1234");
        authParameters.setRightKey("Abcd1234abcd1234");

        StringWriter ppcContent = new StringWriter();

        for (AbstractMT message : messages) {
            signer.sign(message, authParameters);
            PPCWriter.write(message, ppcContent);
        }

        System.out.printf(ppcContent.toString());
    }

    private static String fin1 = "{1:F01AAAABRS0AXXX8683497519}{2:O1031535051028BBBBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:11111}{119:STP}{121:4086d27c-e724-4e12-8a73-b450ec6b2f94}}{4:\r\n" +
            ":20:1111111\r\n" +
            ":23B:CRED\r\n" +
            ":23E:SDVA\r\n" +
            ":32A:061028EUR100000,\r\n" +
            ":33B:EUR100000,\r\n" +
            ":50K:/12345678\r\n" +
            "MARKET AGENTS FOO ABCD123\r\n" +
            "AV XXXXX 123 BIS 9 PL\r\n" +
            "12345 BARCELONA\r\n" +
            ":52A:/2337\r\n" +
            "BCCRCRSJXXX\r\n" +
            ":53A:BCCRCRSJXXX\r\n" +
            ":57A:BNPCFR21XXX\r\n" +
            ":59:/ES0123456789012345671234\r\n" +
            "FOO AGENTES DE BOLSA ASOC\r\n" +
            ":71A:OUR\r\n" +
            ":72:/BNF/TRANSF. BCO. FOO\r\n" +
            "-}";

    private static String fin2 = "{1:F01AAAABRS0AXXX8683497523}{2:O1031535051028BBBBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:2222}{119:STP}{121:4086d27c-e724-4e12-8a73-b450ec6b2f94}}{4:\r\n" +
            ":20:222222222\r\n" +
            ":23B:CRED\r\n" +
            ":23E:SDVA\r\n" +
            ":32A:061028EUR100000,\r\n" +
            ":33B:EUR100000,\r\n" +
            ":50K:/12345678\r\n" +
            "MARKET AGENTS FOO ABCD123\r\n" +
            "AV XXXXX 123 BIS 9 PL\r\n" +
            "12345 BARCELONA\r\n" +
            ":52A:/2337\r\n" +
            "BCCRCRSJXXX\r\n" +
            ":53A:BCCRCRSJXXX\r\n" +
            ":57A:BNPCFR21XXX\r\n" +
            ":59:/ES0123456789012345671234\r\n" +
            "FOO AGENTES DE BOLSA ASOC\r\n" +
            ":71A:OUR\r\n" +
            ":72:/BNF/TRANSF. BCO. FOO\r\n" +
            "-}";

}
