package com.trek.core.annotation.constraints;

import com.trek.core.annotation.Required;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class RequiredValidatorForListConstraint implements ConstraintValidator<Required, List<String>> {
    @Override
    public boolean isValid(
            List<String> value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && !value.isEmpty();
    }
}