/*
 * Copyright (c) 2022 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.model.mx.AbstractMX;

/**
 * This examples shows how to parse an MX messages when the message type is unknown.
 *
 * <p>The generic AbstractMX is useful as parameter for other modules, such as validation or translations. You can also
 * check the message type and cast the message to a specific instance if required.
 *
 * @since 7.6
 */
public class MxParseExample2 {

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

        // parse into generic structure
        AbstractMX mx = AbstractMX.parse(xml);

        System.out.println("Namespace: " + mx.getNamespace());
    }

}
