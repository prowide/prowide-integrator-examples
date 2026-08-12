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
 * Converts JSON into an MX using the REP_VALUESET sheet of {@code /myformat/json2mx.xls}.
 *
 * <p>Same replication as {@link Json2MxExample3}, but the amount and currency are read with the
 * {@code [*]} value set selector, {@code FOREACH(root.PAYMENT[{n}].AMOUNT[*])}. That reads a single
 * payment level value and spreads it over the {@code CdtTrfTxInf} repetitions created by the credit
 * array, so the {@code InstdAmt} elements come out populated rather than empty.
 *
 * <p>The second payment shows the boundary: it has two credits but one amount, so the first
 * transfer gets the amount and the second one only gets its {@code EndToEndId}.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MxExample4 {

    public static void main(String[] args) {
        String json = "{" + "  'root': {"
                + "    'GRP_HDR': {"
                + "      'CUSTREF': 'CUSTREF',"
                + "      'NO_OF_PAYMENTS': 2"
                + "    },"
                + "    'PAYMENT': ["
                + "      {"
                + "        'BANK_ACCOUNT_NUM': 11111111,"
                + "        'VALUE_DATE': '2019-03-05',"
                + "        'CURRENCY_CODE': 'EUR',"
                + "        'AMOUNT': 2000,"
                + "        'CREDIT': {"
                + "          'CDTR_REF_NUM': 22222222"
                + "        }"
                + "      },"
                + "      {"
                + "        'BANK_ACCOUNT_NUM': 33333333,"
                + "        'VALUE_DATE': '2019-03-06',"
                + "        'CURRENCY_CODE': 'USD',"
                + "        'AMOUNT': 3000,"
                + "        'CREDIT': ["
                + "          {"
                + "            'CDTR_REF_NUM': 44444444"
                + "          },"
                + "          {"
                + "            'CDTR_REF_NUM': 55555555"
                + "          }"
                + "        ]"
                + "      }"
                + "    ]"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MxExample4.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("REP_VALUESET");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }
}
