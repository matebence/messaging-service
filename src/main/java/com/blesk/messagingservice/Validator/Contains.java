package com.blesk.messagingservice.Validator;

import com.blesk.messagingservice.Value.Messages;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ContainsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Contains {

    public Class<? extends Enum<?>> enumClass();

    public String message() default Messages.CONTAINS_VALIDATOR_DEFAULT;

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}