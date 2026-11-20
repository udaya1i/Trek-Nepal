package com.trek.core.annotation.constraints;

import com.trek.core.annotation.Required;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidatorForNumberConstraint implements ConstraintValidator<Required, Number> {

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        return number != null && number.doubleValue() >= 0;
    }

}
