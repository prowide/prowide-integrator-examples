/*
 * Copyright (c) http://www.prowidesoftware.com/, 2008. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * 
 * @author www.prowidesoftware.com
 * @since 1.0
 */
public class MT986_ValidationEngineTest extends BaseTestCase {

	@Test
	public void test986_1() throws Exception {
		String s = "{1:F01XXXXXXXXAXXX0387241036}{2:O9862352060913YYYYYYYYYYYY18884819330609140052N}{3:{108:0000000309140009}}{4:\n" +
				":20:INQ3531\n" +
				":21:44985789\n" +
				":59:XXX CORPORATION\n" +
				"110 HIGHGROVE ST\n" +
				"LONDON\n" +
				":79:XXX CORP HAS ORGANISATION\n" +
				"-}{5:{CHK:C0EB56371C00}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
}
