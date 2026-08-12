/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.io.IOException;
import java.util.Objects;

/**
 * Converts a proprietary JSON document into an MT202, using the SIMPLE sheet of
 * {@code /myformat/json2mt.xls}.
 *
 * <p>Source (JSON) selectors are dot separated paths. Target (MT) selectors follow the pattern
 * {@code FIELD/COMPONENT}, so {@code 32A/1} is the value date and {@code 32A/3} the amount, while
 * {@code 72/Line[1]} addresses one line of a multiline field and {@code b2/MessageType} a header.
 * A literal in the source column, quoted as {@code "202"} or {@code "NOREF"}, writes a constant.
 *
 * <p>The sheet also shows the transformation pipeline, several of them chained per row:
 * {@code prepend()}, {@code upperCase()}, {@code Replace()}, {@code stripToNull()},
 * {@code formatDateTime()} and {@code formatMTDecimal()}. The {@code UPDATE} mode adds a component
 * to the field created by the preceding row instead of creating a new field.
 *
 * <p>This is the flat case. {@link Json2MtExample2} reads from a JSON array,
 * {@link Json2MtExample3} generates repetitive sequences, and {@link Json2MtExample5} nests them.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MtExample1 {

    public static void main(String[] args) throws IOException {
        final String jsonInput = "{" + "  'Document': {"
                + "    'GnlInf': {"
                + "      'SndrMsgRef': 12345,"
                + "      'FuncOfMsg': 'NEWM',"
                + "      'CreDtTm': {"
                + "        'DtTm': '2015-08-27T08:59:00'"
                + "      }"
                + "    },"
                + "    'PmtInf': {"
                + "      'PmtRef': {"
                + "        'PmtId': 20150827000000"
                + "      },"
                + "      'DbtrDtls': {"
                + "        'MmbId': 99,"
                + "        'PngAgt': {"
                + "          'CshAcct': '12345-67890-12345',"
                + "          'BIC': 'FOOOUSPAXXX'"
                + "        }"
                + "      },"
                + "      'CdtrDtls': {"
                + "        'MmbId': 123,"
                + "        'PngAgt': {"
                + "          'BIC': 'FOOPUSPW'"
                + "        }"
                + "      },"
                + "      'PmtDtls': {"
                + "        'SttlmDt': '2015-08-27',"
                + "        'StsCd': 21,"
                + "        'CshTxTp': 19,"
                + "        'SttlmAmt': 1234.56,"
                + "        'Ccy': 'USD',"
                + "        'AddnlInf': 'FOO text ZYX8764'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MtExample1.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable table = loader.load("SIMPLE", FileFormat.JSON, FileFormat.MT);
        final String mt = MyFormatEngine.translate(jsonInput, table);
        System.out.println(mt);
    }
}
