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
 * Converts a JSON document into an MT540, using the SEQUENCE sheet of
 * {@code /myformat/json2mt.xls}, to show how repetitive sequences are generated.
 *
 * <p>Each element of the {@code parties.party} array produces one instance of subsequence E1
 * (SETPRTY): {@code FOREACH(...party[{n}].type)} targeting {@code E/E1[{n}]/95Q/1} makes the writer
 * create the sequence and its 16R/16S boundaries for you, and the following row fills component 2 of
 * that same field instance with {@code UPDATE}. The {@code map()} transformation translates the
 * source vocabulary into ISO 15022 qualifiers, SELLER to SELL and BUYER to BUYR.
 *
 * <p>This is also the first example of the other translation entry point: instead of
 * {@code translate(json, table)}, it drives an explicit {@link MtWriter} built for a concrete message
 * type. That is what lets the engine know the message structure, and it gives you the typed
 * {@link MT540} back rather than a string. {@link Json2MtExample5} takes this further with nested
 * sequences.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MtExample3 {

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

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MtExample3.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable t = loader.load("SEQUENCE", FileFormat.JSON, FileFormat.MT);
        // Validate mapping rules syntax
        for (String problem : t.validate()) {
            System.out.println(problem);
        }

        MtWriter writer = new MtWriter(new MT540());
        MyFormatEngine.translate(new JsonReader(jsonInput), writer, t.getRules());

        MT540 mt = (MT540) writer.mt();

        System.out.println(mt.message());
    }
}
