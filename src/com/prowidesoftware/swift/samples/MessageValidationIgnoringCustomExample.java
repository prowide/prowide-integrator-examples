/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.IntegratorConfiguration;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT message validation ignoring specific errors
 * 
 * @author www.prowidesoftware.com
 */
public class MessageValidationIgnoringCustomExample extends ValidationEngineTest{

	public static void main(String[] args) throws IOException {
		/*
		 * Load a message (see MessageParseExample)
		 */
		String swift = "{1:X01BICFOOYYAXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n" + 
				":20:0061350113089908\n" + 
				":13C:/RNCTIME/1534+0000\n" + 
				":23B:CRED\n" + 
				":23E:SDVA\n" + 
				":32A:061028EUR100000,\n" + 
				":33B:EUR100000,\n" + 
				":50K:/12345678\n" + 
				"AGENTES DE BOLSA FOO AGENCIA\n" + 
				"AV XXXXX 123 BIS 9 PL\n" + 
				"12345 BARCELONA\n" + 
				":52A:/2337\n" + 
				"FOOAESMMXXX\n" + 
				":53A:FOOAESMMXXX\n" + 
				":57A:BICFOOYYXXX\n" + 
				":59:/ES0123456789012345671234\n" + 
				"FOO AGENTES DE BOLSA ASOC\n" + 
				":71A:OUR\n" + 
				":72:/BNF/TRANSF. BCO. FOO\n" + 
				"-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}\n" + 
				"\n";
		SwiftParser parser = new SwiftParser();
		parser.setReader(new StringReader(swift));
		/*
		 * msg contains java object from parsed message.
		 */
		SwiftMessage msg = parser.message();

		/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
		
		// illegal X value in application Id
		List<ValidationProblem> r = engine.validateMessage(msg);
		dumpProblems(r, msg);
		
		// an info message will be logged instead of reporting the problem in the problems list
		IntegratorConfiguration.getInstance().addIgnoreStructureProblem(StructureProblem.BLOCK1_APPLICATION_ID);
		r = engine.validateMessage(msg);
		dumpProblems(r, msg);
	}
}
