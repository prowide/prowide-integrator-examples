package com.prowidesoftware.swift.samples;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.validator.IValidationRuleCustomizer;
import com.prowidesoftware.swift.validator.RuleFilter;
import com.prowidesoftware.swift.validator.ValidationEngine;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationRule;

/**
 * Example of modifying the rules on the fly.
 * 
 * @author www.prowidesoftware.com
 */
public class LiveRulesUpdateExample {

	private static IValidationRuleCustomizer validationRuleCustomizer = new IValidationRuleCustomizer() {
		
		public void prepareRules(List<ValidationRule> rules) {
		}
	};

	public RuleFilter ruleFilter = new RuleFilter() {
		
		public void filter(SwiftMessage msg, String scheme, List<ValidationRule> rules) {
			/*
			 * Add a a custom rule which reports a problem for 'big' amounts for MT103 with a 100 as limit and MT102
			 * with a 500 limit. Other MTs are unchecked 
			 */
			if (msg.isMT("103")) {
				rules.add(new CustomRule(100));
			} else if (msg.isMT("102")) {
				rules.add(new CustomRule(500));
			}
		}
	};
	public static void main(String[] args) throws IOException {
		/*
		 * Load a message (see MessageParseExample)
		 */
		SwiftParser parser = new SwiftParser();
		parser.setReader(new FileReader("etc/mt103_customvalidated.txt"));
		/*
		 * msg contains java object from parsed message.
		 */
		SwiftMessage msg = parser.message();

		/*
		 * Create and initialize the validation engine
		 */
		ValidationEngine engine = new ValidationEngine();
		engine.initialize();
		engine.setValidationRuleCustomizer(validationRuleCustomizer);
		List<ValidationProblem> r = engine.validateMessage(msg);
		ValidationEngineTest.dumpProblems(r, msg);
	}
}
