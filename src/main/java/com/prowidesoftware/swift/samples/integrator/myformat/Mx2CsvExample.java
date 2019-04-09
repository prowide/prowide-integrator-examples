/*******************************************************************************
 * Copyright (c) 2019 Prowide Inc.
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

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;

import java.io.IOException;

/**
 * This example shows how to convert and MX with multiple payments records into a CSV
 * with one payment per line using API from Prowide Integrator MyFormat module.
 * <br>
 * The mapping rules in this example are defined programmatically.
 *
 * @since 7.10.9
 */
public class Mx2CsvExample {
    public static void main(String[] args) throws IOException {
        // source MX is pain.001.001.03 including two payment info,

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                " <Doc:Document xmlns:Doc=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">\n" +
                "  <Doc:CstmrCdtTrfInitn>\n" +
                "   <Doc:PmtInf>\n" +
                "    <Doc:DbtrAcct>\n" +
                "     <Doc:Id>\n" +
                "      <Doc:IBAN>DE12345678901234567890</Doc:IBAN>\n" +
                "     </Doc:Id>\n" +
                "    </Doc:DbtrAcct>\n" +
                "   \n" +
                "    <Doc:CdtTrfTxInf>\n" +
                "     <Doc:Amt>\n" +
                "      <Doc:InstdAmt Ccy=\"USD\" >1234.56</Doc:InstdAmt>\n" +
                "     </Doc:Amt>\n" +
                "    \n" +
                "     <Doc:CdtrAcct>\n" +
                "      <Doc:Id>\n" +
                "       <Doc:IBAN>US12345678901234567890</Doc:IBAN>\n" +
                "      </Doc:Id>\n" +
                "     </Doc:CdtrAcct>\n" +
                "    </Doc:CdtTrfTxInf>\n" +
                "   </Doc:PmtInf>\n" +
                "  \n" +
                "   <Doc:PmtInf>\n" +
                "    <Doc:DbtrAcct>\n" +
                "     <Doc:Id>\n" +
                "      <Doc:IBAN>ES12345678901234567890</Doc:IBAN>\n" +
                "     </Doc:Id>\n" +
                "    </Doc:DbtrAcct>\n" +
                "   \n" +
                "    <Doc:CdtTrfTxInf>\n" +
                "     <Doc:Amt>\n" +
                "      <Doc:InstdAmt Ccy=\"USD\" >2345.67</Doc:InstdAmt>\n" +
                "     </Doc:Amt>\n" +
                "    \n" +
                "     <Doc:CdtrAcct>\n" +
                "      <Doc:Id>\n" +
                "       <Doc:IBAN>CA12345678901234567890</Doc:IBAN>\n" +
                "      </Doc:Id>\n" +
                "     </Doc:CdtrAcct>\n" +
                "    </Doc:CdtTrfTxInf>\n" +
                "   \n" +
                "    <Doc:CdtTrfTxInf>\n" +
                "     <Doc:Amt>\n" +
                "      <Doc:InstdAmt Ccy=\"EUR\" >6789.10</Doc:InstdAmt>\n" +
                "     </Doc:Amt>\n" +
                "    \n" +
                "     <Doc:CdtrAcct>\n" +
                "      <Doc:Id>\n" +
                "       <Doc:IBAN>JP12345678901234567890</Doc:IBAN>\n" +
                "      </Doc:Id>\n" +
                "     </Doc:CdtrAcct>\n" +
                "    </Doc:CdtTrfTxInf>\n" +
                "   </Doc:PmtInf>\n" +
                "  </Doc:CstmrCdtTrfInitn>\n" +
                " </Doc:Document>";

        MxPain00100103 mx = MxPain00100103.parse(xml);

        System.out.println(mx.message());

        MappingTable t = new MappingTable(FileFormat.MX, FileFormat.CSV);
        //WIP
    }

}
