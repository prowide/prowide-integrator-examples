package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationProblem;


/**
 * Example of MT545 message validation test.
 * 
 * @author www.prowidesoftware.com
 */
public class MT545_ValidationEngineTest extends BaseTestCase {

	/**
	 * Test no errors
	 * @throws Exception
	 */
	@Test
	public void test545_AllValid() throws Exception {
		String s = "{1:F01AAAAUS00AXXX1540684651}{2:O5451117081110BBBBUS00DGST08309832700811100617N}{3:{108:000576CQ8231905}}{4:\n" +
		
			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//X100001\n" +			//1
			":23G:NEWM\n" +						//3
			":16R:LINK\n" +						//4
			":22F::LINK//INFO\n" +				//5
			":13A::LINK//541\n" +				//6
			":20C::RELA//09010110000001\n" +	//7
			":16S:LINK\n" +						//8
			":16S:GENL\n" +						//9
			
			//Trade Details
			":16R:TRADDET\n" +					//10
			":98A::SETT//20090101\n" +			//11
			":98A::TRAD//20090101\n" +			//12
			":98A::ESET//20090101\n" +			//13
			":35B:ISIN IT0003211601\n" +		//14
			"/XX/7277528\n" +
			"BANCA FOO ABC-CASSA RISP GENOVA\n" +
			"EUR1\n" +
			":70E::SPRO///COMPLETE/\n" +		//15
			":16S:TRADDET\n" +					//16
			
			//Financial Instrument/Account
			":16R:FIAC\n" +						//17
			":36B::ESTT//UNIT/5000,\n" +		//18
			":97A::SAFE//987654321\n" +			//19
			":16S:FIAC\n" +						//20
			
			//Settlement Details
			":16R:SETDET\n" +					//21
			":22F::SETR//TRAD\n" +				//22
			
				//Settlement Parties
				":16R:SETPRTY\n" +								//23
				":95Q::SELL//TEXT GOES HERE DDDDDDDDXXX\n" +			//24
				":97A::SAFE//123456789\n" +					//25
				":16S:SETPRTY\n" +								//26
				//Settlement Parties
				":16R:SETPRTY\n" +								//27
				":95Q::DEAG//TEXT GOES HERE CCCCCCCCXXX\n" +	//28
				":16S:SETPRTY\n" +								//29
				//Settlement Parties
				":16R:SETPRTY\n" +								//30
				":95P::PSET//PPPPPPPP\n" +						//31
				":16S:SETPRTY\n" +								//32
				//Amount
				":16R:AMT\n" +	//33
				":19A::ESTT//EUR11235,\n"+ //34
				":19A::OCMT//EUR11235,\n" +						//35
				":98A::VALU//20081110\n" +						//36
				":16S:AMT\n" +									//37
				
			":16S:SETDET\n" +					//38
			"-}{5:{CHK:D6D37459446C}}\n" +		//39
			"";

		SwiftMessage msg = new SwiftParser(s).message();
		System.out.println(msg);
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r);
		assertTrue(r.isEmpty());
	}
	
}
