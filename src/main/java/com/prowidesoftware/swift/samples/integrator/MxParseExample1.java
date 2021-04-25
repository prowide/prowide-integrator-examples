/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator;

import com.prowidesoftware.swift.model.mx.MxCamt04800103;

/**
 * This examples shows how to parse and read content from an MX messages when the message type is known.
 *
 * <p>Running this will produce:<br>
 *
 * <pre>
 * Message Identification: 001
 * Amount: 1234.0
 * </pre>
 *
 * @since 7.6
 */
public class MxParseExample1 {

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Doc:Document xmlns:Doc=\"urn:swift:xsd:camt.048.001.03\" xmlns:xsi=\"httDoc://www.w3.org/2001/XMLSchema-instance\">\n"
                + "  <Doc:ModfyRsvatn>\n"
                + "    <Doc:MsgHdr>\n"
                + "      <Doc:MsgId>001</Doc:MsgId>\n"
                + "    </Doc:MsgHdr>\n"
                + "    <Doc:RsvatnId>\n"
                + "      <Doc:Cur>\n"
                + "        <Doc:Tp>\n"
                + "          <Doc:Cd>CARE</Doc:Cd>\n"
                + "        </Doc:Tp>\n"
                + "      </Doc:Cur>\n"
                + "    </Doc:RsvatnId>\n"
                + "    <Doc:NewRsvatnValSet>\n"
                + "      <Doc:Amt>\n"
                + "        <Doc:AmtWthtCcy>1234.0</Doc:AmtWthtCcy>\n"
                + "      </Doc:Amt>\n"
                + "    </Doc:NewRsvatnValSet>\n"
                + "  </Doc:ModfyRsvatn>\n"
                + "</Doc:Document>";

        // parse the XML message content
        MxCamt04800103 camt48 = MxCamt04800103.parse(xml);

        // access message data from the java model
        System.out.println("Message Identification: " + camt48.getModfyRsvatn().getMsgHdr().getMsgId());
        System.out.println("Amount: " + camt48.getModfyRsvatn().getNewRsvatnValSet().getAmt().getAmtWthtCcy());
    }

}
