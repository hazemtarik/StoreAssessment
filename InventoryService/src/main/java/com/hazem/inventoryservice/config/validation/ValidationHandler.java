package com.hazem.inventoryservice.config.validation;

import com.hazem.inventoryservice.config.exception.ObjectValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import java.util.Set;
import java.util.stream.Collectors;

public class ValidationHandler {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();


    public static void validate(Object t) {
        Set<ConstraintViolation<Object>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ObjectValidationException(message);
        }
    }


}
