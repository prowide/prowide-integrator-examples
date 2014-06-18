package com.prowidesoftware.swift.samples;

import java.util.List;

import com.prowidesoftware.swift.model.BICRecord;

/*
 * 
 */
public class BICDirectory {

	public static void main(final String[] args) throws Exception {

		final com.prowidesoftware.swift.BICDirectory bicDirectory = new com.prowidesoftware.swift.BICDirectory();
		long t0 = System.currentTimeMillis();
		final List<BICRecord> list = bicDirectory.query("CTI%", true);
		long t1 = System.currentTimeMillis();
		
		for (final BICRecord bicRecord : list) {
			System.out.println(bicRecord);
		}
		System.out.println("Took "+(t1-t0)+"ms");
		
		long t2 = System.currentTimeMillis();
		final List<String> st = bicDirectory.subtypes();
		long t3 = System.currentTimeMillis();
		for (final String s : st) {
			System.out.println("> " + s);
		}
		System.out.println("Took "+(t3-t2)+"ms");
	}

}
