package com.hazem.orderservice.config.shared;


import lombok.Builder;

@Builder
public record ApiResponse(
        boolean success,
        int code,
        String status,
        String message,
        String error,
        Object data
) {
}
