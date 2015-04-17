package com.prowidesoftware.swift.samples.integrator.validation;

import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.utils.Lib;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT message validation with custom rules. 
 * Reading and validating a valid SWIFT message from disk.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageValidationWithCustomRulesExample {

    public static void main(String[] args) throws IOException {
    	/*
		 * Load a message from file
		 */
    	String msg = Lib.readFile("mt103_customvalidated.txt");

    	/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
		
		/*
		 * Run the validation and print results
		 */
		List<ValidationProblem> r = engine.validateMtMessage(msg);
		ExamplesLib.dumpProblems(r);
		
		/*
		 * Release engine
		 */
		engine.dispose();
    }
}
