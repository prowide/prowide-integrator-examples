/*
 * Copyright (c) http://www.prowidesoftware.com/, 2012. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * 
 * @author www.prowidesoftware.com
 */
public class MT195_ValidationEngineTest extends BaseTestCase {

	/**
	 * field 79 not present, additional fields not present
	 * @throws Exception
	 */
	@Test
	public void test195_ok() throws Exception {
		String s = "{1:F01CARBVEC0AXXX3048000056}{2:I195AAAAAAAAXXXXN}{4:\n" +
				":20: 516722/QUERY\n" +
				":21:948LA\n" +
				":75:FOO FOO FOO BAR\n" +
				":77A: Please explain the NSTP claim\n" +
				"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertNotReported(r, StructureProblem.T13);
	}
	
}
