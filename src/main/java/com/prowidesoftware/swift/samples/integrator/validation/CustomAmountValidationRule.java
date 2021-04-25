/*
 * Copyright (c) 2021 Prowide Inc.
 * All rights reserved. This program and the accompanying materials are made available under the terms of private
 * license agreements between Prowide Inc. and its commercial customers and partners.
 */
package com.prowidesoftware.swift.samples.integrator.validation;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.validator.ProblemType;
import com.prowidesoftware.swift.validator.ValidationProblem;
import com.prowidesoftware.swift.validator.ValidationRule;
import com.prowidesoftware.swift.validator.internal.AffectedMTs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This examples shows how to implement a custom validation rule using parameters
 * for the rule and also a custom validation problem with a customized message.
 *
 * <p>@see MessageValidationWithCustomRulesExample to see how the rule is injected
 * in the validation engine</p>
 *
 * @since 7.8.4
 */
@AffectedMTs(103)
public class CustomAmountValidationRule extends ValidationRule {

    private final BigDecimal limit;

    public CustomAmountValidationRule() {
        this(new BigDecimal("1000000"));
    }

    public CustomAmountValidationRule(final BigDecimal limit) {
        this.limit = limit;
    }

    @Override
    protected List<ValidationProblem> eval(SwiftMessage msg, String mt) {
        List<ValidationProblem> result = new ArrayList<ValidationProblem>();
        final Field32A f32A = (Field32A) msg.getBlock4().getFieldByName("32A");
        if (f32A != null && f32A.getAmountBigDecimal().compareTo(limit) >= 0) {
            result.add(new AmountTooBigProblem(f32A, this.limit, f32A.getAmountBigDecimal()));
        }
        return result;
    }

    public static class AmountTooBigProblem extends ValidationProblem {
        private static final long serialVersionUID = 1L;
        private final Field field;
        private final BigDecimal limit;
        private final BigDecimal found;

        /*
         * Creates the validation problem from specific parameters to format the user message.
         * Another alternative would be to use the variables Map available in super class.
         */
        public AmountTooBigProblem(final Field f, final BigDecimal limit, final BigDecimal found) {
            setProblemType(ProblemType.CUSTOM);
            setErrorKey("AMOUNT_LIMIT_EXCEEDED");
            this.limit = limit;
            this.field = f;
            this.found = found;
        }

        @Override
        public String getMessage(Locale locale) {
            /*
             * this message could be formatted differently for each supported locale language
             */
            return "Maximum amount is " + this.limit + " and found " + this.found + " in field " + this.field.getName();
        }
    }

}
