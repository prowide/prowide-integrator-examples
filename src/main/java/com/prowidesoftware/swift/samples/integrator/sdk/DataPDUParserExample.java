/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.AbstractSwiftMessage;
import com.prowidesoftware.swift.model.mx.AbstractMX;
import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.utils.Lib;
import com.prowidesoftware.swift.wrappers.saa.v2_0_14.DataPDUParser;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Parses a SWIFT Alliance Access (SAA) DataPDU envelope and extracts the wrapped business
 * message (an MX {@code pacs.008.001.08} in this sample).
 *
 * <p>The example reads a pre-canned DataPDU XML from the classpath, asserts a handful of
 * header fields (message identifier, priority, expiry, sender), then surfaces the
 * underlying MX so that domain-level fields (for example the inter-bank settlement amount)
 * can be inspected.</p>
 *
 * <p>Requires the Prowide Integrator SDK module.</p>
 */
public class DataPDUParserExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String xml = Lib.readResource("pacs008-finplus.xml");
        DataPDUParser pdu = DataPDUParser.parse(xml);

        // header fields, read straight off the envelope
        System.out.println(
                "Message identifier : " + pdu.getHeader().getMessage().getMessageIdentifier());
        System.out.println("Sumid              : "
                + pdu.getHeader().getMessage().getInterfaceInfo().getSumid());
        System.out.println("Priority           : "
                + pdu.getHeader().getMessage().getNetworkInfo().getPriority());
        System.out.println(
                "Expiry             : " + pdu.getHeader().getMessage().getExpiryDateTime());
        System.out.println("PDU type           : " + pdu.type());

        // the envelope wraps a message, extracted here as the generic persistence model
        AbstractSwiftMessage msg = pdu.extractMessage();
        System.out.println("Is MX              : " + msg.isMX());
        System.out.println("Sender             : " + msg.getSender());
        System.out.println("Identifier         : " + msg.getIdentifier());

        // and here as the typed MX model, which gives access to the domain fields
        AbstractMX mx = pdu.extractMx();
        System.out.println("AppHdr from        : " + mx.getAppHdr().from());

        MxPacs00800108 pacs = (MxPacs00800108) mx;
        BigDecimal settlementAmount = pacs.getFIToFICstmrCdtTrf()
                .getCdtTrfTxInf()
                .get(0)
                .getIntrBkSttlmAmt()
                .getValue();
        System.out.println("Settlement amount  : " + settlementAmount);

        System.out.println();
        System.out.println(mx.message());
    }
}
