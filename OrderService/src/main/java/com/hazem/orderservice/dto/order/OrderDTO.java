package com.hazem.orderservice.dto.order;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


@Builder
public record OrderDTO(String OrderId,
                       List<OrderItem> orderItemsList,
                       String status,
                       LocalDateTime orderDate
) {
}
