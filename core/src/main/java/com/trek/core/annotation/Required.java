package com.trek.core.annotation;


import com.trek.core.annotation.constraints.RequiredValidatorForBooleanConstraint;
import com.trek.core.annotation.constraints.RequiredValidatorForListConstraint;
import com.trek.core.annotation.constraints.RequiredValidatorForNumberConstraint;
import com.trek.core.annotation.constraints.RequiredValidatorForStringConstraint;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author udaya
 */
@Target(
        {ElementType.FIELD, ElementType.ANNOTATION_TYPE}
)
@Constraint(validatedBy = {
        RequiredValidatorForNumberConstraint.class,
        RequiredValidatorForBooleanConstraint.class,
        RequiredValidatorForListConstraint.class,
        RequiredValidatorForStringConstraint.class
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
    String message() default "Please provide valid {field}";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};

    String field();

    int min() default 2;

    int max() default 255;
}