/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator;

import com.prowidesoftware.swift.model.BICRecord;

import java.util.List;

/**
 * Sample program to list all entries in the internal BIC directory, used to
 * validate BICs in messages.
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
