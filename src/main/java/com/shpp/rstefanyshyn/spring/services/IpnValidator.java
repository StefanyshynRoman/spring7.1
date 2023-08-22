package com.shpp.rstefanyshyn.spring.services;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MyValidator.class)
@Target({ElementType.TYPE, FIELD})
@Retention(RUNTIME)
@Documented
public @interface IpnValidator {
    String message() default "IPN is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}