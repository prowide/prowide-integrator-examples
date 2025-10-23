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

class Mx2JsonExample3 {

    public static void main(String[] args) {

        String mx = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Doc:Document xmlns:Doc=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">"
                + "  <Doc:CstmrCdtTrfInitn>"
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
                + "          <Doc:IBAN>ES12345678901234567890</Doc:IBAN>"
                + "        </Doc:Id>"
                + "      </Doc:DbtrAcct>"
                +

                // PAYMENT 2 - TRANSACTION 1
                "      <Doc:CdtTrfTxInf>"
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

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Mx2JsonExample3.class.getResourceAsStream("/myformat/mx2json.xls")));
        MappingTable table = loader.load("FOREACH");
        String json = MyFormatEngine.translate(mx, table);
        JsonElement jsonElement = JsonParser.parseString(json);
        String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
        System.out.println(formattedJson);
    }

}
