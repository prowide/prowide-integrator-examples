/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.sdk;

import com.prowidesoftware.swift.reference.ExternalCodeSetsUtils;
import java.util.Set;

/**
 * This example queries the ISO 20022 external code sets bundled with the SDK.
 * <p>
 * Many MX elements are typed as external codes (purpose, category purpose, local instrument,
 * reason codes, etc.), whose valid values are published by ISO outside the XSD schemas. This API
 * gives programmatic access to those lists, for instance to populate combo boxes or validate
 * values before creating a message. The MX validation engine can also check these codes
 * automatically, see the validation examples.
 *
 * @since 10.3.0
 */
public class ExternalCodeSetsExample {

    public static void main(String[] args) {
        Set<String> names = ExternalCodeSetsUtils.getAllCodeNames();
        System.out.println(names.size() + " external code sets available");

        Set<String> categoryPurpose = ExternalCodeSetsUtils.getCodes("ExternalCategoryPurpose1Code");
        System.out.println("ExternalCategoryPurpose1Code: " + categoryPurpose);

        System.out.println("Is SALA a valid category purpose? " + categoryPurpose.contains("SALA"));
        System.out.println("Is QQQQ a valid category purpose? " + categoryPurpose.contains("QQQQ"));
    }
}
