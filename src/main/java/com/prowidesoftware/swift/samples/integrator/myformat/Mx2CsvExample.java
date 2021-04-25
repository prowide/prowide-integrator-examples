/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.myformat.*;

import java.io.IOException;

/**
 * This example shows how to convert and MX with multiple payments records into a CSV with one payment per line using
 * API from Prowide Integrator MyFormat module.
 *
 * <p>The mapping rules in this example are defined programmatically.
 */
public class Mx2CsvExample {
    public static void main(String[] args) throws IOException {
        // source MX is pain.001.001.03 including two payment info,

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Doc:Document xmlns:Doc=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">\n" +
                "   <Doc:CstmrCdtTrfInitn>\n" +
                "       <Doc:PmtInf>\n" +
                "           <Doc:DbtrAcct>\n" +
                "               <Doc:Id>\n" +
                "                   <Doc:IBAN>DE12345678901234567890</Doc:IBAN>\n" +
                "               </Doc:Id>\n" +
                "           </Doc:DbtrAcct>\n" +
                "           <Doc:CdtTrfTxInf>\n" +
                "               <Doc:Amt>\n" +
                "                   <Doc:InstdAmt Ccy=\"USD\" >1234.56</Doc:InstdAmt>\n" +
                "               </Doc:Amt>\n" +
                "               <Doc:CdtrAcct>\n" +
                "                   <Doc:Id>\n" +
                "                       <Doc:IBAN>US12345678901234567890</Doc:IBAN>\n" +
                "                   </Doc:Id>\n" +
                "               </Doc:CdtrAcct>\n" +
                "           </Doc:CdtTrfTxInf>\n" +
                "       </Doc:PmtInf>\n" +
                "       <Doc:PmtInf>\n" +
                "           <Doc:DbtrAcct>\n" +
                "               <Doc:Id>\n" +
                "                   <Doc:IBAN>ES12345678901234567890</Doc:IBAN>\n" +
                "               </Doc:Id>\n" +
                "           </Doc:DbtrAcct>\n" +
                "           <Doc:CdtTrfTxInf>\n" +
                "               <Doc:Amt>\n" +
                "                   <Doc:InstdAmt Ccy=\"USD\" >2345.67</Doc:InstdAmt>\n" +
                "               </Doc:Amt>\n" +
                "               <Doc:CdtrAcct>\n" +
                "                   <Doc:Id>\n" +
                "                       <Doc:IBAN>CA12345678901234567890</Doc:IBAN>\n" +
                "                   </Doc:Id>\n" +
                "               </Doc:CdtrAcct>\n" +
                "           </Doc:CdtTrfTxInf>\n" +
                "           <Doc:CdtTrfTxInf>\n" +
                "               <Doc:Amt>\n" +
                "                   <Doc:InstdAmt Ccy=\"EUR\" >6789.10</Doc:InstdAmt>\n" +
                "               </Doc:Amt>\n" +
                "               <Doc:CdtrAcct>\n" +
                "                   <Doc:Id>\n" +
                "                       <Doc:IBAN>JP12345678901234567890</Doc:IBAN>\n" +
                "                   </Doc:Id>\n" +
                "               </Doc:CdtrAcct>\n" +
                "           </Doc:CdtTrfTxInf>\n" +
                "       </Doc:PmtInf>\n" +
                "   </Doc:CstmrCdtTrfInitn>\n" +
                "</Doc:Document>";

        MxPain00100103 mx = MxPain00100103.parse(xml);

        System.out.println(mx.message());

        MappingTable t = new MappingTable(FileFormat.MX, FileFormat.CSV);

        // Initialize target CSV with one row per ignore repetition CdtTrfTxInf in source MX.
        // Since PmtInf is also repetitive, we could have one row per PmtInf as well, it just depend on how we want to
        // serialize the MX nested structure into the CSV rows.
        t.add(new MappingRule("FOREACH(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf, \",,,\")",
                "addRow", WriteMode.SETUP));

        // ignore repetition of PmtInf
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{n}]/CdtrAcct/Id/IBAN)", "1"));
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{m}]/Amt/InstdAmt)", "2"));
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{m}]/Amt/InstdAmt/@Ccy)", "3"));
        // then for each CdtTrfTxInf use relative path to get the parent debtor account
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{m}]/, ../DbtrAcct/Id/IBAN)", "0"));

        // group by PmtInf DbtrAcct
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf[n]/DbtrAcct/Id/IBAN)", "0"));
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf[n:/DbtrAcct/Id/IBAN]/CdtTrfTxInf[m]/CdtrAcct/Id/IBAN)", "1"));
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf[n:/DbtrAcct/Id/IBAN]/CdtTrfTxInf[m]/Amt/InstdAmt)", "2"));
        t.add(new MappingRule("foreach(/Document/CstmrCdtTrfInitn/PmtInf[n:/DbtrAcct/Id/IBAN]/CdtTrfTxInf[m]/Amt/InstdAmt/@Ccy)", "3"));

        // call translation
        final String csv = MyFormatEngine.translate(mx.message(), t);

        // print the created output (this could be written into a file)
        System.out.println(csv);

        // expected output is:
        // DE12345678901234567890,US12345678901234567890,1234.56,USD
        // ES12345678901234567890,CA12345678901234567890,2345.67,USD
        // ES12345678901234567890,JP12345678901234567890,6789.10,EUR
    }

}
