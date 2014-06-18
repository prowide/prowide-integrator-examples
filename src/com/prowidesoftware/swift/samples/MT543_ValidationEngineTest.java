package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;


/**
 * Extended class for validation testing.
 * 
 * @author rob
 * @since 1.0
 */
public class MT543_ValidationEngineTest extends BaseTestCase {

	
	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test543_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX0000000000}{2:I543BBBBUS00XGSTN}{3:{108:09010110000003}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//09010110000003\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3
			
			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090101\n" +			//5
			":98A::TRAD//20090101\n" +			//6
			":35B:ISIN IT0001465159\n" +		//7
			"1000000-3\n" +	
			"ITALCEMENTI SPA COMMON\n" +
			":16S:TRADDET\n" +					//8
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/20000,\n" +		//10
			":97A::SAFE//XXX123\n" +				//11
			":16S:FIAC\n" +						//12
			
			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14
			
				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95P::REAG//CCCCCCCC\n" +	//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95Q::PSET//XXX\n" +			//19
				":16S:SETPRTY\n" +				//20
				//Settlement Parties
				":16R:SETPRTY\n" +				//21
				":95P::BUYR//DDDDDDDDXXX\n" +	//22
				":97A::SAFE//123456789\n" +			//24
				":16S:SETPRTY\n" +				//25
				//Amount
				":16R:AMT\n" +					//26
				":19A::SETT//EUR190764,00\n" +	//27
				":16S:AMT\n" +					//28
				
			":16S:SETDET\n" +					//29
			"-}\n" +
			"";

		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
	
}
