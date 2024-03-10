package project.first.spring.processData.config;

import project.first.spring.processData.model.enums.Rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AppliedRule {
    Rules ruleName() default Rules.ASCENDING;
}