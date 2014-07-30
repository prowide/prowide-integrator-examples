package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT950 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT950_ValidationEngineTest extends BaseTestCase {

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test950_1() throws Exception {
		String s = "{1:F01XXXXXXXXAXXX0387241036}{2:O9502352060913YYYYYYYYYYYY18884819330609140052N}{3:{108:0000000309140009}}{4:\n" +
				":20:123456\n" +
				":25:123 456789\n" +
				":28C:102\n" +
				":60F:C020527EUR3723495,\n" +
				":61:020528D1,2FBNK494935/DEV//67914\n" +
				":61:020528D30,2NCHK78911//123464\n" +
				":61:020528D250,NCHK67822//123460\n" +
				":61:020528D450,S103494933/DEV//P064118\n" +
				"FOO A. DESMIDEN\n" +
				":61:020528D500,NCH45633//123456\n" +
				":61:020528D1058,47S103494931//3841188\n" +
				"FOO B. F. JOE DOE\n" +
				":61:020528D2500,NCHK56728//123457\n" +
				":61:020528D3840,S103494935//3841189\n" +
				"FOO B. F. JOE DOE\n" +
				":61:020528D5000,S20023/200516//47829\n" +
				"ORDER ROTTERDAM\n" +
				":62F:C020528EUR3709865,13\n-}{5:{CHK:C0EB56371C00}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
	
}
