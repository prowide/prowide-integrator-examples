package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT900 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT900_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test900_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I900AAAAAAAAXXXXN}{4:\n" +
            ":20:C11126A1378\n" +
            ":21:5482ABC\n" +
            ":25:9-9876543\n" +
            ":32A:980123USD233530,\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

}
