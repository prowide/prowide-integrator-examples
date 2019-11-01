package com.prowidesoftware.swift.samples.integrator;

import com.prowidesoftware.swift.model.MtSwiftMessage;
import com.prowidesoftware.swift.model.MxSwiftMessage;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mx.MxPacs00800107;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This example prints the different Json representation supported by the MT and MX model.
 */
public class JsonExample {

    public static void main(String[] args) throws IOException {
        // Json for MTnnn classes with text block content parsed into individuals fields
        String json1 = MT103.parse(Lib.readResource("mt103.txt")).toJson();

        // Json for the persistence model for MT, including the raw FIN content plus metadata
        String json2 = new MtSwiftMessage(Lib.readResource("mt103.txt")).toJson();

        // Json with specific structure for elements of the parsed MX (does not includes header data)
        String json3 = MxPacs00800107.parse(Lib.readResource("pacs.008.001.07.xml")).toJson();

        // Json for the persistence model for MX, including the raw XML content plus metadata
        String json4 = new MxSwiftMessage(Lib.readResource("pacs.008.001.07.xml")).toJson();

        Files.write(Paths.get("/tmp/json1.json"), json1.getBytes());
        Files.write(Paths.get("/tmp/json2.json"), json2.getBytes());
        Files.write(Paths.get("/tmp/json3.json"), json3.getBytes());
        Files.write(Paths.get("/tmp/json4.json"), json4.getBytes());
    }

}
