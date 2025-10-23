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

class Mx2JsonExample2 {

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

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Mx2JsonExample2.class.getResourceAsStream("/myformat/mx2json.xls")));
        MappingTable table = loader.load("RELATIVE");
        String json = MyFormatEngine.translate(mx, table);
        JsonElement jsonElement = JsonParser.parseString(json);
        String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
        System.out.println(formattedJson);
    }

}
