/*******************************************************************************
 * Copyright (c) 2019 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;

import java.util.List;

/**
 * This example shows how to convert a single CSV file row into an MT103 using API from Prowide Integrator MyFormat module.
 */
public class Csv2MtExample1 {
	public static void main(String[] args) {
		// Create a mapping table instance with source and target formats
		MappingTable table = new MappingTable(FileFormat.CSV, FileFormat.MT);

		// Load mapping rules from Excel
		MappingTable.loadFromSpreadsheet(Xml2MtExample1.class.getResourceAsStream("/myformat/csv2mt.xls"), "example1", table);

		// Validate mapping rules syntax
		List<String> problems = table.validate();
		for (String s : problems) {
			System.out.println(s);
		}

		// Source message sample
		String source = "04/20/19,CITICATT,EFX-EPPAY,USD,1234.56,Joe Doe,14th Street Dep 87 Long Island PO 10002,Washington,USA,1234567890,Foo Corp LTD,International division NY 202099,,1234555,INV-12323";
		String out = MyFormatEngine.translate(source, table);

		// parse as MT
		MT103 mt = MT103.parse(out);

		// generate GPI UETR
		mt.getSwiftMessage().setUETR();

		// print message content as FIN
		System.out.println(mt.message());
	}
}
