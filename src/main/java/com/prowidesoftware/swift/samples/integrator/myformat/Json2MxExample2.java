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

class Json2MxExample2 {

    public static void main(String[] args) {
        String json = "{"
                + "  'root': {"
                + "    'GRP_HDR': {"
                + "      'CUSTREF': 'FILEREF123',"
                + "      'NO_OF_PAYMENTS': 4,"
                + "      'TOTAL_AMOUNT': 100"
                + "    }"
                + "  }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MxExample2.class.getResourceAsStream("/myformat/json2mx.xls")));
        MappingTable table = loader.load("PATH");
        String out = MyFormatEngine.translate(json, table);
        MxPain00100103 mx = MxPain00100103.parse(out);
        System.out.println(mx.message());
    }

}
