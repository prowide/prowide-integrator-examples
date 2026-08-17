/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.io.printout.mx.MxPrintoutWriter;
import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.utils.Lib;
import java.io.IOException;

/**
 * This example creates a human readable, expanded printout of an ISO 20022 message: each XML
 * element is printed with its business label instead of the short tag name, and amounts are
 * formatted according to the currency.
 * <p>
 * This is the MX counterpart of {@link ExpandedPrintoutExample}. The output format can be
 * customized providing a {@code MxPrintoutVisitor} implementation to the writer constructor, and a
 * BIC directory can also be provided to expand BIC codes into institution names.
 * <p>
 * This example requires the pw-swift-integrator-data jar in the classpath, containing the labels.
 *
 * @since 9.2.25
 */
public class MxExpandedPrintoutExample {

    public static void main(String[] args) throws IOException {
        MxSwiftMessage mx = new MxSwiftMessage(Lib.readResource("pacs.008.001.07.xml"));

        MxPrintoutWriter writer = new MxPrintoutWriter();
        System.out.println(writer.print(mx));
    }
}
