/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.wrappers.saa.v2_0_14.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DataPDUWriterExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ParserConfigurationException, SAXException {
        DataPDUWriter pdu = new DataPDUWriter();
        pdu.setHeader(new Header());
        pdu.getHeader().setMessage(new Message());
        pdu.getHeader().getMessage().setSenderReference("senderRef123");
        pdu.getHeader().getMessage().setFormat("File");
        pdu.getHeader().getMessage().setSecurityInfo(new SecurityInfo());
        pdu.getHeader().getMessage().getSecurityInfo().setSWIFTNetSecurityInfo(new SWIFTNetSecurityInfo());
        pdu.getHeader().getMessage().getSecurityInfo().getSWIFTNetSecurityInfo().setSignatureValue(new SwAny());

        String signatureFragment =
                "<SwInt:RequestPayload type=\"swift.fileact.secsecureddata\" xmlns:SwInt=\"urn:swift:snl:ns.SwInt\" xmlns:Sw=\"urn:swift:snl:ns.Sw\">\n"
                        + "                            <Sw:FileRequestHeader>\n"
                        + "                                <SwInt:Requestor>o=wvious6l,o=swift</SwInt:Requestor>\n"
                        + "                                <SwInt:Responder>o=ecoctgtg,o=swift</SwInt:Responder>\n"
                        + "                                <SwInt:Service>swift.corp.fa!p</SwInt:Service>\n"
                        + "                                <SwInt:RequestType>pain.xxx.cashpmt</SwInt:RequestType>\n"
                        + "                                <SwInt:Priority>Normal</SwInt:Priority>\n"
                        + "                                <SwInt:RequestRef>WLNGVZY99952</SwInt:RequestRef>\n"
                        + "                            </Sw:FileRequestHeader>\n"
                        + "                            <Sw:TransferRef>SNL50967D11595016152206253</Sw:TransferRef>\n"
                        + "                            <Sw:Digest>\n"
                        + "                                <Sw:DigestAlgorithm>SHA-256</Sw:DigestAlgorithm>\n"
                        + "                                <Sw:DigestValue>GYJ9WduXYkX2Zqn4Ou5kWRgw2ABJFXZcpd1XDxJS2QE=</Sw:DigestValue>\n"
                        + "                            </Sw:Digest>\n"
                        + "                        </SwInt:RequestPayload>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document requestPayload = builder.parse(new InputSource(new StringReader(signatureFragment)));

        pdu.getHeader()
                .getMessage()
                .getSecurityInfo()
                .getSWIFTNetSecurityInfo()
                .getSignatureValue()
                .addContent(requestPayload.getFirstChild());

        String xml = pdu.xml();
        System.out.printf(xml);

        assert (xml.contains("2.0.11"));
        assert (xml.contains("senderRef123"));
        assert (xml.contains("File"));
        assert (xml.contains("o=wvious6l,o=swift"));
    }
}
