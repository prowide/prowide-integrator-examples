/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

/**
 * This example shows how to convert and XML into an MT using API from Prowide Integrator MyFormat module.
 *
 * <p>The mapping rules in this example are loaded from an Excel spreadsheet.
 */
public class Xml2MtExample1 {

    public static void main(String[] args) {
        /*
         * Create de mapping table instance
         */
        MappingTable table = new MappingTable(FileFormat.XML, FileFormat.MT);
        /*
         * Load mapping rules from Excel
         */
        MappingTable.loadFromSpreadsheet(Xml2MtExample1.class.getResourceAsStream("/myformat/xml2mt.xls"), "example1", table);

        /*
         * Source message content
         */
        final String source = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<Payload xmlns=\"urn:foo:xsd:sample.001.01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
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
                "        <CshAcct>123423423423</CshAcct>\n" +
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
                "</Payload>";

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
