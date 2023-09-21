/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.Objects;

/**
 * This example shows how to convert and XML into an MT using API from Prowide Integrator MyFormat module.
 *
 * <p>The mapping rules in this example are loaded from an Excel spreadsheet.
 */
public class Xml2MtExample2 {

    public static void main(String[] args) {

        // Load mapping rules from Excel
        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Xml2MtExample2.class.getResourceAsStream("/myformat/xml2mt.xls")));

        // Create a mapping table instance with source and target formats
        MappingTable table = loader.load("example2");
        table.setSourceFormat(FileFormat.XML);
        table.setTargetFormat(FileFormat.MT);

        /*
         * Source message content
         */
        final String source = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<TrdCaptRpt xmlns=\"http://www.fixprotocol.org/FIXML-5-0-SP1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "            Bk=\"JHB\" Ccy=\"USD\" CntCcy=\"ZAR\" CntLastQty=\"+147700000.00\" DealingInf=\"Siena\" ExecTyp=\"0\" LastMkt=\"14.782\"\n" +
                "            LastPx=\"14.77\" LastQty=\"+10000000.00\" ManQuot=\"1\" MsgID=\"0001645041\" SettlDt=\"2018-09-06\" TrdDt=\"2018-09-04\"\n" +
                "            TrdID=\"SCI8W40001\" TrdID2=\"209074\"\n" +
                "            xsi:schemaLocation=\"http://www.fixprotocol.org/FIXML-5-0-SP1 fixml-tradecapture-base-5-0-SP1.xsd\">\n" +
                "    <Hdr Snt=\"2018-09-05T09:50:33\"/>\n" +
                "    <Instrmt CFI=\"RCSXXX\" SubTyp=\"SP\" Sym=\"USD/ZAR\"/>\n" +
                "    <Undly ChlMgn=\"0.0\" CxMktPts=\"0.0\" Desc=\"SP\" FxRate=\"14.77\" Mat=\"2018-09-06\" MktFxRate=\"14.782\" Px=\"-0.0120\"\n" +
                "           QteBas=\"0\" RateUids=\"-1\" RevalRate=\"1.0\" Sym=\"USD/ZAR\"/>\n" +
                "    <RptSide Side=\"1\">\n" +
                "        <Pty ID=\"Tracy Jensen\" R=\"11\"/>\n" +
                "        <Pty ID=\"10X.INVESTMENTS     LRG\" R=\"13\"/>\n" +
                "        <Pty ID=\"13502\" R=\"3\"/>\n" +
                "        <Pty ID=\"SCI\" R=\"31\"/>\n" +
                "        <Pty ID=\"SCI\" R=\"53\"/>\n" +
                "        <Pty R=\"87\"/>\n" +
                "        <SettlDetails>\n" +
                "            <Pty/>\n" +
                "        </SettlDetails>\n" +
                "        <SettlDetails>\n" +
                "            <Pty/>\n" +
                "        </SettlDetails>\n" +
                "    </RptSide>\n" +
                "</TrdCaptRpt>";

        /*
         * Call translation
         */
        final String mt = MyFormatEngine.translate(source, table);

        System.out.println(mt);
    }

}
