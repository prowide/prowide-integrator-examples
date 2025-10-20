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
 * Test creating an MT from a simple JSON.
 */
class Json2MtExample2 {

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


        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MtExample2.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable table = loader.load("SIMPLE", FileFormat.JSON, FileFormat.MT);
        final String mt = MyFormatEngine.translate(jsonInput, table);
        System.out.println(mt);
    }
}
