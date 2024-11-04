package com.hazem.inventoryservice.config.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<ApiResponse> createSuccessResponse(HttpStatus httpStatus, Object data) {
        return ResponseEntity.status(httpStatus)
                .body(
                        ApiResponse.builder()
                                .success(true)
                                .code(httpStatus.value())
                                .status(httpStatus.name())
                                .data(data)
                                .build()
                );
    }

    public static ResponseEntity<ApiResponse> createSuccessResponse(HttpStatus httpStatus, Object data, String message) {
        return ResponseEntity.status(httpStatus)
                .body(
                        ApiResponse.builder()
                                .success(true)
                                .code(httpStatus.value())
                                .status(httpStatus.name())
                                .message(message)
                                .data(data)
                                .build()
                );
    }

    public static ResponseEntity<ApiResponse> createFailResponse(HttpStatus httpStatus, String error) {
        return ResponseEntity.status(httpStatus)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .code(httpStatus.value())
                                .status(httpStatus.name())
                                .error(error)
                                .build()
                );
    }

}
