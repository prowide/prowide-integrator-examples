package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 */
public class MT920_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * @throws Exception
	 */
	@Test
	public void test920_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I920AAAAAAAAXXXXN}{4:\n" +
            ":20:3948\n" +
            ":12:942\n" +
			":25:123-45678\n" +
			":34F:CHFD1000000,\n" +
			":34F:CHFC100000,\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}
	

}
