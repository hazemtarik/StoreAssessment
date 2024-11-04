package com.hazem.orderservice.config.exception;


import com.hazem.orderservice.config.shared.ApiResponse;
import com.hazem.orderservice.config.shared.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleItemNotFoundException(ResourceNotFoundException ex) {
        return Response.createFailResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleInsufficientStockException(BadRequestException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<ApiResponse> handleObjectValidationException(ObjectValidationException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse> handleInsufficientStockException(InsufficientStockException ex) {
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

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        return Response.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ApiResponse> handleServerErrorException(ServerErrorException ex) {
        return Response.createFailResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

}
