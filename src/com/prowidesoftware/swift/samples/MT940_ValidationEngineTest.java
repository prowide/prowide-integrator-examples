package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * 
 * @since 1.0
 */
public class MT940_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * @throws Exception
	 */
	@Test
	public void test940_1() throws Exception {
		String s = "{1:F01BBBBBBB0AXXX3048000056}{2:I940AAAAAAAAXXXXN}{4:\n" +
            ":20:TEST0AAAAAAAAAAA\n"+
            ":25:TEST25AAAAAAAAAA\n"+ 
            ":28C:00084/001\n"+ 
            ":60F:C031002EUR5000,00\n"+ 
            ":62F:C031020EUR3891,59\n"+ 
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void test940_2() throws Exception {
		String s = "{1:F01BBBBBBB0AXXX3048000056}{2:I940AAAAAAAAXXXXN}{4:\n" +
            ":20:123456\n"+
			":25:123-304958\n"+
			":28C:123/1\n"+
			":60F:C980622USD395212311,71\n"+
			":61:980623C50000000,NTRFNONREF//8951234\n"+
			"ORDER BK OF NYC WESTERN CASH RESERVE\n"+
			":61:980625C5700000,NFEX036960//8954321\n"+
			":61:980626C200000,NDIVNONREF//8846543\n"+
			":86:DIVIDEND LORAL CORP\n"+
			"PREFERRED STOCK 1ST QUARTER 1998\n"+
			":62F:C980623USD451112311,71\n"+
			":64:C980623USD445212311,71\n"+
			":65:C980625USD450912311,71\n"+
			":65:C980626USD451112311,71\n"+
			":86:PRIME RATE AS OF TODAY 11 PCT\n"+ 
            "-}\n";
	    SwiftMessage msg = new SwiftParser(s).message();
	    System.out.println(msg);
	    List<ValidationProblem> r = engine.validateMessage(msg);
	    dumpProblems(r);
	    assertEquals(0, r.size());
	}

}
