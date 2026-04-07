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

class Json2MxExample1 {

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
                + "            'CDTR_NAME': 'Venkat'"
                + "          },"
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948199,"
                + "            'CDTR_REF_NUM': 71237457,"
                + "            'CDTR_NAME': 'Gourab'"
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
                + "            'CDTR_NAME': 'Venkat'"
                + "          },"
                + "          {"
                + "            'DBTR_BANK_ACC_NUM': 5948199,"
                + "            'CDTR_REF_NUM': 71237457,"
                + "            'CDTR_NAME': 'Gourab'"
                + "          }"
                + "        ]"
                + "      }"
                + "    ]"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MxExample1.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("SIMPLE");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }

}
