/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
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

import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.utils.Lib;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT messages validation.<br>
 * Reading and validating a valid SWIFT message from disk.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageValidation1Example {

    public static void main(String[] args) throws IOException {
		/*
		 * Load a message from file
		 */
    	String msg = Lib.readFile("mt103.txt");
	
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
