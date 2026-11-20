package com.trek.core.annotation;

import com.trek.core.annotation.constraints.EnumValidatorConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {

    String message() default "Please provide valid {field}";

    Class<?>[] groups() default {};

    boolean required() default false;

    Class<? extends Payload>[] payload() default {};

    String field();

    Class<? extends Enum<?>> enumClass();
}