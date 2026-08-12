/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mx.MxPain00100103;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import java.util.Objects;

/**
 * Converts a proprietary JSON document into an MX (pain.001), using the SIMPLE sheet of
 * {@code /myformat/json2mx.xls}, and parses the result into the {@link MxPain00100103} model.
 *
 * <p>Source (JSON) selectors are dot separated paths, wrapped in {@code FOREACH(...)} with
 * {@code [{n}]} / {@code [{m}]} placeholders to iterate arrays. Target (MX) selectors are absolute
 * paths, and {@code @Ccy} addresses an attribute. The message type is declared in the sheet itself
 * through the {@code mxType} SETUP row, which is why no {@code MxPain00100103} needs to be created
 * up front.
 *
 * <p>This is the baseline case: nested arrays ({@code PAYMENT} containing {@code CREDIT}) driving
 * repetitive {@code PmtInf} and {@code CdtTrfTxInf}. The other three examples in this family each
 * change one thing: {@link Json2MxExample2} shortens the selectors with path aliases,
 * {@link Json2MxExample3} replicates a parent value across children, and {@link Json2MxExample4}
 * feeds a repetition from a value set.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MxExample1 {

    public static void main(String[] args) {

        String json = "{" + "  'root': {"
                + "    'GRP_HDR': {"
                + "      'CUSTREF': 'FILEREF123',"
                + "      'NO_OF_PAYMENTS': 4,"
                + "      'TOTAL_AMOUNT': 100"
                + "    },"
                + "    'PAYMENT': ["
                + "      {"
                + "        'VALUE_DATE': '2019-03-11',"
                + "        'TRANSFER_CCY_CODE': 'USD',"
                + "        'TRANSFER_AMOUNT': 30,"
                + "        'CREDIT': ["
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948198,"
                + "            'CDTR_REF_NUM': 71237456,"
                + "            'CDTR_NAME': 'Joe Doe'"
                + "          },"
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948199,"
                + "            'CDTR_REF_NUM': 71237457,"
                + "            'CDTR_NAME': 'Jane Doe'"
                + "          }"
                + "        ]"
                + "      },"
                + "      {"
                + "        'VALUE_DATE': '2019-03-11',"
                + "        'TRANSFER_CCY_CODE': 'KWD',"
                + "        'TRANSFER_AMOUNT': 20,"
                + "        'CREDIT': ["
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948198,"
                + "            'CDTR_REF_NUM': 71237456,"
                + "            'CDTR_NAME': 'Joe Doe'"
                + "          },"
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948199,"
                + "            'CDTR_REF_NUM': 71237457,"
                + "            'CDTR_NAME': 'Jane Doe'"
                + "          }"
                + "        ]"
                + "      }"
                + "    ]"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MxExample1.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("SIMPLE");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }
}
