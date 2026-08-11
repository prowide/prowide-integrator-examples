/*
 * Copyright (c) 2024 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Health check for the Prowide Integrator jars present on the classpath.
 * <p>
 * For every known Integrator module it tries to load a probe class and, if found,
 * reads the {@code Implementation-Version} attribute from the containing jar manifest.
 * Missing modules are reported but do not abort the run, so a prospect using a
 * partial trial can quickly see which examples will work.
 * <p>
 * Run from the IDE (main method) or via {@code ./gradlew doctor}.
 */
public class VersionChecker {

    /**
     * (module label, probe class FQCN...) entries. The probe class is a stable, public type
     * shipped by each module. We avoid touching internal/generated packages.
     * <p>
     * More than one probe can be declared per module, and the first one found wins. This is needed
     * for modules where the model classes were relocated between releases, for example the SIC
     * messages moved into version specific packages in SRU2026.
     */
    private static final List<String[]> MODULES = Arrays.asList(
            new String[] {"pw-swift-core", "com.prowidesoftware.swift.model.MtSwiftMessage"},
            new String[] {"pw-iso20022", "com.prowidesoftware.swift.model.MxSwiftMessage"},
            new String[] {"pw-swift-integrator-sdk", "com.prowidesoftware.swift.SRU"},
            new String[] {"pw-swift-integrator-validation", "com.prowidesoftware.swift.validator.ValidationEngine"},
            new String[] {"pw-swift-integrator-translations", "com.prowidesoftware.swift.app.TranslatorApp"},
            new String[] {"pw-swift-integrator-myformat", "com.prowidesoftware.swift.myformat.MyFormatEngine"},
            new String[] {"pw-swift-integrator-cbpr", "com.prowidesoftware.swift.model.mx.cbpr.CbprMessageType"},
            new String[] {"pw-swift-integrator-score", "com.prowidesoftware.swift.model.mt.mt7xx.MT798_727_LC_B2C"},
            new String[] {
                "pw-swift-integrator-sic",
                "com.prowidesoftware.swift.model.mx.sic.v4_10.MxCamt00500108Ch01",
                "com.prowidesoftware.swift.model.mx.sic.MxCamt00500108Ch01"
            });

    /**
     * The data jar carries no classes, it is the Derby database read from the classpath, so it is
     * probed by resource instead. It is required by BICDirectoryExample and ExpandedPrintoutExample.
     */
    private static final String DATA_JAR = "pw-swift-integrator-data";

    private static final String DATA_JAR_PROBE = "pw-swift-integrator-data/service.properties";

    public static String getImplementationVersion(Class<?> clazz) {
        try {
            String classPath = Objects.requireNonNull(clazz.getResource(clazz.getSimpleName() + ".class"))
                    .toString();
            int bang = classPath.lastIndexOf('!');
            if (bang < 0) {
                return null; // class is not loaded from a jar (e.g., a build folder)
            }
            String manifestPath = classPath.substring(0, bang + 1) + "/META-INF/MANIFEST.MF";
            URL manifestUrl = new URL(manifestPath);
            Manifest manifest = new Manifest(manifestUrl.openStream());
            Attributes attr = manifest.getMainAttributes();
            return attr.getValue("Implementation-Version");
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Prowide Integrator — module health check");
        System.out.println("----------------------------------------");

        int present = 0, missing = 0;
        for (String[] entry : MODULES) {
            String module = entry[0];
            Class<?> clazz = null;
            for (int i = 1; i < entry.length && clazz == null; i++) {
                try {
                    clazz = Class.forName(entry[i]);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    // try the next probe class for this module
                }
            }
            if (clazz != null) {
                String version = getImplementationVersion(clazz);
                System.out.printf(
                        "  [OK]      %-38s %s%n", module, version != null ? version : "(version not in manifest)");
                present++;
            } else {
                System.out.printf("  [MISSING] %-38s (drop the jar into lib/)%n", module);
                missing++;
            }
        }

        if (VersionChecker.class.getClassLoader().getResource(DATA_JAR_PROBE) != null) {
            System.out.printf("  [OK]      %-38s %s%n", DATA_JAR, "(BIC directory database)");
            present++;
        } else {
            System.out.printf("  [MISSING] %-38s (drop the jar into lib/)%n", DATA_JAR);
            missing++;
        }

        System.out.println();
        System.out.printf("Summary: %d module(s) detected, %d missing.%n", present, missing);
        if (missing > 0) {
            System.out.println("Examples for missing modules will fail at runtime.");
        }
        System.out.println();
    }
}
