package com.prowidesoftware.swift.samples;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.SemanticProblem;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

public class MT103_RuleE10Example {
	final static String msg1 = "{1:F01CCRTIT2TAXXX8031666656}{2:O1030601130510BNPAFRPPAXXX96340757071305100632N}{3:{108:AFZ0194073129000}}{4:\n" + 
			":20:TFMI218180540510\n" + 
			":23B:CRED\n" + 
			":32A:130510EUR1920,4\n" + 
			":33B:EUR1940,4\n" + 
			":50K:/G102528\n" + 
			"VIAS IMPORTS LTD\n" + 
			"EURO CALL ACCOUNT\n" + 
			"875 SIXTH AVENUE 22ND FLOOR\n" + 
			"NEW YORK, NY 10001\n" + 
			":52A:BWSTUS66XXX\n" + 
			":57A:CCRTIT2TXXX\n" + 
			":59:IT19U0834474291000005813560\n" + 
			"AZ AG COLLETONNO\n" + 
			"LOCALITA COLLETONO\n" + 
			"08012 ANAGNI FR IT\n" + 
			":70:FT.6\n" + 
			":71A:SHA\n" + 
			":71F:EUR20,\n" + 
			"-}{5:{CHK:71CA9706E862}}{S:{SAC:}{COP:P}{SIG:<SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>BSPOrapb9zOuLZaGdim66jnYURPqCgmQNCzl2f/8TMU=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY\n" + 
			"Content-Domain: RFC822\n" + 
			"EntrustFile-Version: 2.0\n" + 
			"Originator-DN: cn=snl-10607,ou=fin,o=bnpafrpp,o=swift\n" + 
			"Orig-SN: 1339901268\n" + 
			"MIC-Info: SHA256, RSA,\n" + 
			" Vr5BPVHUYtI4GKc+XJTtU0m2DHuSVM+gk5Bb9aKmuUG58pYbOL78w7505M8BJogE\n" + 
			" RnGR4QqaIEUhRSRF7P0tCxopdjXzEvP9sFZZHlAtPG2azMEugsf9VH+1KkOGCpNG\n" + 
			" 4zJP3q8K3uJkRQaIthHZu0tr/SRHcctjz5CwmwAoErCyeagjA6WuO+1JPbGOgVfa\n" + 
			" oxcvxoAO22DbHmJ1lx/UZcGbKdjN4JcVeyIieqiPunfe1sNAsn//Ss35FNdTfKYt\n" + 
			" jA/LYjUrGeXFGqRZ4+zWN30FsQiwwZLH4nOoUkmEtbqdVZzILXZgCcFNkbzjM3sb\n" + 
			" BJ12mZQrIv419ExeFUhs+g==\n" + 
			"</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=snl-10607,ou=fin,o=bnpafrpp,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>M</Sw:DigestRef><Sw:DigestValue>Lbb2hNE4zMg/W0+IT4vcqZ8/yvzXnOQxtPQSf9foK1M=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature>}}"; 
	final static String msg2 = "{1:F01CCRTIT2TAXXX8031666656}{2:O1030601130510BNPAFRPPAXXX96340757071305100632N}{3:{108:AFZ0194073129000}}{4:\n" + 
			":20:TFMI218180540510\n" + 
			":23B:CRED\n" + 
			":32A:130510EUR1920,4\n" + 
			":33B:EUR1940,4\n" + 
			":50K:/G102528\n" + 
			"VIAS IMPORTS LTD\n" + 
			"EURO CALL ACCOUNT\n" + 
			"875 SIXTH AVENUE 22ND FLOOR\n" + 
			"NEW YORK, NY 10001\n" + 
			":52A:BWSTUS66XXX\n" + 
			":57A:CCRTIT2TXXX\n" + 
			":59:/IT19U0834474291000005813560\n" + 
			"AZ AG COLLETONNO\n" + 
			"LOCALITA COLLETONO\n" + 
			"08012 ANAGNI FR IT\n" + 
			":70:FT.6\n" + 
			":71A:SHA\n" + 
			":71F:EUR20,\n" + 
			"-}{5:{CHK:71CA9706E862}}{S:{SAC:}{COP:P}{SIG:<SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>BSPOrapb9zOuLZaGdim66jnYURPqCgmQNCzl2f/8TMU=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY\n" + 
			"Content-Domain: RFC822\n" + 
			"EntrustFile-Version: 2.0\n" + 
			"Originator-DN: cn=snl-10607,ou=fin,o=bnpafrpp,o=swift\n" + 
			"Orig-SN: 1339901268\n" + 
			"MIC-Info: SHA256, RSA,\n" + 
			" Vr5BPVHUYtI4GKc+XJTtU0m2DHuSVM+gk5Bb9aKmuUG58pYbOL78w7505M8BJogE\n" + 
			" RnGR4QqaIEUhRSRF7P0tCxopdjXzEvP9sFZZHlAtPG2azMEugsf9VH+1KkOGCpNG\n" + 
			" 4zJP3q8K3uJkRQaIthHZu0tr/SRHcctjz5CwmwAoErCyeagjA6WuO+1JPbGOgVfa\n" + 
			" oxcvxoAO22DbHmJ1lx/UZcGbKdjN4JcVeyIieqiPunfe1sNAsn//Ss35FNdTfKYt\n" + 
			" jA/LYjUrGeXFGqRZ4+zWN30FsQiwwZLH4nOoUkmEtbqdVZzILXZgCcFNkbzjM3sb\n" + 
			" BJ12mZQrIv419ExeFUhs+g==\n" + 
			"</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=snl-10607,ou=fin,o=bnpafrpp,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>M</Sw:DigestRef><Sw:DigestValue>Lbb2hNE4zMg/W0+IT4vcqZ8/yvzXnOQxtPQSf9foK1M=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature>}}"; 

	public static void main(String[] args) throws IOException {
		SwiftMessage m1 = new SwiftParser(msg1).message();
		SwiftMessage m2 = new SwiftParser(msg2).message();
		ValidationEngine engine = new ValidationEngine();
		List<ValidationProblem> r1 = engine.validateMessage(m1);
		List<ValidationProblem> r2 = engine.validateMessage(m2);
		// missing slash in :59: in m1
		System.out.println("r1 code: "+r1.get(0).getErrorKey());
		// m2 contains leading / in :59:
		System.out.println("r2 empty: "+r2.isEmpty());
	}
}
