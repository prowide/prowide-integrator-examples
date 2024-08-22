/*
 * Copyright (c) 2024 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */

package com.prowidesoftware.swift.samples.integrator;

import com.prowidesoftware.swift.SRU;
import com.prowidesoftware.swift.app.TranslatorApp;
import com.prowidesoftware.swift.model.MtSwiftMessage;
import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.model.mt.MtTypeScore;
import com.prowidesoftware.swift.model.mx.cbpr.CbprMessageType;
import com.prowidesoftware.swift.myformat.MyFormatEngine;
import com.prowidesoftware.swift.validator.ValidationEngine;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class VersionChecker {

    public static String getImplementationVersion(Class<?> clazz) {
        String version = null;
        try {
            String classPath = Objects.requireNonNull(clazz.getResource(clazz.getSimpleName() + ".class")).toString();
            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
            URL manifestUrl = new URL(manifestPath);
            Manifest manifest = new Manifest(manifestUrl.openStream());
            Attributes attr = manifest.getMainAttributes();
            version = attr.getValue("Implementation-Version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void main(String[] args) {
        String iso20022_version = getImplementationVersion(MxSwiftMessage.class);
        System.out.println("Iso20022 Version: " + iso20022_version);

        String core_version = getImplementationVersion(MtSwiftMessage.class);
        System.out.println("Core Version: " + core_version);

        String cbpr_version = getImplementationVersion(CbprMessageType.class);
        System.out.println("Cbpr Version: " + cbpr_version);

        String myFormatEngine_version = getImplementationVersion(MyFormatEngine.class);
        System.out.println("MyFormat Version: " + myFormatEngine_version);

        String score_version = getImplementationVersion(MtTypeScore.class);
        System.out.println("Score Version: " + score_version);

        String sdk_version = getImplementationVersion(SRU.class);
        System.out.println("Sdk Version: " + sdk_version);

        String translations_version = getImplementationVersion(TranslatorApp.class);
        System.out.println("Translations Version: " + translations_version);

        String validation_version = getImplementationVersion(ValidationEngine.class);
        System.out.println("Validation Version: " + validation_version);
    }
}
