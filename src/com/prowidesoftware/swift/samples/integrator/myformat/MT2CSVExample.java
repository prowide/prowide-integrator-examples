package com.prowidesoftware.swift.samples.integrator.myformat;

import com.prowidesoftware.swift.myformat.FileFormat;
import com.prowidesoftware.swift.myformat.MappingRule;
import com.prowidesoftware.swift.myformat.MappingTable;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.myformat.Transformation;
import com.prowidesoftware.swift.myformat.Transformation.Key;
import com.prowidesoftware.swift.myformat.csv.CsvWriter;

/**
 * This example shows how to convert and MT into a CSV row
 * using API from Prowide Integrator MyFormat module.
 * <br>
 * The mapping rules in this example are defined programmatically.
 * <br>
 * The example takes an MT300 as input, and produces as result an
 * individual CSV row with information gathered from the MT.
 * To create a CSV file with multiples lines, each corresponding to
 * a translated input MT, the translation call should be run on each
 * source message, appending the resulting CSV row to the output File.  
 * 
 * @author sebastian@prowidesoftware.com
 * @since 7.8
 */
public class MT2CSVExample {
	public static void main(String[] args) {
		/*
		 * source message
		 */
		String msg = "{1:F01ABCBUS33AXXX3768156193}{2:O3001139050822XYZBUS33AFXO29569650200508221139N}{3:{108:FC003105ded7970a}}{4:\n" +
				   ":15A:\n" +
				   ":20:QCOUCN\n" +
				   ":21:NEW\n" +
				   ":22A:CANC\n" +
				   ":22C:ABCB334209XYZB33\n" +
				   ":82A:XYZBUS33FXO\n" +
				   ":87A:ABCBUS33XXX\n" +
				   ":77D:/VALD/20040509\n" +
				   "/SETC/USD\n" +
				   ":15B:\n" +
				   ":30T:20070422\n" +
				   ":30V:20070513\n" +
				   ":36:542,09\n" +
				   ":32B:CLP3794630000,\n" +
				   ":53A:XYZBUS33FXO\n" +
				   ":57D:NET SETTLEMENT\n" +
				   ":33B:USD7000000,00\n" +
				   ":53A:ABCBUS33XXX\n" +
				   ":57D:NET SETTLEMENT\n" +
				   ":58A:/9301011483\n" +
				   "ABCBUS33XXX\n" +
				   ":15C:\n" +
				   ":24D:BROK\n" +
				   ":88D:GFI-NY\n" +
				   ":72:/ACC/GTMS:\n" +
				   "//L1710833-1-1\n" +
				   "-}{5:{CHK:97BE21F26A78}}";
		
		/*
		 * programmatic mapping rules
		 * notice this rules could also be loaded from Excel spreadsheet or database.
		 */
		MappingTable t = new MappingTable(FileFormat.MT, FileFormat.CSV);
		t.add(new MappingRule("20", "0")); 
		t.add(new MappingRule("21", "1"));
		t.add(new MappingRule("B/32B/1", "2"));
		t.add(new MappingRule("B/32B/2", "3", new Transformation(Key.replace, ",", ".")));
		t.add(new MappingRule("B/58A/2", "4", new Transformation(Key.prepend, "#")));
		t.add(new MappingRule("C/72/Line[2]", "5", new Transformation(Key.stripStart, "/")));
		
		/*
		 * check if the mapping table is valid
		 */
		if (t.validate().isEmpty()) {
			
			/*
			 * translation call
			 */
			final String target = MyFormatEngine.translate(msg, t);
			
			/*
			 * print the result:
			 * QCOUCN,NEW,CLP,3794630000.,#9301011483,L1710833-1-1
			 */
			System.out.println(target);
		}
	}
}
