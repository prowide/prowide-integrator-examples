/*
 * Copyright (c) http://www.prowidesoftware.com/, 2008. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.scheme.Scheme;
import com.prowidesoftware.swift.scheme.SchemeManager;
import com.prowidesoftware.swift.validator.FieldProblem;
import com.prowidesoftware.swift.validator.SemanticProblem;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * 
 * @author www.prowidesoftware.com
 * @since 1.0
 */
public class MT540_ValidationEngineTest extends BaseTestCase {

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test540_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I540BBBBUS00XGSTN}{3:{108:09010110000000}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//23423423\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0004176001\n" +		//7
			"1000000-0\n" +	
			"PRYSMIAN SPA COMMON\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/299000,\n" +		//10
			":97A::SAFE//XXX123\n" +			//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95Q::DEAG//CCCCCCCC\n" +		//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95P::SELL//DDDDDDDDXXX\n" +	//19
				":97A::SAFE//123456789\n" +		//20
				":16S:SETPRTY\n" +				//21
				//Settlement Parties
				":16R:SETPRTY\n" +				//22
				":95Q::PSET//XXX\n" +			//23
				":16S:SETPRTY\n" +				//24
				
			":16S:SETDET\n" +					//25
			"-}\n" +
			"";
		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
		
}
