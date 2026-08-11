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
 * Converts JSON into an MX using the REPLICATED sheet of {@code /myformat/json2mx.xls}.
 *
 * <p>The placeholders here are uppercase, {@code [{N}]} and {@code [{M}]}, which replicates the
 * outer value for each inner occurrence instead of consuming the two indexes in lockstep. In the
 * input below the first payment carries a single {@code CREDIT} object and the second one carries an
 * array of two, and both shapes are handled by the same rules.
 *
 * <p>Note the amount is not mapped by this sheet, only the currency attribute is, so the resulting
 * {@code InstdAmt} elements come out empty. {@link Json2MxExample4} is the variant that fills them,
 * by sourcing the value from a payment level value set.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MxExample3 {

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

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MxExample3.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("REPLICATED");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }
}
