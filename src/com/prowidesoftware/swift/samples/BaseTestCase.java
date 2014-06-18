package com.prowidesoftware.swift.samples;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.prowidesoftware.swift.validator.ValidationProblem;

public class BaseTestCase extends ValidationEngineTest {

	public void dumpProblems(List<ValidationProblem> list) {
		dumpProblems(list, null);
	}
	
	protected void assertNotReported(final List<ValidationProblem> r, final String problemKey) {
		assertEquals(problemKey+" NOT expected", 0, count(r, problemKey));
	}
	public void assertNotReported(final List<ValidationProblem> r, final Enum problem) {
		assertEquals(problem+" NOT expected", 0, count(r, problem));
	}

	public void assertReported(final List<ValidationProblem> r, final Enum key) {
		assertEquals("Expected error "+key+ " expected not reported", 1, count(r, key.name()));
	}
	
	public void assertReported(final String msg, final List<ValidationProblem> r, final Enum key) {
		assertEquals(msg, 1, count(r, key.name()));
	}

	protected void assertReported(final List<ValidationProblem> r, final String problemKey) {
		assertEquals("Expected error "+problemKey+ " not reported", 1, count(r, problemKey));
	}

	protected void assertReported(final String msg, final List<ValidationProblem> r, final String problemKey) {
		assertEquals(msg, 1, count(r, problemKey));
	}
}
