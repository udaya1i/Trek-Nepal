package com.trek.core.annotation;
import com.trek.core.annotation.constraints.MaxImagesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxImagesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxImages {
    int value() default 10;
    String message() default "Number of images must not exceed {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}