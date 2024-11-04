package com.hazem.inventoryservice.config.validation;

import jakarta.validation.ValidatorFactory;

public class Validator {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Override
    public String apply(Object t) {
        Set<ConstraintViolation<Object>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new RequestValidationException(message);
        }
        return "";
    }

    public static void validate(Object t) {
        Set<ConstraintViolation<Object>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new RequestValidationException(message);
        }
    }


}
