package com.blesk.messagingservice.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContainsValidator implements ConstraintValidator<Contains, String> {

    private List<String> list = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.list.contains(value.toUpperCase());
    }

    @Override
    public void initialize(Contains constraintAnnotation) {
        this.list = new ArrayList<String>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();
        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            this.list.add(enumVal.toString().toUpperCase());
        }
    }
}