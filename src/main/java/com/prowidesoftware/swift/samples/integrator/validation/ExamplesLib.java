/*******************************************************************************
 * Copyright (c) 2019 Prowide Inc.
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

import java.util.List;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.validator.ValidationProblem;

/**
 * Helper API for example and test cases.
 * 
 */
public class ExamplesLib {
    public static final String B1 = "{1:F01FOOSEDR0AXXX0000000000}";
    public static final String B2_103 = "{2:I103FOORECV0XXXXN}";

    /**
     * Dumps validation problems in standard out
     */
    public static void dumpProblems(List<ValidationProblem> problems) {
    	dumpProblems(problems, (SwiftMessage)null);
    }
    
    /**
     * Dumps validation problems in standard out
     */
    public static void dumpProblems(List<ValidationProblem> problems, AbstractMT mt) {
    	dumpProblems(problems, mt.getSwiftMessage());
    }
    
    /**
     * Dumps validation problems in standard out
     */
    public static void dumpProblems(List<ValidationProblem> problems, SwiftMessage m) {
		if (problems.isEmpty()) {
		    System.out.println("MESSAGE OK. NO VALIDATION PROBLEMS FOUND.");
		} else {
		    System.out.println("MALFORMED MESSAGE, " + problems.size() + " VALIDATION PROBLEM" + (problems.size() == 1 ? "" : "S") + " FOUND.");
		    int k = 1;
		    for (final ValidationProblem p : problems) {
				String tagName = "";
				if (m != null && p.getTagIndex() != null && p.getTagIndex() < m.getBlock4().getTags().size()) {
				    final Tag t = m.getBlock4().getTag(p.getTagIndex().intValue());
				    tagName = " " + t.getName();
				}
				String seq = "";
				if (p.getSequence() != null) {
				    seq = " (sequence: " + p.getSequence().getName() + ")";
				}
				StringBuilder s = new StringBuilder();
				s.append(k + "/" + problems.size() + tagName + ": " + p.getErrorKey());
				if (p.getTagIndex() != null) {
					s.append(" tag index " + p.getTagIndex());
				}
				s.append(" --> " + p.getMessage()+ seq);
				System.out.println(s.toString());
				k++;
		    }
		}
    }

}
