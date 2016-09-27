package com.prowidesoftware.swift.samples.integrator.validation;

import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.validator.FieldProblem;
import com.prowidesoftware.swift.validator.GeneralProblem;
import com.prowidesoftware.swift.validator.SemanticProblem;
import com.prowidesoftware.swift.validator.StructureProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

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
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageValidationIgnoringCustomExample {

    public static void main(String[] args) throws IOException {
		/*
		 * Load a message
		 */
		String msg = "{1:X01BICFOOY0AXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n"
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
		ExamplesLib.dumpProblems(r);
	
		/*
		 * Configuration is set to ignore the reported errors.
		 * As result of this configuration, the message will be reported to be valid.
		 */
		engine.getConfig().addIgnoredValidationProblem(GeneralProblem.EXTRA_DATA_IN_MESSAGE);
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.INVALID_FIELD_QUALIFIER);
		engine.getConfig().addIgnoredValidationProblem(StructureProblem.BLOCK1_APPLICATION_ID);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.COMPONENT_BAD_SIZE);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.BAD_CURRENCY);
		engine.getConfig().addIgnoredValidationProblem(FieldProblem.COMPONENT_NOT_BIC);
		engine.getConfig().addIgnoredValidationProblem(SemanticProblem.D75);
		r = engine.validateMtMessage(msg);
		System.out.println("Results after ignore configuration");
		ExamplesLib.dumpProblems(r);
    }
}
