package com.hazem.inventoryservice.config.exception;


import com.hazem.inventoryservice.config.shared.ApiResponse;
import com.hazem.inventoryservice.config.shared.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        return Response.createFailResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse> handleInsufficientStockException(InsufficientStockException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<ApiResponse> handleObjectValidationException(ObjectValidationException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        return Response.createFailResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

}
