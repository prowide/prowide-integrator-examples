/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT103 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT103_ValidationEngineTest extends BaseTestCase {

	@Test
	public void test103_1() throws Exception {
		String s = "{1:F01CARBVEC0AXXX0344000050}{2:I103CARAANC0XXXXN}{4:\n"+
					":20:TBEXO200909031\n"+
					":23B:CRED\n"+
					":32A:090903USD23453,\n"+
					":50K:/01111001759234567890\n"+
					"ROMAN GUILLEN DOBOZI \n"+
					"R00000V0574734\n"+
					":53B:/00010013800002001234\n"+
					"MI BANCO\n"+
					":59:/00013500510020179998\n"+
					"PDVSA GAS\n"+
					"R00000V000034534\n"+
					":71A:OUR\n"+
					":72:/TIPO/422\n"+
					"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r, msg);
	}

	/*
	 * This example contains a non-existing field 99.
	 * During execution a stack trace is printed, this is NOT an error but the correct execution of this code
	 */
	@SuppressWarnings("boxing")
	@Test
	public void test103_2() throws Exception {
		String s = "{1:F01CARBVEC0AXXX0344000050}{2:I103CARAANC0XXXXN}{4:\n"+
					//":20:TBEXO200909031\n"+
					":23B:CRED\n"+
					":32A:090903USD23453,\n"+
					":99:RFSAMPPGN0031091\n" +
					":50K:/01111001759234567890\n"+
					"ROMAN GUILLEN DOBOZI \n"+
					"R00000V0574734\n"+
					":53B:00010013800002001234\n"+
					"MI BANCO\n"+
					":59:/00013500510020179998\n"+
					"PDVSA GAS\n"+
					"R00000V000034534\n"+
					":71A:OUR\n"+
					":72:/TIPO/422\n"+
					"-}";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r, msg);
	}

}
