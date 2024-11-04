package com.hazem.orderservice.config.exception;

public class ObjectValidationException extends RuntimeException {
    public ObjectValidationException(String message) {
        super(message);
    }
}
