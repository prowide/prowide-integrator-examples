package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.model.mx.MxType;
import com.prowidesoftware.swift.myformat.*;
import com.prowidesoftware.swift.myformat.csv.CsvFileReader;
import com.prowidesoftware.swift.myformat.mx.MxWriter;

import java.util.List;

/**
 * This example shows how to convert a CSV into an MX using API from Prowide Integrator MyFormat module.
 *
 * <p>In this example the mapping rules are defined programmatically instead of loading them from an Excel.
 *
 * <p>In each row in the source CSV is used to produce a repetitive element in the message output; conversion is 1 to n.
 * We use for this a special feature in the CsvFileReader that provides a variable with the current row iteration. The
 * same could be achieve with FOREACH functions in the mapping rules.
 */
public class Csv2MxExample5 {

    public static void main(String[] args) {
        // Create the mapping table
        MappingTable t = new MappingTable(FileFormat.CSV, FileFormat.MX);

        // Sample source content
        String input =
                "10;11;US;FR763000301100000205504727;14\n" +
                "20;21;KW;;24";

        // Sample external content in a variable name
        String name = "company name";

        // Create the reader, using a custom split separator and defining a variable for the iteratino count
        CsvFileReader reader = new CsvFileReader(input);
        reader.setSplitChar(';');
        reader.setRowNumberVariable("myRow");

        // We define the mapping rules programmatically instead of loading it from an Excel file
        t.add(new MappingRule("0", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/PmtId/InstrId"));
        t.add(new MappingRule("1", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/PmtId/EndToEndId", new Transformation(Transformation.Key.leftPad, 6, "0")));
        t.add(new MappingRule("2", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/Cdtr/PstlAdr/Ctry"));
        t.add(new MappingRule("3", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/CdtrAcct/Id/IBAN"));
        t.add(new MappingRule("4", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/CdtrAcct/Id/Othr/Id"));
        t.add(new MappingRule("\"" + name + "\"", "/Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[{myRow}]/Cdtr/Nm"));

        if (t.validate().isEmpty()) {

            // Create writer and call translation
            MxWriter writer = new MxWriter(MxType.pain_001_001_03);
            MyFormatEngine.translate(reader, writer, t.getRules());

            // Get result directly from writer
            // The output has one CdtTrfTxInf element per row in the input CSV
            MxPain00100103 mx = (MxPain00100103) writer.mx();
            System.out.println(mx.message());
        }

        reader.close();
    }

}
