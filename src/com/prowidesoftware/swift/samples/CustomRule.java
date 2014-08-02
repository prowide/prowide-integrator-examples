package com.prowidesoftware.swift.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.validator.ProblemType;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationRule;
import com.prowidesoftware.swift.validator.internal.AffectedMTs;

/**
 * Custom validation rule.
 * 
 * @see MessageValidationWithCustomRulesExample
 * @author www.prowidesoftware.com
 */
@AffectedMTs(103)
public class CustomRule extends ValidationRule {
	public static class AmountTooBigProblem extends ValidationProblem {
		private Field field;
		
		public AmountTooBigProblem(Field f) {
			setProblemType(ProblemType.CUSTOM);
			this.field = f;
		}

		private static final long serialVersionUID = 1L;
		
		@Override
		public String getMessage(Locale locale) {
			return "AmountTooBig[field: "+this.field.getName()+"]";
		}
	}

	private int amountLimit;
	
	public CustomRule() {
		this(1000000);
	}
	
	public CustomRule(int limit) {
		this.amountLimit = limit;
	}

	@Override
	protected List<ValidationProblem> eval(SwiftMessage msg, String mt) {
		List<ValidationProblem> result = new ArrayList<ValidationProblem>();
		if (msg.isMT("103")) {
			final Field32A f32A = (Field32A) msg.getBlock4().getFieldByName("32A");
			if (f32A != null && f32A.getComponent3AsNumber().intValue() >= amountLimit) {
				result.add(new AmountTooBigProblem(f32A));
			}
		}
		return result;
	}

}
