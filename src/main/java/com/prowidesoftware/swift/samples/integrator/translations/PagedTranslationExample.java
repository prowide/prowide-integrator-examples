/*
 * Copyright (c) 2026 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.translations;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.translations.split.PagedMxToMtTranslation;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * This example translates a large camt.053 statement into a sequence of paginated MT940 messages
 * using the paged translation API.
 * <p>
 * The source is processed as a stream (StAX) with constant memory usage, so it can handle
 * statements of hundreds of megabytes that would not fit in memory as a parsed object. The
 * statement entries are split in pages of a configurable size, and the continuation is indicated
 * with field 28C page numbers and intermediate balances 60M/62M.
 * <p>
 * The page size is lowered to 2 entries here just to produce several output messages from a small
 * sample; the default is 50 entries per page.
 *
 * @since 10.3.44
 */
public class PagedTranslationExample {

    public static final String sample = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.053.001.08\">\n"
            + "  <BkToCstmrStmt>\n"
            + "    <GrpHdr>\n"
            + "      <MsgId>STMT2026081702</MsgId>\n"
            + "      <CreDtTm>2026-08-17T18:00:00+00:00</CreDtTm>\n"
            + "    </GrpHdr>\n"
            + "    <Stmt>\n"
            + "      <Id>STMT2026081702</Id>\n"
            + "      <ElctrncSeqNb>232</ElctrncSeqNb>\n"
            + "      <Acct><Id><IBAN>BE71096123456769</IBAN></Id></Acct>\n"
            + "      <Bal>\n"
            + "        <Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>\n"
            + "        <Amt Ccy=\"EUR\">10000.00</Amt>\n"
            + "        <CdtDbtInd>CRDT</CdtDbtInd>\n"
            + "        <Dt><Dt>2026-08-17</Dt></Dt>\n"
            + "      </Bal>\n"
            + "      <Bal>\n"
            + "        <Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>\n"
            + "        <Amt Ccy=\"EUR\">10500.00</Amt>\n"
            + "        <CdtDbtInd>CRDT</CdtDbtInd>\n"
            + "        <Dt><Dt>2026-08-17</Dt></Dt>\n"
            + "      </Bal>\n"
            + entries(5)
            + "    </Stmt>\n"
            + "  </BkToCstmrStmt>\n"
            + "</Document>";

    private static String entries(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            sb.append("      <Ntry>\n")
                    .append("        <Amt Ccy=\"EUR\">100.00</Amt>\n")
                    .append("        <CdtDbtInd>CRDT</CdtDbtInd>\n")
                    .append("        <Sts><Cd>BOOK</Cd></Sts>\n")
                    .append("        <BookgDt><Dt>2026-08-17</Dt></BookgDt>\n")
                    .append("        <ValDt><Dt>2026-08-17</Dt></ValDt>\n")
                    .append("        <BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>\n")
                    .append("        <NtryDtls><TxDtls><Refs><EndToEndId>E2E-")
                    .append(i)
                    .append("</EndToEndId></Refs></TxDtls></NtryDtls>\n")
                    .append("      </Ntry>\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        PagedMxToMtTranslation translation = new PagedMxToMtTranslation();

        // use small pages to produce several MT940 from the sample; the default page size is 50
        translation.getConf().setMaxDetailsPerPage(2);

        // in a real scenario this would be a FileInputStream over a huge statement file
        InputStream source = new ByteArrayInputStream(sample.getBytes(StandardCharsets.UTF_8));

        // the stream must be closed to release the underlying source, thus the try-with-resources
        try (Stream<AbstractMT> pages = translation.translate(source)) {
            pages.forEach(mt -> {
                System.out.println(mt.message());
                System.out.println();
            });
        }
    }
}
