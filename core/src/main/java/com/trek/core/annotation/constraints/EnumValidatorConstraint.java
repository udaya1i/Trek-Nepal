package com.trek.core.annotation.constraints;

import com.trek.core.annotation.EnumValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean required;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!required && value == null) {
            return true;
        }

        if (required && value == null) {
            return false;
        }

        if (enumClass.isInstance(value)) {
            return true;
        }

        if (value instanceof String strValue) {
            return Arrays.stream(enumClass.getEnumConstants())
                    .anyMatch(e -> e.name().equals(strValue));
        }

        return false;
    }
}
