package com.prowidesoftware.swift.samples.integrator.validation;

import java.util.List;

import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Validation test sample for MT540 with valid and invalid sample messages
 * 
 * The expected ouput is:
<pre>
Message OK test:
223 day/s left for this license.
MESSAGE OK. NO VALIDATION PROBLEMS FOUND.
-----------------------
Message with validation problem test:
MALFORMED MESSAGE, 8 VALIDATION PROBLEMS FOUND.
1/8: INVALID_FIELD_QUALIFIER tag index 10 --> Invalid qualifier SETT/UNIT/299000, found in field 36B, expecting one of: SETT. (errorcode: K36)
2/8: FAILED_EXPRESSION_QUALIFIER tag index 11 --> Invalid qualifier  found in field 36B, expecting one of: AMOR FAMT UNIT.
3/8: MISSING_DOUBLE_SLASH tag index 10 --> Field 36B: Missing double slash ('//') between components.
4/8: BAD_SLASH_SEPARATORS tag index 10 --> Field 36B: 2 '/' found while 3 were expected.
5/8: COMPONENT_BAD_SIZE tag index 10 --> Field 36B: Component component 1 must have 4 characters, and 17 where found.
6/8: BAD_CHARSET tag index 10 --> Field 36B: Bad char at position 5 of SETT/UNIT/299000, found. Expected characters are: Alpha-numeric capital letters (upper case), and digits only. ([A-Z][0-9]).
7/8: MISSING_COMPONENT tag index 10 --> Field 36B: Missing required component 2.
8/8: MISSING_COMPONENT tag index 10 --> Field 36B: Missing required amount component.
</pre>
 * 
 */
public class MessageValidation4Example {
	
	public static void main(String[] args) {
		ValidationEngine validator = new ValidationEngine();
		validator.initialize();
		
		System.out.println("-----------------------");
		System.out.println("Message OK test:");
		List<ValidationProblem> result = validator.validateMtMessage(mt540_ok);
		System.out.print(ValidationProblem.printout(result));
		
		System.out.println("-----------------------");
		System.out.println("Message with validation problem test:");
		result = validator.validateMtMessage(mt540_errors);
		System.out.print(ValidationProblem.printout(result));
		
		validator.dispose();
	}
	
	final static String mt540_ok = "{1:F01FOOLUS33AXXX0000000000}{2:I540FOOSGB2LXGSTN}{3:{108:090106C1234567}}{4:\n" +

			//General Information
			":16R:GENL\n" +						//0
			":20C::SEME//090106C1234567\n" +	//1
			":23G:NEWM\n" +						//2
			":16S:GENL\n" +						//3

			//Trade Details
			":16R:TRADDET\n" +					//4
			":98A::SETT//20090109\n" +			//5
			":98A::TRAD//20090109\n" +			//6
			":35B:ISIN IT0009999001\n" +		//7
			"C1234567\n" +
			"FOO SPA COMMON\n" +
			":16S:TRADDET\n" +					//8

			//Financial Instrument/Account
			":16R:FIAC\n" +						//9
			":36B::SETT//UNIT/100000,\n" +		//10
			":97A::SAFE//AAA01\n" +				//11
			":16S:FIAC\n" +						//12

			//Settlement Details
			":16R:SETDET\n" +					//13
			":22F::SETR//TRAD\n" +				//14

				//Settlement Parties
				":16R:SETPRTY\n" +				//15
				":95Q::DEAG//BCITITMM\n" +		//16
				":16S:SETPRTY\n" +				//17
				//Settlement Parties
				":16R:SETPRTY\n" +				//18
				":95P::SELL//CRESCHZZ80A\n" +	//19
				":97A::SAFE//999999999\n" +		//20
				":16S:SETPRTY\n" +				//21
				//Settlement Parties
				":16R:SETPRTY\n" +				//22
				":95Q::PSET//ZZZ\n" +			//23
				":16S:SETPRTY\n" +				//24

			":16S:SETDET\n" +					//25
			"-}";
	
	final static String mt540_errors = "{1:F01AAAAUS00AXXX0000000000}{2:I540BBBBUS00XGSTN}{3:{108:09010110000000}}{4:\n" +

		//General Information
		":16R:GENL\n" +
		":20C::SEME//09010110000000\n" +
		":23G:NEWM\n" +
		":16S:GENL\n" +

		//Trade Details
		":16R:TRADDET\n" +
		":98A::SETT//20090101\n" +
		":98A::TRAD//20090101\n" +
		":35B:ISIN IT0004176001\n" +
		"1000000-0\n" +
		"FOO SPA COMMON\n" +
		":16S:TRADDET\n" +

		//Financial Instrument/Account
		":16R:FIAC\n" +
		":36B::SETT/UNIT/299000,\n" +		// remove slash
		":97A::SAFE//XXX123\n" +
		":16S:FIAC\n" +

		//Settlement Details
		":16R:SETDET\n" +
		":22F::SETR//TRAD\n" +

			//Settlement Parties
			":16R:SETPRTY\n" +
			":95Q::DEAG//CCCCCCCC\n" +
			":16S:SETPRTY\n" +
			//Settlement Parties
			":16R:SETPRTY\n" +
			":95P::SELL//DDDDDDDDXXX\n" +
			":97A::SAFE//123456789\n" +
			":16S:SETPRTY\n" +
			//Settlement Parties
			":16R:SETPRTY\n" +
			":95Q::PSET//XXX\n" +
			":16S:SETPRTY\n" +

		":16S:SETDET\n" +
		"-}";
	
}
