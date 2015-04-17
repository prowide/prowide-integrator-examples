package com.prowidesoftware.swift.samples.integrator.validation;

import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.validator.IntegratorConfiguration;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT message validation ignoring specific errors.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageValidationIgnoringCustomExample {

    public static void main(String[] args) throws IOException {
	/*
	 * Load a message
	 */
	String msg = "{1:X01BICFOOYYAXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n"
	        + ":20:0061350113089908\n" 
			+ ":13C:/RNCTIME/1534+0000\n" 
	        + ":23B:CRED\n" 
			+ ":23E:SDVA\n" 
	        + ":32A:061028EUR100000,\n" 
			+ ":33B:EUR100000,\n"
	        + ":50K:/12345678\n" 
			+ "AGENTES DE BOLSA FOO AGENCIA\n" 
	        + "AV XXXXX 123 BIS 9 PL\n" 
			+ "12345 BARCELONA\n" 
	        + ":52A:/2337\n" 
			+ "FOOAESMMXXX\n"
	        + ":53A:FOOAESMMXXX\n" 
			+ ":57A:BICFOOYYXXX\n" 
	        + ":59:/ES0123456789012345671234\n" 
			+ "FOO AGENTES DE BOLSA ASOC\n" 
	        + ":71A:OUR\n"
	        + ":72:/BNF/TRANSF. BCO. FOO\n" 
	        + "-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}";

		/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
	
		/*
		 *  Illegal X value in application Id will be reported
		 */
		List<ValidationProblem> r = engine.validateMtMessage(msg);
		ExamplesLib.dumpProblems(r);
	
		/*
		 * Configuration is set to ignore the reported error.
		 * As result of this configuration, an info message will be logged instead of reporting the problem in validation problem list.
		 */
		IntegratorConfiguration.getInstance().addIgnoreStructureProblem(StructureProblem.BLOCK1_APPLICATION_ID);
		r = engine.validateMtMessage(msg);
		ExamplesLib.dumpProblems(r);
    }
}
