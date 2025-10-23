/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.json.JsonReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

import java.io.IOException;
import java.util.Objects;

/**
 * Each "party" element should generate an instance of sequence E.
 * Either attribute "type" or "price" in the "party" element should generate a field 95Q in the corresponding
 * sequence E, the attribute value goes to component 1 in the field.
 * The "id" attribute in the party element must be mapped to component 2 in the corresponding 95Q field instance.
 */
class Json2MtExample3 {

    public static void main(String[] args) throws IOException {
        String jsonInput = "{ 'riskPledgeAllocation': {" + "    'taskId': 1111,"
                + "    'bussinessDate': '2019-01-03',"
                + "    'strategyId': 'XXXXXXX',"
                + "    'fundId': 'XXXXX',"
                + "    'sscSecId': 999999,"
                + "    'assetType': 'EQUITY',"
                + "    'settlementLoc': 'AAA',"
                + "    'allocatedQty': 0,"
                + "    'availableQty': 9999,"
                + "    'memoPledgeQty': 0,"
                + "    'allocatedMarketVal': 0,"
                + "    'calculatedDelta': 99,"
                + "    'eligibilityFlag': false,"
                + "    'price': 99,"
                + "    'priceCcy': 'USD',"
                + "    'lastModifiedBy': 'XXXXX',"
                + "    'pricingBasis': 1,"
                + "    'parties': {"
                + "      'party': ["
                + "        {"
                + "          'id': 'AA',"
                + "          'type': 'SELLER'"
                + "        },"
                + "        {"
                + "          'id': 'BB',"
                + "          'type': 'BUYER'"
                + "        }"
                + "      ]"
                + "    }"
                + "  }"
                + "}";


        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MtExample2.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable t = loader.load("SEQUENCE", FileFormat.JSON, FileFormat.MT);
        assert (t.validate().isEmpty());

        MtWriter writer = new MtWriter(new MT540());
        MyFormatEngine.translate(new JsonReader(jsonInput), writer, t.getRules());

        MT540 mt = (MT540) writer.mt();

        System.out.println(mt.message());
    }
}
