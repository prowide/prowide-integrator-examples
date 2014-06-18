package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 */
public class MT942_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * @throws Exception
	 */
	@Test
	public void test942_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I942AAAAAAAAXXXXN}{4:\n" +
            ":20:345678\n" +
			":21:5678\n" +
			":25:123-45678\n" +
			":28C:124/1\n" +
			":34F:EURD100000,\n" +
			":34F:EURC50000,\n" +
			":13D:0206261200+1200\n" +
			":61:020626D120000,NCOLABCD//12345\n" +
			":61:020626C55000,NFEX99485//678922\n" +
			":90D:9EUR210000,\n" +
			":90C:87EUR385700,\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}
	

}
