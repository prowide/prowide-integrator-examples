package com.prowidesoftware.swift.samples;

import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT messages validation. Creating and validating an invalid
 * SWIFT message.
 * 
 * @author www.prowidesoftware.com
 */
public class MessageValidation2Example extends ValidationEngineTest {

    public static void main(String[] args) throws IOException {
	/*
	 * Create an invalid message (see MessageCreateExample)
	 */
	final MT103 m = new MT103(new SwiftMessage(true));
	m.setSender("FOOSEDR0AXXX");
	m.setReceiver("FOORECV0XXXX");
	m.addField(new Field20("REFERENCE"));

	/*
	 * Create and initialize the validation engine
	 */
	ValidationEngine engine = new ValidationEngine();
	engine.initialize();
	List<ValidationProblem> r = engine.validateMessage(m.getSwiftMessage(), false);
	dumpProblems(r, m.getSwiftMessage());
    }
}
