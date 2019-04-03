/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
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

import com.prowidesoftware.swift.model.mt.MtType;
import com.prowidesoftware.swift.model.mt.mt3xx.MT300;
import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.WriteMode;
import com.prowidesoftware.swift.myformat.csv.CsvFileReader;
import com.prowidesoftware.swift.myformat.csv.CsvReader;
import com.prowidesoftware.swift.myformat.mt.MtWriter;

/**
 * This example shows how to convert a CSV file row into an MT300
 * using API from Prowide Integrator MyFormat module.
 * <br>
 * The mapping rules in this example are defined programmatically.
 * <br>
 * The example takes single row from a CSV, and produces as result 
 * the corresponding MT message with information gathered from the
 * CSV row content.
 * To parse a CSV file with multiples lines, each corresponding to
 * a message, the translation call should be run on each source 
 * line. Meaning reading lines from the actual file is out of the
 * translation scope covered by MyFormat.
 * 
 * @author sebastian@prowidesoftware.com
 * @since 7.8
 */
public class CSV2MTExample {
	public static void main(String[] args) {
		/*
		 * programmatic mapping rules
		 * notice this rules could also be loaded from Excel spreadsheet or database.		 
		 */
		MappingTable t = new MappingTable(FileFormat.CSV, FileFormat.MT);
		t.add(new MappingRule("0", "20")); 
		t.add(new MappingRule("1", "21"));
		t.add(new MappingRule("2", "B/32B/1"));
		t.add(new MappingRule("3", "B/32B/2", WriteMode.APPEND, new Transformation(Key.replace, ".", ",")));
		t.add(new MappingRule("4", "B/58A/2", new Transformation(Key.stripStart, "#")));
		t.add(new MappingRule("\"/ACC/GTMS:\"", "C/72/Line[1]"));
		t.add(new MappingRule("5", "C/72/Line[2]", WriteMode.APPEND, new Transformation(Key.prepend, "//")));
		
		/*
		 * check if the mapping table is valid
		 */
		if (t.validate().isEmpty()) {
			
			/*
			 * create the file reader
			 */
			CsvFileReader reader = new CsvFileReader(CSV2MTExample.class.getResourceAsStream("messages.csv"));
			
			while (reader.hasNext()) {
				/*
				 * read a row from the CSV file
				 */
				CsvReader line = reader.next();
				System.out.println("processing: "+line);
				
				/*
				 * translation call
				 */
				MtWriter writer = new MtWriter(MtType.MT300);
				MyFormatEngine.translate(line, writer, t.getRules());
				
				/*
				 * parse and print content from the created MT:
				 * 
				 * Sender's Reference: QCOUCN
				 * Related Reference: NEW
				 * Transaction Amount: CLP3794630000,
				 * Account: /9301011483
				 */
				MT300 mt = (MT300) writer.mt();
				System.out.println("Sender's Reference: " + mt.getField20().getValue());
				System.out.println("Related Reference: " + mt.getField21().getValue());
				System.out.println("Transaction Amount: " + mt.getField32B().get(0).getValue());
				System.out.println("Account: " + mt.getField58A().get(0).getValue());
			}
			
			reader.close();
		}
	}
}
