package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.util.List;

/**
 * This example shows how to convert and MT into a CSV using API from Prowide Integrator MyFormat module.
 * <p>
 * The mapping rules in this example are loaded from a spreadsheet.
 * <p>
 * The example takes an MT102 with repetitive content, and produces as result a CSV with different rows
 * for the repetitive information, creating also a row identifier. Data from sequence A will go to a header
 * line in the CSV, the repetitive sequences B will go to multiple transaction rows and finally data from
 * sequence C will go to a final footer row.
 */
public class Mt2CsvExample2 {

    public static void main(String[] args) throws IOException {
        // create de mapping table instance with source and target formats
        MappingTable table = new MappingTable(FileFormat.MT, FileFormat.CSV);

        // load mapping rules from Excel
        MappingTable.loadFromSpreadsheet(Xml2MtExample1.class.getResourceAsStream("/myformat/mt2csv.xls"), "example 2", table);

        // validate mapping table
        List<String> problems = table.validate();
        for (String s : problems) {
            System.out.println(s);
        }

        String fin = Lib.readResource("mt940.txt");

        // call translation
        final String csv = MyFormatEngine.translate(fin, table);

        // print the created output
        System.out.println(csv);
        /*
            HR,1234567890,0987654321,21/1,EUR,12283841.0
            TX,Apr-26,423,EUR,125000.0,Inf-229183
            TX,Apr-27,423,EUR,125000.0,Inf-229183
            TX,Apr-28,423,EUR,125000.0,Inf-229183
            TX,Apr-29,423,EUR,125000.0,Inf-229183
            TX,Apr-30,423,EUR,125000.0,Inf-229183
            TX,May-01,423,EUR,125000.0,Inf-229183
            TX,May-02,423,EUR,125000.0,Inf-229183
         */
    }
}
