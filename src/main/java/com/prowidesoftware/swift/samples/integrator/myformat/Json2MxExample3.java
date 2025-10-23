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

class Json2MxExample3 {

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
                + "        'CREDIT': {"
                + "          'CDTR_REF_NUM': 22222222,"
                + "          'CURRENCY_CODE': 'INR'"
                + "        }"
                + "      },"
                + "      {"
                + "        'BANK_ACCOUNT_NUM': 33333333,"
                + "        'VALUE_DATE': '2019-03-06',"
                + "        'CREDIT': ["
                + "          {"
                + "            'CDTR_REF_NUM': 44444444,"
                + "            'CURRENCY_CODE': 'USD'"
                + "          },"
                + "          {"
                + "            'CDTR_REF_NUM': 55555555,"
                + "            'CURRENCY_CODE': 'EUR'"
                + "          }"
                + "        ]"
                + "      }"
                + "    ]"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MxExample3.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("REPLICATED");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }

}
