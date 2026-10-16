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
import com.prowidesoftware.swift.myformat.ReadMode;
import com.prowidesoftware.swift.myformat.SetupCommand;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.WriteMode;

/**
 * This example shows how to remove elements from an MX message with the DELETE write mode.
 * <p>
 * The table enables {@code seedFromSource}, so the output is the source pacs.008, header and namespace prefixes
 * included, minus the deleted elements. A DELETE target is an element selector, leaf or aggregate, absolute
 * ({@code /Document/...}, {@code /AppHdr/...}) or an inner path ({@code //Purp}); the whole subtree of the matched
 * element is removed.
 * <p>
 * Ancestors left empty go too: deleting both {@code AdrLine} of the creditor removes its {@code PstlAdr}, which has
 * nothing else. An ancestor with other children or attributes is kept, and the root element always stays.
 * <p>
 * As in any other DELETE rule, the source with its transformations is an optional guard; here the remittance
 * information is removed only for salary payments. The guard reads the source message, while the delete is
 * resolved against the target as left by the earlier rules, so the guard still sees the {@code Purp} that the
 * previous rule removed from the target.
 * <p>
 * Neither the validator nor the writer checks that the message stays schema valid after a delete: removing a
 * mandatory element is reported by the downstream validation.
 *
 * @since 10.3.18
 */
public class MxDeleteExample {

    private static final String TX = "/Document/FIToFICstmrCdtTrf/CdtTrfTxInf";

    public static final String xml = "<RequestPayload>\n"
            + "<h:AppHdr xmlns:h=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n"
            + "  <h:Fr><h:FIId><h:FinInstnId><h:BICFI>AAAAUSC0XXX</h:BICFI></h:FinInstnId></h:FIId></h:Fr>\n"
            + "  <h:To><h:FIId><h:FinInstnId><h:BICFI>BBBBGB2LXXX</h:BICFI></h:FinInstnId></h:FIId></h:To>\n"
            + "  <h:BizMsgIdr>REF20261016</h:BizMsgIdr>\n"
            + "  <h:MsgDefIdr>pacs.008.001.08</h:MsgDefIdr>\n"
            + "  <h:CreDt>2026-10-16T10:00:00Z</h:CreDt>\n"
            + "</h:AppHdr>\n"
            + "<Doc:Document xmlns:Doc=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n"
            + "  <Doc:FIToFICstmrCdtTrf>\n"
            + "    <Doc:GrpHdr>\n"
            + "      <Doc:MsgId>REF20261016</Doc:MsgId>\n"
            + "      <Doc:CreDtTm>2026-10-16T10:00:00</Doc:CreDtTm>\n"
            + "      <Doc:NbOfTxs>1</Doc:NbOfTxs>\n"
            + "      <Doc:SttlmInf><Doc:SttlmMtd>INDA</Doc:SttlmMtd></Doc:SttlmInf>\n"
            + "    </Doc:GrpHdr>\n"
            + "    <Doc:CdtTrfTxInf>\n"
            + "      <Doc:PmtId><Doc:EndToEndId>E2E20261016</Doc:EndToEndId></Doc:PmtId>\n"
            + "      <Doc:IntrBkSttlmAmt Ccy=\"EUR\">1000</Doc:IntrBkSttlmAmt>\n"
            + "      <Doc:IntrBkSttlmDt>2026-10-16</Doc:IntrBkSttlmDt>\n"
            + "      <Doc:ChrgBr>SHAR</Doc:ChrgBr>\n"
            + "      <Doc:Dbtr><Doc:Nm>JOHN DOE</Doc:Nm></Doc:Dbtr>\n"
            + "      <Doc:DbtrAgt><Doc:FinInstnId><Doc:BICFI>AAAAUSC0XXX</Doc:BICFI></Doc:FinInstnId></Doc:DbtrAgt>\n"
            + "      <Doc:CdtrAgt><Doc:FinInstnId><Doc:BICFI>BBBBGB2LXXX</Doc:BICFI></Doc:FinInstnId></Doc:CdtrAgt>\n"
            + "      <Doc:Cdtr>\n"
            + "        <Doc:Nm>JANE SMITH</Doc:Nm>\n"
            + "        <Doc:PstlAdr>\n"
            + "          <Doc:AdrLine>1 HIGH STREET</Doc:AdrLine>\n"
            + "          <Doc:AdrLine>LONDON</Doc:AdrLine>\n"
            + "        </Doc:PstlAdr>\n"
            + "      </Doc:Cdtr>\n"
            + "      <Doc:Purp><Doc:Cd>SALA</Doc:Cd></Doc:Purp>\n"
            + "      <Doc:RmtInf><Doc:Ustrd>OCTOBER SALARY</Doc:Ustrd></Doc:RmtInf>\n"
            + "    </Doc:CdtTrfTxInf>\n"
            + "  </Doc:FIToFICstmrCdtTrf>\n"
            + "</Doc:Document>\n"
            + "</RequestPayload>";

    public static void main(String[] args) {
        MappingTable table = new MappingTable(FileFormat.MX, FileFormat.MX);
        table.add(new MappingRule(ReadMode.LITERAL, "true", SetupCommand.seedFromSource.name(), WriteMode.SETUP));

        // a leaf, by absolute path
        table.add(new MappingRule(null, TX + "/ChrgBr", WriteMode.DELETE));
        // every AdrLine; the PstlAdr left empty goes with them, the Cdtr keeps its Nm
        table.add(new MappingRule(null, TX + "/Cdtr/PstlAdr/AdrLine", WriteMode.DELETE));
        // an aggregate, by inner path
        table.add(new MappingRule(null, "//Purp", WriteMode.DELETE));
        // guarded: the remittance information goes for salary payments only
        table.add(new MappingRule(
                TX + "/Purp/Cd", TX + "/RmtInf", WriteMode.DELETE, new Transformation(Key.ifMatches, "SALA")));

        System.out.println("Validation problems: " + table.validate());
        System.out.println(MyFormatEngine.translate(xml, table));

        /*
         * The output is the source message without ChrgBr, PstlAdr, Purp and RmtInf; the transaction now ends with
         *
         *       <Doc:Cdtr>
         *         <Doc:Nm>JANE SMITH</Doc:Nm>
         *       </Doc:Cdtr>
         *     </Doc:CdtTrfTxInf>
         */
    }
}
