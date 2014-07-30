package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of MT546 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT546_ValidationEngineTest extends BaseTestCase {
	
	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test546_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1535683613}{2:O5461015081107BBBBUS00DGST08299115840811070515N}{3:{108:000575CQ8241552}}{4:\n" +
		
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100002\n" +			//1
			":23G:NEWM\n" +						//3
			":16R:LINK\n" +						//4
			":22F::LINK//INFO\n" +				//5
			":13A::LINK//542\n" +				//6
			":20C::RELA//09010110000002\n" +	//7
			":16S:LINK\n" +						//8
			":16S:GENL\n" +						//9
			
			//Trade Details
			":16R:TRADDET\n" +					//10
			":98A::SETT//20090101\n" +			//11
			":98A::TRAD//20090101\n" +			//12
			":98A::ESET//20090101\n" +			//13
			":35B:ISIN IT0003430813\n" +		//14
			"/XX/B0N64J1\n" +
			"MYCOMP GROUP\n" +
			"EUR0.25\n" +
			":70E::SPRO///COMPLETE/\n" +		//15
			":16S:TRADDET\n" +					//16
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//17
			":36B::ESTT//UNIT/110000,\n" +		//18
			":97A::SAFE//987654321\n" +			//19
			":16S:FIAC\n" +						//20
			
			//Settlement Details
			":16R:SETDET\n" +					//21
			":22F::SETR//TRAD\n" +				//22
			//":22F::DBNM//INTE\n" +				//22
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//23
				":95Q::BUYR//TEXT GOES HERE DDDDDDDDXXX\n" +	//24
				":97A::SAFE//123456789\n" +						//25
				":16S:SETPRTY\n" +								//26
				//Settlement Parties
				":16R:SETPRTY\n" +								//27
				":95Q::REAG//TEXT GOES HERE CCCCCCCCXXX\n" +				//28
				":16S:SETPRTY\n" +								//29
				//Settlement Parties
				":16R:SETPRTY\n" +								//30
				":95P::PSET//PPPPPPPP\n" +						//31
				":16S:SETPRTY\n" +								//32
				
			":16S:SETDET\n" +					//33
			"-}{5:{CHK:7E62833FBD4D}}\n" +		//34
			"";

		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
	
}
