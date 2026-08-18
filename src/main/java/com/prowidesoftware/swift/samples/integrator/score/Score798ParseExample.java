/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.score;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt7xx.MT760;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798_763_LC_C2B;

/**
 * This example parses SWIFT for Corporates (SCORE) messages, which are trade finance sub-messages
 * enclosed in an MT798 envelope, using the model provided by the pw-swift-integrator-score module.
 * <p>
 * Two approaches are shown:
 * <ul>
 *   <li><b>Typed parse</b>: each SCORE structure has a specific model class such as
 *   {@code MT798_763_LC_C2B} (sub-message type 763, letter of credit, corporate to bank), with
 *   getters for the complete combined structure.</li>
 *   <li><b>Envelope plus payload</b>: when the sub-message after field 77E is a standard MT (the
 *   type is indicated in field 12), the generic {@code MT798.getSubMessage()} extracts it, and it
 *   can then be converted to its specific MT class.</li>
 * </ul>
 *
 * @since 7.7
 */
public class Score798ParseExample {

    public static final String sample763 = "{1:F01AAAADEM0AXXX0000000000}{2:I798BBBBITRRXMCEN}{4:\n"
            + ":20:TRE96372\n"
            + ":12:763\n"
            + ":77E:\n"
            + ":27A:1/2\n"
            + ":21A:XYZ999\n"
            + ":21T:XYZ111\n"
            + ":20:PGFFA0815\n"
            + ":13E:202608171433\n"
            + "-}";

    public static final String sample760 = "{1:F01AAAADEM0AXXX0000000000}{2:I798BBBBITRRXMCEN}{4:\n"
            + ":20:TRE96373\n"
            + ":12:760\n"
            + ":77E:\n"
            + ":27:1/1\n"
            + ":15A:\n"
            + ":27A:2/2\n"
            + ":21A:XYZ999\n"
            + ":15B:\n"
            + ":20:PGFFA0816\n"
            + ":30:260817\n"
            + ":22D:GUAR\n"
            + ":40C:ISPR\n"
            + ":23B:FIXD\n"
            + ":31E:270817\n"
            + ":50:APPLICANT COMPANY\n"
            + ":59:BENEFICIARY COMPANY\n"
            + ":32B:USD100000,\n"
            + ":77U:GUARANTEE TERMS AND CONDITIONS\n"
            + "-}";

    public static void main(String[] args) throws Exception {
        // typed parse: the complete SCORE structure in a single model class
        MT798_763_LC_C2B typed = MT798_763_LC_C2B.parse(sample763);
        System.out.println("Sub-message type: " + typed.getField12().getValue());
        System.out.println("Envelope reference: " + typed.getField20().get(0).getValue());
        System.out.println("Chunk: " + typed.getField27A().getValue());

        // generic envelope parse, extracting the standard MT payload after field 77E
        MT798 envelope = MT798.parse(sample760);
        SwiftMessage subMessage = envelope.getSubMessage();
        MT760 guarantee = (MT760) subMessage.toMT();
        System.out.println("Guarantee amount: " + guarantee.getField32B().get(0).getValue());
    }
}
