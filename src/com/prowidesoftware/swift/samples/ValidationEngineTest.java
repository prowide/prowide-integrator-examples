/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.validator.SemanticProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Base class for full message validation examples.
 * 
 * @author www.prowidesoftware.com
 */
@Ignore
public class ValidationEngineTest  {

	protected ValidationEngine engine;

	@Before
	public void setUp() throws Exception {
		this.engine = new ValidationEngine();
		this.engine.initialize();
	}

	@After
	public void tearDown() throws Exception {
		this.engine.dispose();
	}

	public static void dumpProblems(final List<ValidationProblem> problems, SwiftMessage m) {
		if (problems.isEmpty()) {
			System.out.println("MESSAGE OK. NO VALIDATION PROBLEMS FOUND.");
		} else {
			System.out.println("MALFORMED MESSAGE, "+problems.size()+" VALIDATION PROBLEM"+(problems.size()==1?"":"S")+" FOUND.");
			int k = 1;
			for (ValidationProblem p : problems) {
				String tagName = "";
				if (m != null && p.getTagIndex() != null) {
					Tag t = m.getBlock4().getTag(p.getTagIndex().intValue());
					tagName = " "+t.getName();
				}
				String seq ="";
				if (p.getSequence() != null) {
					seq = " (sequence: "+p.getSequence().getName()+")";
				}
				System.out.println(k + "/" + problems.size() + tagName + ": " + p.getErrorKey() + 
									" tag index " + p.getTagIndex() + " --> "+p.getMessage() + seq);
				k++;
			}
		}
	}

	/**
	 * Given a validation problem key it counts how many problems with that key
	 * exists in the list
	 * 
	 * @return amount of problems with the key argument
	 */
	protected int count(List<ValidationProblem> problems, Enum problemCode) {
		return count(problems, problemCode.name());
	}
	protected int count(final List<ValidationProblem> problems, final String key) {
		int k = 0;
		for (final ValidationProblem p : problems) {
			if (p.getErrorKey().equals(key)) {
				k++;
			}
		}
		return k;
	}

}
