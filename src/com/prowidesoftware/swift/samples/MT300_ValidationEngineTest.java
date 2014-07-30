package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT300 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT300_ValidationEngineTest extends BaseTestCase {

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test300_1() throws Exception {
		String s = "{1:F01ABCBUS33AXXX3768156193}{2:O3001139050822XYZBUS33AFXO29569650200508221139N}{3:{108:FC003105ded7970a}}{4:\n" +
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
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

}
