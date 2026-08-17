/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.WriteMode;

/**
 * This example converts an MX pain.001 into a fixed-length (positional) file with a header record
 * (HDR) and one detail record (DTL) per transaction.
 * <p>
 * Two remarks specific to fixed-length as target:
 * <ul>
 *   <li>The table starts with SETUP rules using the {@code addRow} command and the {@code filler}
 *   transformation, creating the empty record structure. The detail row setup iterates the source
 *   transactions, so the structure is dynamic.</li>
 *   <li>Every value rule ends with a {@code toPIC} transformation, converting the value to its
 *   COBOL PICTURE format with proper padding.</li>
 * </ul>
 *
 * @since 7.8
 */
public class Mx2FixedLenExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">\n"
            + "  <CstmrCdtTrfInitn>\n"
            + "    <GrpHdr><MsgId>PAY2026081701</MsgId></GrpHdr>\n"
            + "    <PmtInf>\n"
            + "      <CdtTrfTxInf>\n"
            + "        <Amt><InstdAmt Ccy=\"EUR\">12500.50</InstdAmt></Amt>\n"
            + "        <CdtrAcct><Id><IBAN>BE71096123456769</IBAN></Id></CdtrAcct>\n"
            + "      </CdtTrfTxInf>\n"
            + "      <CdtTrfTxInf>\n"
            + "        <Amt><InstdAmt Ccy=\"USD\">900.00</InstdAmt></Amt>\n"
            + "        <CdtrAcct><Id><IBAN>GB29NWBK60161331926819</IBAN></Id></CdtrAcct>\n"
            + "      </CdtTrfTxInf>\n"
            + "    </PmtInf>\n"
            + "  </CstmrCdtTrfInitn>\n"
            + "</Document>";

    public static void main(String[] args) {
        MappingTable table = new MappingTable(FileFormat.MX, FileFormat.FIXEDLEN);

        // setup: one empty header row, and one empty detail row per source transaction
        table.add(new MappingRule(null, "addRow", WriteMode.SETUP, new Transformation(Key.filler, "HDR", "X(20)")));
        table.add(new MappingRule(
                "foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf)",
                "addRow",
                WriteMode.SETUP,
                new Transformation(Key.filler, "DTL", "X(30)", "9(10).99", "X(3)")));

        // header record: file reference at positions 4 to 23
        table.add(new MappingRule(
                "/Document/CstmrCdtTrfInitn/GrpHdr/MsgId",
                "4/20[<1/3='HDR'>]",
                new Transformation(Key.toPIC, "X(20)")));

        // detail records: account, amount and currency per transaction
        table.add(new MappingRule(
                "foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{n}]/CdtrAcct/Id/IBAN)",
                "4/30[{n}<1/3='DTL'>]",
                new Transformation(Key.toPIC, "X(30)")));
        table.add(new MappingRule(
                "foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{n}]/Amt/InstdAmt)",
                "34/13[{n}<1/3='DTL'>]",
                new Transformation(Key.toPIC, "9(10).99")));
        table.add(new MappingRule(
                "foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{n}]/Amt/InstdAmt/@Ccy)",
                "47/3[{n}<1/3='DTL'>]",
                new Transformation(Key.toPIC, "X(3)")));

        System.out.println(MyFormatEngine.translate(sample, table));
    }
}
