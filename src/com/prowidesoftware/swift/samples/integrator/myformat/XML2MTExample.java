/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

/**
 * This example shows how to convert and XML into an MT
 * using API from Prowide Integrator MyFormat module.
 * <br>
 * The mapping rules in this example are loaded from an
 * Excel spreadsheet.
 * 
 * @author sebastian@prowidesoftware.com
 * @since 7.8
 */
public class XML2MTExample {
	
	public static void main(String[] args) {
		/*
		 * Create de mapping table instance
		 */
		MappingTable table = new MappingTable(FileFormat.XML, FileFormat.MT);
		/*
		 * Load mapping rules from Excel
		 */
		MappingTable.loadFromSpreadsheet(XML2MTExample.class.getResourceAsStream("sample.xls"), null, table);
		
		/*
		 * Source message content
		 */
		final String source = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
		        "<Document xmlns=\"urn:foo:xsd:sample.001.01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
		        "<GnlInf>\n" +
		        "   <SndrMsgRef>000012345</SndrMsgRef>\n" +
		        "   <FuncOfMsg>NEWM</FuncOfMsg>\n" +
		        "   <CreDtTm>\n" +
		        "     <DtTm>2015-08-27T08:59:00</DtTm>\n" +
		        "   </CreDtTm>\n" +
		        "</GnlInf>\n" +
		        "<PmtInf>\n" +
		        "   <PmtRef>\n" +
		        "      <PmtId>20150827000000</PmtId>\n" +
		        "   </PmtRef>\n" +
		        "   <DbtrDtls>\n" +
		        "      <MmbId>0099</MmbId>\n" +
		        "      <PngAgt>\n" +
		        "         <CshAcct>12345-67890-12345</CshAcct>\n" +
		        "       <BIC>FOOOUSPAXXX</BIC>\n" +
		        "      </PngAgt>\n" +
		        "   </DbtrDtls>\n" +
		        "   <CdtrDtls>\n" +
		        "     <MmbId>0123</MmbId>\n" +
		        "     <PngAgt>\n" +
		        "        <BIC>FOOPUSPW</BIC>\n" +
		        "     </PngAgt>\n" +
		        "   </CdtrDtls>\n" +
		        "   <PmtDtls>\n" +
		        "      <SttlmDt>2015-08-27</SttlmDt>\n" +
		        "      <StsCd>21</StsCd>\n" +
		        "      <CshTxTp>19</CshTxTp>\n" +
		        "      <SttlmAmt Ccy=\"USD\">1234.56</SttlmAmt>\n" +
		        "      <AddnlInf>FOO text ABC1234</AddnlInf>\n" +
		        "   </PmtDtls>\n" +
		        "</PmtInf>\n" +
		        "</Document>";
		
		/*
		 * Call translation
		 */
		final String mt = MyFormatEngine.translate(source, table);
		
		/*
		 * Print result:
		 * 
		 * {1:F01TESTBEBBAXXX0000000000}{2:I202TESTBEBBXBILN}{4:
		 *  :20:RE20150827000000
		 *  :21:NOREF
		 *  :32A:150827USD1234,56
		 *  :52A:/123456789012345
		 *  FOOOUSPAXXX
		 *  :58A:FOOPUSPW
		 *  :72:/NBPSTAT/19
		 *  //FOO text ABC1234
		 * -}
		 */
		System.out.println(mt);
	}
	
}
