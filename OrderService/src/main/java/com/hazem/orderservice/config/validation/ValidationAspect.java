package com.hazem.orderservice.config.validation;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {


    @Before("execution(* com.hazem.orderservice.service..*(*))")
    public void beforeValidation(JoinPoint joinPoint) {
        for (Object object : joinPoint.getArgs()) {
            ValidationHandler.validate(object);
            if (object instanceof Iterable<?>) {
                for (Object item : (Iterable<?>) object) {
                    ValidationHandler.validate(item);
                }
            }
        }
    }

}
