package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 */
public class MT910_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * @throws Exception
	 */
	@Test
	public void test910_1() throws Exception {
		String s = "{1:F01FOOABCD0AXXX3048000056}{2:I910AAAAAAAAXXXXN}{4:\n" +
            ":20:C11126C9224\n" +
            ":21:494936/DEV\n" +
            ":25:6-9412771\n" +
            ":32A:980527USD500000,\n" +
            ":52A:BKAUATWW\n" +
            ":56A:BKTRUS33\n" +
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}
	

}
