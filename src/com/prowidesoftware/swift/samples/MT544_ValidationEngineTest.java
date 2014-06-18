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
public class MT544_ValidationEngineTest extends BaseTestCase {

	
	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test544_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1534682759}{2:O5441216081106BBBBUS00DGST08298582490811060716N}{3:{108:000574CQ8270598}}{4:\n" +
			
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100000\n" +			//1
			":23G:NEWM\n" +						//2
			":16R:LINK\n" +						//3
			":22F::LINK//INFO\n" +				//4
			":13A::LINK//540\n" +				//5
			":20C::RELA//09010110000000\n" +	//6
			":16S:LINK\n" +						//7
			":16S:GENL\n" +						//8
			
			//Trade Details
			":16R:TRADDET\n" +					//9
			":98A::SETT//20090101\n" +			//10
			":98A::TRAD//20090101\n" +			//11
			":98A::ESET//20090101\n" +			//12
			":35B:ISIN IT0004176001\n" +		//13
			"/XX/B1W4V69\n" +
			"PRYSMIAN SPA\n" +
			"EUR0.10\n" +
			":70E::SPRO//COMPLETE\n" +			//14
			":16S:TRADDET\n" +					//15
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//16
			":36B::ESTT//UNIT/299000,\n" +		//17
			":97A::SAFE//987654321\n" +			//18
			":16S:FIAC\n" +						//19
			
			//Settlement Details
			":16R:SETDET\n" +					//20
			":22F::SETR//TRAD\n" +				//21
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//22
				":95Q::SELL//TEXT GOES HERE DDDDDDDDXXX\n" +	//23
				":97A::SAFE//009643850229\n" +					//24
				":16S:SETPRTY\n" +								//25
				//Settlement Parties
				":16R:SETPRTY\n" +								//26
				":95Q::DEAG//TEXT GOES HERE CCCCCCCCXXX\n" +	//27
				":16S:SETPRTY\n" +								//28
				//Settlement Parties
				":16R:SETPRTY\n" +								//29
				":95P::PSET//PPPPPPPP\n" +						//30
				":16S:SETPRTY\n" +								//31
				
			":16S:SETDET\n" +					//32
			"-}{5:{CHK:625F3A7E17C7}}\n" +		//33
			"";

		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
	
}
