/*
 * Copyright (c) http://www.prowidesoftware.com/, 2008. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.FieldProblem;
import com.prowidesoftware.swift.validator.SemanticProblem;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * 
 * @author www.prowidesoftware.com
 * @since 1.0
 */
public class MT202_ValidationEngineTest extends BaseTestCase {

	@Test
	public void test202() throws Exception {
		String s = "{1:F01BICFOOYYAXXX8628453424}{2:O2021300050901IRVTLULXALTA06556102830509011300N}{4:\n" + 
				":20:RFSAMPPGN0031091\n" + 
				":21:RFSAMPPGN0031091\n" + 
				":13C:/RNCTIME/1356+0000\n" +
				":13C:/RNCTIME/1410+0000\n" +
				":32A:050901EUR19265,53\n" + 
				":52A:IRVTLULXLTA\n" + 
				":53A:/D/1234A0123456ABC012345\n" + 
				"BICFOOYY\n" + 
				":54A:BICFOOYY\n" + 
				":56A:BICFOOYY\n" + 
				":57A:BICFOOYY\n" + 
				":58A:/ES12 1234 6789 1234 1111 1234\n" + 
				"BICFOOYY\n" + 
				":72:/BNF/00002695 0001 2005083130110\n" + 
				"-}\n" + 
				"";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}

}
