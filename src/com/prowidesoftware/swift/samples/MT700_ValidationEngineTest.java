package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT700 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT700_ValidationEngineTest extends BaseTestCase {

	/**
	 * Test no error.
	 * @throws Exception
	 */
	@Test
	public void test700_1() throws Exception {
		String s = "{1:F01CARBVEC0AXXX5480000053}{2:I700BICPDVSAXXXXN}{4:\n" +
				":27:1/1\n" +
				":40A:IRREVOCABLE\n" +
				":20:12345\n" +
				":23:PREADV/030510\n" +
				":31C:030517\n" +
				":40E:UCP LATEST VERSION\n" +
				":31D:030730AMSTERDAM\n" +
				":50:ABC COMPANY\n" +
				"KAERNTNERSTRASSE 3\n" +
				"VIENNA\n" +
				":59:AMDAM COMPANY\n" +
				"PO BOX 123\n" +
				"AMSTERDAM\n" +
				":32B:EUR100000,\n" +
				":41A:AMRONL2A\n" +
				"BY PAYMENT\n" +
				":43P:ALLOWED\n" +
				":43T:ALLOWED\n" +
				":44A:AMSTERDAM\n" +
				":44B:VIENNA\n" +
				":45A:+400,000 BOTTLES OF BEER\n" +
				"PACKED 12 TO AN EXPORT CARTON\n" +
				"+FCA AMSTERDAM\n" +
				":46A:+SIGNED COMMERCIAL INVOICE IN QUINTUPLICATE\n" +
				"+ FORWARDING AGENTS CERTIFICATE OF RECEIPT SHOWING GOODS\n" +
				"+ADDRESSED TO THE APPLICANT\n" +
				":48:WITHIN 6 DAYS OF ISSUANCE OF FCR\n" +
				":49:CONFIRM\n" +
				":57A:MEESNL2A\n" +
				"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

}
