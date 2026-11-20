package com.trek.core.annotation.constraints;

import com.trek.core.annotation.Required;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidatorForBooleanConstraint implements ConstraintValidator<Required, Boolean> {

    @Override
    public boolean isValid(Boolean aBoolean, ConstraintValidatorContext constraintValidatorContext) {
        return aBoolean != null && (aBoolean.equals(Boolean.TRUE) || aBoolean.equals(Boolean.FALSE));
    }
}
