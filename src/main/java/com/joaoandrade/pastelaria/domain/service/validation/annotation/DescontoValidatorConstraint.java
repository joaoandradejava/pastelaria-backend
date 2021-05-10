package com.joaoandrade.pastelaria.domain.service.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescontoValidatorConstraint implements ConstraintValidator<Desconto, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		return value < 0 || value > 100 ? false : true;
	}

}
