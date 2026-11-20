package com.trek.core.annotation.constraints;

import com.trek.core.annotation.Required;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author udaya
 */
public class RequiredValidatorForStringConstraint implements ConstraintValidator<Required, String> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(Required constraintAnnotation) {
        minLength = constraintAnnotation.min();
        maxLength = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String trimmedValue = value.trim();

        return !isFirstOrLastCharWhiteSpace(value)
                && isLengthWithinLimit(trimmedValue);
    }

    private boolean isWhiteSpace(String value, int index) {
        return Character.isWhitespace(value.charAt(index));
    }

    private boolean isFirstOrLastCharWhiteSpace(String value) {
        return isWhiteSpace(value, 0) || isWhiteSpace(value, value.length() - 1);
    }

    private boolean isLengthWithinLimit(String value) {
        return value.length() >= this.minLength && value.length() <= this.maxLength;
    }
}