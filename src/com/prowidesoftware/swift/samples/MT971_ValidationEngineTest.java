package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT971 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT971_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test971_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I971AAAAAAAAXXXXN}{4:\n" +
            ":20:98031645\n" +
            ":25:UFOOME\n" +
            ":62F:D980314EUR1234,5\n" +
            ":25:USDPRIME\n" +
            ":62F:D980314USD1234,5\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

}
