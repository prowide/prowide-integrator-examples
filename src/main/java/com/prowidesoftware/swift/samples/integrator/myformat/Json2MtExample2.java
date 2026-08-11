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
 * Converts a JSON document into an MT202 using the INDEX sheet of {@code /myformat/json2mt.xls},
 * where the source is an array addressed by explicit position.
 *
 * <p>The input here is the standard Prowide JSON representation of an MT: header blocks plus a
 * {@code textBlock.fields} array. The mapping rows reach into it with one based literal indexes,
 * {@code textBlock.fields[3].currency}, rather than iterating. Use this when the source layout is
 * fixed and you know which slot holds what; when the array length varies, iterate instead as in
 * {@link Json2MtExample3}.
 *
 * <p>Worth noting: because each {@code 32A/n} row is a separate rule with no {@code UPDATE} mode,
 * the output carries three separate {@code :32A:} tags rather than one field with three components.
 * {@link Json2MtExample1} shows the {@code UPDATE} mode that merges them.
 *
 * <p>If all you need is the standard Prowide JSON structure in either direction, no mapping table is
 * required at all, just call {@code toJson()} / {@code fromJson()} on the message class.
 *
 * <p>Requires the Prowide Integrator MyFormat module.
 */
public class Json2MtExample2 {

    public static void main(String[] args) throws IOException {
        String jsonInput = "{" + "    'type': 'MT',"
                + "    'basicHeaderBlock': {"
                + "        'applicationId': 'F',"
                + "        'serviceId': '01',"
                + "        'logicalTerminal': 'FOOTHKHHAXXX',"
                + "        'sessionNumber': '0000',"
                + "        'sequenceNumber': '000000'"
                + "    },"
                + "    'applicationHeaderBlock': {"
                + "        'receiverAddress': 'FOOBHKHHXXXX',"
                + "        'senderInputTime': null,"
                + "        'MIRDate': null,"
                + "        'MIRLogicalTerminal': null,"
                + "        'MIRSessionNumber': null,"
                + "        'MIRSequenceNumber': null,"
                + "        'receiverOutputDate': null,"
                + "        'receiverOutputTime': null,"
                + "        'messagePriority': 'N',"
                + "        'messageType': '202',"
                + "        'direction': 'I'"
                + "    },"
                + "    'textBlock': {"
                + "        'fields': ["
                + "            {"
                + "                'name': '20',"
                + "                'reference': 'TEST2021234'"
                + "            },"
                + "            {"
                + "                'name': '21',"
                + "                'reference': 'TEST202123233'"
                + "            },"
                + "            {"
                + "                'name': '32A',"
                + "                'date': '230131',"
                + "                'currency': 'USD',"
                + "                'amount': '7878778,'"
                + "            },"
                + "            {"
                + "                'name': '58A',"
                + "                'account': '898989',"
                + "                'bIC': 'FOOBHKHH'"
                + "            },"
                + "            {"
                + "                'name': '72',"
                + "                'narrative': ' /INS/PURPOSE CODE 0000',"
                + "                'narrative2': '//SAMPLE NARRATIVE, DUMMY DATA'"
                + "            }"
                + "        ]"
                + "    }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(
                Objects.requireNonNull(Json2MtExample2.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable table = loader.load("INDEX", FileFormat.JSON, FileFormat.MT);
        final String mt = MyFormatEngine.translate(jsonInput, table);
        System.out.println(mt);
    }
}
