package com.prowidesoftware.swift.samples;

import java.util.List;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;

public class MT103_SemanticTestExample {

	void showSemantic2() throws Exception {
		
		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":32A:010113USD200,00\n"+
				":33B:USD200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:BEN\n"+
				":71G:EUR10,\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}

	void showSemantic150() throws Exception {
		
		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":32A:010113USD200,00\n"+
				":33B:USD200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:SHA\n"+
				":71G:USD10,\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}
	

	void showSemantic151() throws Exception {
		
		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":32A:010113USD200,00\n"+
				// ":33B:ARS200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:OUR\n"+
				":71G:USD10,\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}
	
	void showSemantic157() throws Exception {
		
		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":32A:010113USD200,00\n"+
				 ":33B:USD200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:BEN\n"+
				":71G:USD0,\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}
	
	void showSemantic175() throws Exception {	

		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":32A:010113USD200,00\n"+
				":33B:ARS200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:OUR\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}
	
	void showSemantic197() throws Exception {
		
		String mt = Lib.B1 + Lib.B2_103 + "{4:\n:20:777777M350\n"+
				":23B:SSTD\n"+
				":23E:FOOO/1234567\n"+
				":32A:010113USD200,00\n"+
				":33B:USD200,00\n"+
				":50K:TRUST BANK\n"+
				"FUND\n"+
				":53B:/1111YYYYYY\n"+
				":57A:CHASUS33\n"+
				":59:/222YYYYYY\n"+
				"JP\n"+
				":71A:BEN\n"+
				":71G:USD10,\n"+
				":72:/TELE/ IN FAVOUR OF\n"+
				"//A/C R-000000\n-}";
		
		SwiftMessage msg = new SwiftParser(mt).message();
		List<ValidationProblem> r = new ValidationEngine().validateMessage(msg, false);
		//List<ValidationProblem> r = engine.validateMessage(msg, "103_STP");
		Lib.dumpProblems(r, msg);
	}
	
	public static void main(String[] args) throws Exception {
		final MT103_SemanticTestExample main = new MT103_SemanticTestExample();
		System.out.println("SEMANTIC RULE 2");
		main.showSemantic2();
		System.out.println("SEMANTIC RULE 150");
		main.showSemantic150();
		System.out.println("SEMANTIC RULE 151");
		main.showSemantic151();
		System.out.println("SEMANTIC RULE 157");
		main.showSemantic157();
		System.out.println("SEMANTIC RULE 175");
		main.showSemantic175();
		System.out.println("SEMANTIC RULE 197");
		main.showSemantic197();
	}
}
