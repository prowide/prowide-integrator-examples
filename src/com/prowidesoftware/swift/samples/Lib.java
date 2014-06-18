package com.prowidesoftware.swift.samples;

import java.util.List;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.validator.ValidationProblem;

public class Lib {
	public static final String B1 = "{1:F01FOOSEDR0AXXX0000000000}";
	public static final String B2_103 = "{2:I103FOORECV0XXXXN}";

	public static void dumpProblems(List<ValidationProblem> problems, SwiftMessage m) {
		if (problems.isEmpty()) {
			System.out.println("MESSAGE OK. NO VALIDATION PROBLEMS FOUND.");
		} else {
			System.out.println("MALFORMED MESSAGE, " + problems.size() + " VALIDATION PROBLEM" + (problems.size() == 1 ? "" : "S")
					+ " FOUND.");
			int k = 1;
			for (final ValidationProblem p : problems) {
				String tagName = "";
				if ((m != null) && (p.getTagIndex() != null)) {
					final Tag t = m.getBlock4().getTag(p.getTagIndex().intValue());
					tagName = " " + t.getName();
				}
				String seq = "";
				if (p.getSequence() != null) {
					seq = " (sequence: " + p.getSequence().getName() + ")";
				}
				System.out.println(k + "/" + problems.size() + tagName + ": " + p.getErrorKey() + " tag index " + p.getTagIndex() + " --> "
						+ p.getMessage() + seq);
				k++;
			}
		}
	}

}
