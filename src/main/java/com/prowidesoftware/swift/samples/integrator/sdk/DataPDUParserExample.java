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
import com.prowidesoftware.swift.wrappers.saa.DataPDUType;
import com.prowidesoftware.swift.wrappers.saa.v2_0_14.DataPDUParser;
import com.prowidesoftware.swift.wrappers.saa.v2_0_14.Priority;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DataPDUParserExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String xml = Lib.readResource("pacs008-finplus-tested.xml");
        assert (xml != null);
        DataPDUParser pdu = DataPDUParser.parse(xml);

        assert ("pacs.008.001.08".equals(pdu.getHeader().getMessage().getMessageIdentifier()));
        assert ("2067CEF9FFFFFF8C".equals(pdu.getHeader().getMessage().getInterfaceInfo().getSumid()));
        assert (Priority.NORMAL.equals(pdu.getHeader().getMessage().getNetworkInfo().getPriority()));
        assert ("20201215141940".equals(pdu.getHeader().getMessage().getExpiryDateTime()));

        assert (DataPDUType.Message_MX.equals(pdu.type()));

        AbstractSwiftMessage msg = pdu.extractMessage();
        assert (msg.isMX());

        assert ("FOOZJOCC".equals(msg.getSender()));
        assert ("pacs.008.001.08".equals(msg.getIdentifier()));

        AbstractMX mx = pdu.extractMx();
        assert ("FOOZJOAM".equals(mx.getAppHdr().from()));

        assert (mx instanceof MxPacs00800108);
        MxPacs00800108 pacs = (MxPacs00800108) mx;
        assert (new BigDecimal("11401.50").equals(pacs.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue()));

        System.out.println(mx.message());
    }
}
