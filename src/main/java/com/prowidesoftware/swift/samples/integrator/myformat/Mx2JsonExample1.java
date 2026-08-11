/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.util.Objects;

/**
 * Converts an MX (pain.001) into a proprietary JSON document, using the SIMPLE sheet of
 * {@code /myformat/mx2json.xls}.
 *
 * <p>Source (MX) selectors are absolute paths, {@code /Document/CstmrCdtTrfInitn/...}, with
 * {@code [{n}]} index placeholders to walk repetitive elements and {@code @Ccy} to read an
 * attribute. Target (JSON) selectors are dot separated paths.
 *
 * <p>This sheet mixes indexed and non indexed selectors, so some repetitions collapse: the two
 * {@code InstdAmt} of the second payment end up concatenated into a single value. Compare with
 * {@link Mx2JsonExample3}, whose FOREACH sheet keeps every repetition as its own entry, and with
 * {@link Mx2JsonExample2}, which expresses these same rules using relative selectors.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Mx2JsonExample1 {

    public static void main(String[] args) {

        String mx = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Doc:Document xmlns:Doc=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">"
                + "  <Doc:CstmrCdtTrfInitn>"
                + "    <Doc:GrpHdr>"
                + "      <Doc:MsgId>12345</Doc:MsgId>"
                + "      <Doc:NbOfTxs>3</Doc:NbOfTxs>"
                + "      <Doc:CtrlSum>5555.55</Doc:CtrlSum>"
                + "    </Doc:GrpHdr>"
                +
                // PAYMENT 1
                "    <Doc:PmtInf>"
                + "      <Doc:DbtrAcct>"
                + "        <Doc:Id>"
                + "          <Doc:IBAN>DE12345678901234567890</Doc:IBAN>"
                + "        </Doc:Id>"
                + "      </Doc:DbtrAcct>"
                +
                // PAYMENT 1 - TRANSACTION 1
                "      <Doc:CdtTrfTxInf>"
                + "        <Doc:PmtId>"
                + "          <Doc:EndToEndId>11</Doc:EndToEndId>"
                + "        </Doc:PmtId>"
                + "        <Doc:Amt>"
                + "          <Doc:InstdAmt Ccy=\"USD\">1111.11</Doc:InstdAmt>"
                + "        </Doc:Amt>"
                + "        <Doc:CdtrAcct>"
                + "          <Doc:Id>"
                + "            <Doc:IBAN>US12345678901234567890</Doc:IBAN>"
                + "          </Doc:Id>"
                + "        </Doc:CdtrAcct>"
                + "      </Doc:CdtTrfTxInf>"
                + "    </Doc:PmtInf>"
                +
                // PAYMENT 2
                "    <Doc:PmtInf>"
                + "      <Doc:DbtrAcct>"
                + "        <Doc:Id>"
                + "          <Doc:Othr>"
                + "            <Doc:Id>9999</Doc:Id>"
                + "          </Doc:Othr>"
                + "        </Doc:Id>"
                + "      </Doc:DbtrAcct>"
                +
                // PAYMENT 2 - TRANSACTION 1
                "      <Doc:CdtTrfTxInf>"
                + "        <Doc:PmtId>"
                + "          <Doc:EndToEndId>21</Doc:EndToEndId>"
                + "        </Doc:PmtId>"
                + "        <Doc:Amt>"
                + "          <Doc:InstdAmt Ccy=\"USD\">2222.11</Doc:InstdAmt>"
                + "        </Doc:Amt>"
                + "        <Doc:CdtrAcct>"
                + "          <Doc:Id>"
                + "            <Doc:IBAN>CA12345678901234567890</Doc:IBAN>"
                + "          </Doc:Id>"
                + "        </Doc:CdtrAcct>"
                + "      </Doc:CdtTrfTxInf>"
                +
                // PAYMENT 2 - TRANSACTION 2
                "      <Doc:CdtTrfTxInf>"
                + "        <Doc:PmtId>"
                + "          <Doc:EndToEndId>22</Doc:EndToEndId>"
                + "        </Doc:PmtId>"
                + "        <Doc:Amt>"
                + "          <Doc:InstdAmt Ccy=\"EUR\">2222.22</Doc:InstdAmt>"
                + "        </Doc:Amt>"
                + "        <Doc:CdtrAcct>"
                + "          <Doc:Id>"
                + "            <Doc:IBAN>JP12345678901234567890</Doc:IBAN>"
                + "          </Doc:Id>"
                + "        </Doc:CdtrAcct>"
                + "      </Doc:CdtTrfTxInf>"
                + "    </Doc:PmtInf>"
                + "  </Doc:CstmrCdtTrfInitn>"
                + "</Doc:Document>";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Mx2JsonExample1.class.getResourceAsStream("/myformat/mx2json.xls")));
        MappingTable table = loader.load("SIMPLE");
        String json = MyFormatEngine.translate(mx, table);
        JsonElement jsonElement = JsonParser.parseString(json);
        String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
        System.out.println(formattedJson);
    }
}
