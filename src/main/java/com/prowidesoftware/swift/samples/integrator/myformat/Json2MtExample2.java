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
 * Test creating an MT from a JSON source with an array of elements.
 */
class Json2MtExample2 {

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
                + "        'receiverAddress': 'EXMTHKHHXXXX',"
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
                + "                'bIC': 'EXMTHKHH'"
                + "            },"
                + "            {"
                + "                'name': '72',"
                + "                'narrative': ' /INS/PURPOSE CODE 1670',"
                + "                'narrative2': '//SERVICES, SELF COMPANY FUNDING'"
                + "            }"
                + "        ]"
                + "    }"
                + "}";

        MappingTableExcelLoader loader = new MappingTableExcelLoader(Objects.requireNonNull(Json2MtExample2.class.getResourceAsStream("/myformat/json2mt.xls")));
        MappingTable table = loader.load("INDEX", FileFormat.JSON, FileFormat.MT);
        final String mt = MyFormatEngine.translate(jsonInput, table);
        System.out.println(mt);
    }
}
