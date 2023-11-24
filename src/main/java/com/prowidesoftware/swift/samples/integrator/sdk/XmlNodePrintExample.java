/*
 * Copyright (c) 2023 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.mx.MxLabelInfo;
import com.prowidesoftware.swift.model.mx.MxTypePacs;
import com.prowidesoftware.xml.XmlNode;
import com.prowidesoftware.xml.XmlParser;
import org.apache.commons.lang3.StringUtils;

/**
 * This class processes and formats XML messages conforming to the ISO 20022 standard, specifically the
 * pacs.008.001.08 schema. It features functions for parsing XML, recursively printing node details with
 * human-readable labels, and handling string manipulations for label formatting.
 *
 * @since 9.4.12
 */
public class XmlNodePrintExample {

    static MxLabelInfo labelInfo = new MxLabelInfo(MxTypePacs.pacs_008_001_10.schema());

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">"
                + "    <FIToFICstmrCdtTrf>"
                + "        <GrpHdr>"
                + "            <MsgId>asdfsd</MsgId>"
                + "            <CreDtTm>2020-09-17T21:27:41</CreDtTm>"
                + "            <NbOfTxs>1</NbOfTxs>"
                + "            <SttlmInf>"
                + "                <SttlmMtd>INDA</SttlmMtd>"
                + "                <SttlmAcct>"
                + "                    <Id>"
                + "                        <Othr>"
                + "                            <Id>ACAABAJOESTAELCODIGO</Id>"
                + "                            <SchmeNm>"
                + "                                <!-- this code is valid for ISO 20022 and as external code set -->"
                + "                                <Cd>BBAN</Cd>"
                + "                            </SchmeNm>"
                + "                        </Othr>"
                + "                    </Id>"
                + "                </SttlmAcct>"
                + "            </SttlmInf>"
                + "        </GrpHdr>"
                + "        <CdtTrfTxInf>"
                + "            <PmtId>"
                + "                <InstrId>sdfasdfsd</InstrId>"
                + "                <EndToEndId>asdfasdfasdfasdf</EndToEndId>"
                + "                <UETR>8579f4a4-a547-463e-ae63-e7c6620d59b4</UETR>"
                + "            </PmtId>"
                + "            <IntrBkSttlmAmt Ccy=\"USD\">11111</IntrBkSttlmAmt>"
                + "            <IntrBkSttlmDt>2023-04-10</IntrBkSttlmDt>"
                + "            <ChrgBr>DEBT</ChrgBr>"
                + "            <Dbtr>"
                + "                <Nm>asdfasdf</Nm>"
                + "            </Dbtr>"
                + "            <DbtrAgt>"
                + "                <FinInstnId>"
                + "                    <BICFI>ITAUUSXXXXX</BICFI>"
                + "                </FinInstnId>"
                + "            </DbtrAgt>"
                + "            <CdtrAgt>"
                + "                <FinInstnId>"
                + "                    <BICFI>AAAAUSXXXXX</BICFI>"
                + "                </FinInstnId>"
                + "            </CdtrAgt>"
                + "            <Cdtr>"
                + "                <Nm>sdfasdfsd</Nm>"
                + "            </Cdtr>"
                + "        </CdtTrfTxInf>"
                + "    </FIToFICstmrCdtTrf>"
                + "</Document>";

        XmlNode mxMsg = XmlParser.parse(xml);
        XmlNode node = mxMsg.findFirst("/Document");

        printNode(node);
    }

    private static void printNode(XmlNode node) {
        for (XmlNode child : node.getChildren()) {
            if (child.hasChildren()) {
                printNode(child);
            } else {
                System.out.printf(
                        "%-20s %-40s %-20s%n", "<" + child.getName() + ">", getLabel(child.path()), child.getValue());
            }
        }
    }

    private static String getLabel(String path) {
        String label = labelInfo.get(path);
        if (StringUtils.isNotBlank(label)) {
            return splitByUpperCases(StringUtils.substringAfterLast(label, "/"));
        }
        return path.replace("/", "");
    }

    private static String splitByUpperCases(String inputString) {
        if (StringUtils.isNotBlank(inputString) && !inputString.equals(inputString.toUpperCase())) {
            return String.join(" ", inputString.split("(?=\\p{Upper})"))
                    .replace("B I C", "BIC")
                    .replace("F I ", "FI ")
                    .replace("Sn F", "SnF");
        }
        return inputString;
    }
}
