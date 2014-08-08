/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.io.StringWriter;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.writer.XMLWriterVisitor;

/**
 * Example of message conversion to XML representation.
 * Notice this is not SWIFT MX format, but the internal/proprietary XML representation defined at WIFE.
 * 
 * <p>running this sample will generate something like:</p>
 * <pre>

</pre> 
 * @author www.prowidesoftware.com
 */
public class MessageRichXMLConversionExample {

	public static void main(String[] args) {
		ConversionService srv = new ConversionService();
		String fin = "{1:F01BICFOOYYAXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n" + 
				":20:0061350113089908\n" + 
				":13C:/RNCTIME/1534+0000\n" + 
				":23B:CRED\n" + 
				":23E:SDVA\n" + 
				":72:/BNF/TRANSF. BCO. FOO\n" + 
				"-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}\n" + 
				"\n" ;
		
		// This will switch on a more detailed output in the xml, ideal for processing elements inside each tag
		boolean useFieldInsteadOfTag = true;
		String xml = srv.getXml(fin, useFieldInsteadOfTag);
		System.out.println(xml);
		
	}
}
