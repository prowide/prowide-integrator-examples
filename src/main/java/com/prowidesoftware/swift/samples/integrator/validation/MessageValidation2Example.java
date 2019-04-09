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

import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT messages validation.<br> 
 * Creating and validating an invalid SWIFT message.
 * 
 * @author www.prowidesoftware.com
 */
public class MessageValidation2Example  {

    public static void main(String[] args) throws IOException {
		/*
		 * Create an invalid message (see MessageCreateExample)
		 */
		final MT103 m = new MT103();
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
		m.addField(new Field20("REFERENCE"));
		System.out.println(m.message());
		
		/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
		List<ValidationProblem> r = engine.validateMessage(m);
		ExamplesLib.dumpProblems(r, m.getSwiftMessage());
    }
}
