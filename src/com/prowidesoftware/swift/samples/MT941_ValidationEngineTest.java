package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT941 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT941_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test941_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I941AAAAAAAAXXXXN}{4:\n" +
            ":20:234567\n" +
            ":21:765432\n" +
            ":25:6894-77381\n" +
            ":28:212\n" +
            ":13D:0206041515+0200\n" +
            ":60F:C020603EUR595771,95\n" +
            ":90D:72EUR385920,\n" +
            ":90C:44EUR450000,\n" +
            ":62F:C020604EUR659851,95\n" +
            ":64:C020604EUR480525,87\n" +
            ":65:C020605EUR530691,95\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}
	
}
