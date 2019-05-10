/*******************************************************************************
 * Copyright (c) 2019 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.integrator.validation;

import java.util.List;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Simple validation example forcing several semantic problems, to see how 
 * the semantic validations are detected are reported.
 * 
 * <p>
 * The example produces the following output:
 * <pre>
 * MALFORMED MESSAGE, 4 VALIDATION PROBLEMS FOUND.
 * 1/4 23E: INVALID_FIELD_QUALIFIER tag index 2 --> Invalid qualifier FOOO found in field 23E, expecting one of: CHQB, CORT, HOLD, INTC, PHOB, PHON, PHOI, REPA, SDVA, TELB, TELE, TELI. (errorcode: K23) (sequence: main)
 * 2/4: E02 --> If field 23B contains SSTD or SPAY, 23E must not be used.
 * 3/4: D97 --> Component 2 of field 23E is only allowed when component 1 consists of PHON, PHOB, PHOI, TELE, TELB, TELI, HOLD or REPA for not STP and REPA for STP.
 * 4/4: E15 --> If field 71A contains BEN, field 71F is mandatory and field 71G is not allowed.
 * </pre>
 * </p>
 * 
 */
public class MessageValidation3Example {
	public static final String B1 = "{1:F01FOOSEDR0AXXX0000000000}";
	public static final String B2_103 = "{2:I103FOORECV0XXXXN}";

	public static void main(String[] args) throws Exception {
		final MessageValidation3Example main = new MessageValidation3Example();
		System.out.println("SEMANTIC RULE 2");
		main.showSemantic2();
		System.out.println("SEMANTIC RULE 150");
		main.showSemantic150();
		System.out.println("SEMANTIC RULE 151");
		main.showSemantic151();
		System.out.println("SEMANTIC RULE 157");
		main.showSemantic157();
		System.out.println("SEMANTIC RULE 175");
		main.showSemantic175();
		System.out.println("SEMANTIC RULE 197");
		main.showSemantic197();
    }
    
    void showSemantic2() throws Exception {
		MT103 mt = MT103.parse(B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n" + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
		        + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:BEN\n" + ":71G:EUR10,\n"
		        + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}");
	
		List<ValidationProblem> r = new ValidationEngine().validateMessage(mt);
		System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic150() throws Exception {
		String mt = B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n" + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
		        + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:SHA\n" + ":71G:USD10,\n"
		        + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";
	
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
		System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic151() throws Exception {
		String mt = B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n"
		        +
		        // ":33B:ARS200,00\n"+
		        ":50K:TRUST BANK\n" + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:OUR\n" + ":71G:USD10,\n"
		        + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";
	
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
		System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic157() throws Exception {
		String mt = B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n" + ":33B:USD200,00\n" + ":50K:TRUST BANK\n"
		        + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:BEN\n" + ":71G:USD0,\n"
		        + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";
	
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
		System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic175() throws Exception {
		String mt = B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":32A:010113USD200,00\n" + ":33B:ARS200,00\n" + ":50K:TRUST BANK\n"
		        + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:OUR\n" + ":72:/TELE/ IN FAVOUR OF\n"
		        + "//A/C R-000000\n-}";
	
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
		System.out.println(ValidationProblem.printout(r));
    }

    void showSemantic197() throws Exception {
		String mt = B1 + B2_103 + "{4:\n:20:777777M350\n" + ":23B:SSTD\n" + ":23E:FOOO/1234567\n" + ":32A:010113USD200,00\n" + ":33B:USD200,00\n"
		        + ":50K:TRUST BANK\n" + "FUND\n" + ":53B:/1111YYYYYY\n" + ":57A:CHASUS33\n" + ":59:/222YYYYYY\n" + "JP\n" + ":71A:BEN\n" + ":71G:USD10,\n"
		        + ":72:/TELE/ IN FAVOUR OF\n" + "//A/C R-000000\n-}";
	
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg);
		System.out.println(ValidationProblem.printout(r));																			
    }

}
