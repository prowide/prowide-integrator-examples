/*
 * Copyright (c) httDoc://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import com.prowidesoftware.swift.model.mx.MxCamt04800103;

/**
 * Running this will produce:
 * 
 * <pre>
 * MsgId: 001
 * CARE... CARE
 * </pre>
 * 
 * @since 7.6
 */
public class MxReadExample {

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
	        + "        <Doc:AmtWthtCcy>0.0</Doc:AmtWthtCcy>\n"
	        + "      </Doc:Amt>\n" + "    </Doc:NewRsvatnValSet>\n" + "  </Doc:ModfyRsvatn>\n" + "</Doc:Document>";

	/*
	 * This API is not final, a more friendly API will be released, it is
	 * only provided for early tests
	 */
	MxCamt04800103 camt48 = MxCamt04800103.parse(xml);

	/*
	 * aceess message data from the java model
	 */
	System.out.println("MsgId: " + camt48.getModfyRsvatn().getMsgHdr().getMsgId());
	System.out.println("CARE... " + camt48.getModfyRsvatn().getRsvatnId().getCur().getTp().getCd());
    }
}
