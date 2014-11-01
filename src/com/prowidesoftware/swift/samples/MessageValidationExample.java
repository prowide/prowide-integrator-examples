package com.prowidesoftware.swift.samples;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Example of SWIFT messages validation. Reading and validating a valid SWIFT
 * message from disk.
 * 
 * @author www.prowidesoftware.com
 */
public class MessageValidationExample extends ValidationEngineTest {

    public static void main(String[] args) throws IOException {
	/*
	 * Load a message (see MessageParseExample)
	 */
	String swiftFilename = Resources.getString("msg.1");
	SwiftParser parser = new SwiftParser();
	parser.setReader(new FileReader(swiftFilename));
	/*
	 * msg contains java object from parsed message.
	 */
	SwiftMessage msg = parser.message();

	/*
	 * Create and initialize the validation engine
	 */
	ValidationEngine engine = new ValidationEngine();
	engine.initialize();
	List<ValidationProblem> r = engine.validateMessage(msg);
	dumpProblems(r, msg);
    }
}
