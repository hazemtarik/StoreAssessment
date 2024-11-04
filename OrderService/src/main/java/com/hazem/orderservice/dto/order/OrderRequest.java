package com.hazem.orderservice.dto.order;

import lombok.Builder;

import java.util.Set;


@Builder
public record OrderRequest(Set<OrderItem> orderItems) {
}
