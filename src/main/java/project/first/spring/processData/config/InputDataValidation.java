package project.first.spring.processData.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = InputDataValidator.class)
public @interface InputDataValidation {
    String message() default "The entered list is empty or has only one element.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
