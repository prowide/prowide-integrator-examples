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
package com.prowidesoftware.swift.samples.integrator;

import java.util.List;

import com.prowidesoftware.swift.model.BICRecord;

/**
 * Sample program to list all entries in the internal BIC directory, used to
 * validate BICs in messages.
 * 
 * @author www.prowidesoftware.com
 */
public class BICDirectoryExample {

    public static void main(final String[] args) throws Exception {
	
		final com.prowidesoftware.swift.BICDirectory bicDirectory = new com.prowidesoftware.swift.BICDirectory();
		long t0 = System.currentTimeMillis();
		final List<BICRecord> list = bicDirectory.query("CTI%", true);
		long t1 = System.currentTimeMillis();
	
		for (final BICRecord bicRecord : list) {
		    System.out.println(bicRecord);
		}
		System.out.println("Took " + (t1 - t0) + "ms");
	
		long t2 = System.currentTimeMillis();
		final List<String> st = bicDirectory.subtypes();
		long t3 = System.currentTimeMillis();
		for (final String s : st) {
		    System.out.println("> " + s);
		}
		System.out.println("Took " + (t3 - t2) + "ms");
    }

}
