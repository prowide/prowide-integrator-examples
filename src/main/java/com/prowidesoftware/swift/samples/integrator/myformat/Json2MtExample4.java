/*
 * Copyright (c) 2025 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt5xx.MT535;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MappingTableExcelLoader;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.json.JsonReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

import java.io.IOException;
import java.util.Objects;

/**
 * Complex test creating real MT535 nested sequences from several foreach.
 */
class Json2MtExample4 {

    public static void main(String[] args) throws IOException {
        String jsonInput = "{" + "  'M': {"
                + "    'B': ["
                + "      {"
                + "        'T95P': 'F1F1//ABCDARXXXXX',"
                + "        'T97A': 'F2F2//1',"
                + "        'B1': {"
                + "          'T35B': 'F3',"
                + "          'B1b': ["
                + "            {"
                + "              'T22F': 'F4F4//1',"
                + "              'T94B': 'F5F5//1'"
                + "            },"
                + "            {"
                + "              'T22F': 'F4F4//2',"
                + "              'T94B': 'F5F5//2'"
                + "            }"
                + "          ],"
                + "          'T99A': 'F6F6//103',"
                + "          'B1c': {"
                + "            'T13B': 'F7F7//1',"
                + "            'T90A': 'F8F8//F8F/1,'"
                + "          }"
                + "        }"
                + "      },"
                + "      {"
                + "        'T97A': 'F2F2//2',"
                + "        'B1': {"
                + "          'B1c': ["
                + "            {"
                + "              'T13B': 'F7F7//2',"
                + "              'T90A': 'F8F8//F8F/2,'"
                + "            },"
                + "            {"
                + "              'T13B': 'F7F7//3',"
                + "              'T90A': 'F8F8//F8F/3,'"
                + "            }"
                + "          ]"
                + "        }"
                + "      }"
                + "    ]"
                + "  }"
                + "}";

        System.out.println(jsonInput);

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MtExample2.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable t = loader.load("REP_SEQ", FileFormat.JSON, FileFormat.MT);
        assert (t.validate().isEmpty());

        MtWriter writer = new MtWriter(new MT535());
        MyFormatEngine.translate(new JsonReader(jsonInput), writer, t.getRules());

        MT535 mt = (MT535) writer.mt();
        System.out.println(mt.message());
    }
}
