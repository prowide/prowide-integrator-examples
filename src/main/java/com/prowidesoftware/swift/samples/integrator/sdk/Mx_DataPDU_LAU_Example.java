/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.lau.AuthParameters;
import com.prowidesoftware.swift.lau.LAU;
import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.utils.Lib;
import com.prowidesoftware.swift.wrappers.saa.v2_0_13.DataPDUWriter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * This example uses the SDK API create a DataPDU envelope for an MX message, including the LAU signature to a couple of MT messages.
 * The signed messages are then written into a DOS-PPC batch file for SAA.
 */
public class Mx_DataPDU_LAU_Example {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // read a resource containing a pacs message XML
        String pacsXml = Lib.readResource("pacs.008.001.07.xml");

        // create a MxSwiftMessage from the XML
        MxSwiftMessage mx = new MxSwiftMessage(pacsXml);

        // create a specific DataPDU writer (notice the imported package determines the version)
        DataPDUWriter w = new DataPDUWriter(mx);

        // customize the default generated data
        w.getHeader().getMessage().getNetworkInfo().setService("swift.finplus!pc");

        // create the DataPDU XML
        String dataPDU = w.xml();

        // create a LAU signer with the SAA private keys
        LAU signer = new LAU();
        AuthParameters authParameters = new AuthParameters();
        authParameters.setLeftKey("Abcd1234abcd1234");
        authParameters.setRightKey("Abcd1234abcd1234");

        // sign the DataPDU
        String signedPayload = signer.signXmlV2(dataPDU, authParameters);

        System.out.println(signedPayload);
    }

}
