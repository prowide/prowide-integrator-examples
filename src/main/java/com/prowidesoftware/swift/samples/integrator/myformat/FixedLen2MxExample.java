/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.model.mx.MxTypePain;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.fixedlen.FixedLenFieldsDef;
import com.prowidesoftware.swift.myformat.fixedlen.FixedLenReader;
import com.prowidesoftware.swift.myformat.mx.MxWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This example converts a fixed-length (positional) file into an MX pain.001, a typical
 * integration with legacy back office or COBOL based systems.
 * <p>
 * The source file has a header record (HDR) and one detail record (DTL) per payment. A field
 * names dictionary maps the record positions once, so the rules use names instead of repeating
 * positions. Record types are selected with conditions such as {@code [<RECORD='DTL'>]}, and the
 * {@code fromPIC} transformation cleans each value according to its COBOL PICTURE format (stripping
 * padding and placing the decimal separator) before it is written to the target.
 *
 * @since 7.8
 */
public class FixedLen2MxExample {

    public static final String[] sample = {
        "HDRPAY2026081701       ",
        "DTLBE71096123456769              000001250050EUR",
        "DTLGB29NWBK60161331926819        000000090000USD"
    };

    public static void main(String[] args) {
        // name the record positions once: name, "start/length" (positions are one-based)
        FixedLenFieldsDef defs = new FixedLenFieldsDef()
                .addField("RECORD", "1/3")
                .addField("REFERENCE", "4/20")
                .addField("ACCOUNT", "4/30")
                .addField("AMOUNT", "34/12")
                .addField("CURRENCY", "46/3");

        List<MappingRule> rules = new ArrayList<>();

        // file reference from the header record
        rules.add(new MappingRule(
                "REFERENCE[<RECORD='HDR'>]",
                "/Document/CstmrCdtTrfInitn/GrpHdr/MsgId",
                new Transformation(Key.fromPIC, "X(20)")));

        // one payment information block per detail record
        rules.add(new MappingRule(
                "foreach(ACCOUNT[{n}<RECORD='DTL'>])",
                "/Document/CstmrCdtTrfInitn/PmtInf[{n}]/CdtTrfTxInf/CdtrAcct/Id/Othr/Id",
                new Transformation(Key.fromPIC, "X(30)")));
        rules.add(new MappingRule(
                "foreach(AMOUNT[{n}<RECORD='DTL'>])",
                "/Document/CstmrCdtTrfInitn/PmtInf[{n}]/CdtTrfTxInf/Amt/InstdAmt",
                new Transformation(Key.fromPIC, "9(10)V99")));
        rules.add(new MappingRule(
                "foreach(CURRENCY[{n}<RECORD='DTL'>])",
                "/Document/CstmrCdtTrfInitn/PmtInf[{n}]/CdtTrfTxInf/Amt/InstdAmt/@Ccy",
                new Transformation(Key.fromPIC, "X(3)")));

        // the field definitions are provided to the reader
        FixedLenReader reader = FixedLenReader.builder(sample).fieldsDef(defs).build();
        MxWriter writer = new MxWriter(MxTypePain.pain_001_001_03);

        MyFormatEngine.translate(reader, writer, rules);

        MxPain00100103 mx = (MxPain00100103) writer.mx();
        System.out.println(mx.message());
    }
}
