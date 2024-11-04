package com.hazem.inventoryservice.config.exception;

public class ObjectValidationException extends RuntimeException {
    public ObjectValidationException(String message) {
        super(message);
    }
}
