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

import com.prowidesoftware.swift.validator.*;

import java.io.IOException;
import java.util.List;

/**
 * Example of SWIFT message validation ignoring specific errors.
 * 
 * <p>
 * The example will mark to ignore several kind of validation rules, including
 * general, structure, field, and semantic checks.
 * In a real use case scenario this is use to filter just a few specific constraints
 * depending on the nature of the messages being validation and the needs for some
 * flexibility over the default standard constraints.
 * </p>
 * 
 * @since 7.7
 */
public class MessageValidationIgnoringCustomExample {

    public static void main(String[] args) throws IOException {
		/*
		 * Load a message
		 */
		String msg = "{1:X01BICFOOY0AXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}{121:4086d27c-e724-4e12-8a73-b450ec6b2f94}}{4:\n"
		        + ":20:0061350113089908\n" 
				+ ":13C:/RNCTIME/1534+0000\n" 
		        + ":23B:FOO\n" 
				+ ":23E:SDVA\n" 
		        + ":32A:061028FOO100000,\n" 
				+ ":33B:EUR100000,\n"
		        + ":50K:/12345678\n" 
				+ "AGENTES DE BOLSA FOO AGENCIA\n" 
		        + "AV XXXXX 123 BIS 9 PL\n" 
				+ "12345 BARCELONA\n" 
		        + ":52A:/2337\n" 
				+ "FOOAESMMXXX\n"
		        + ":53A:FOOAESMMXXXAAAAA\n" 
				+ ":57A:BICFOOYYXXX\n" 
		        + ":59:/ES0123456789012345671234\n" 
				+ "FOO AGENTES DE BOLSA ASOC\n" 
		        + ":71A:OUR\n"
		        + ":72:/BNF/TRANSF. BCO. FOO\n" 
		        + "-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}foo";

		/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
	
		/*
		 *  Several validation problems are reported by default when
		 *  all standard rules are applied to the message
		 */
		List<ValidationProblem> r = engine.validateMtMessage(msg);
		System.out.println("Results without ignore configuration");
		System.out.println(ValidationProblem.printout(r));
	
		/*
		 * Configuration is set to ignore the reported errors.
		 * As result of this configuration, the message will be reported to be valid.
		 */
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.P16);
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.H02);
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.H10);
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.KNN);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.T34);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.T52);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.T27);
		engine.getConfig().addIgnoredValidationProblem(SemanticProblem.D75);
		r = engine.validateMtMessage(msg);
		System.out.println("Results after ignore configuration");
		System.out.println(ValidationProblem.printout(r));
    }
}
