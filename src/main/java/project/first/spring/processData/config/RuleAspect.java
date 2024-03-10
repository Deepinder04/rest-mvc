package project.first.spring.processData.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RuleAspect {

    @Pointcut("@annotation(project.first.spring.processData.config.AppliedRule)" +
                " || within(project.first.spring.processData.rules.*)")
    public void appliedRuleAnnotation(){}

    @Around(value = "appliedRuleAnnotation()")
    public Object runningRule(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("Inside runningRule aspect");
        Signature methodSignature = joinPoint.getSignature();
        MethodSignature signature = (MethodSignature) methodSignature;
        Method method = joinPoint.getTarget().getClass()
                .getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());

        Class<?> declaringClass = method.getDeclaringClass();
        AppliedRule ruleAnnotation = declaringClass.getAnnotation(AppliedRule.class);

        Object[] args = joinPoint.getArgs();
        String journey = (String) args[1];

        if(ruleAnnotation.ruleName().name().equals(journey)){
            return joinPoint.proceed();
        }
        return null;
    }
}
