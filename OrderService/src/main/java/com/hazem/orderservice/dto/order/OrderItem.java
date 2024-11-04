package com.hazem.orderservice.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


@Builder
public record OrderItem(
        @NotNull(message = "item id is required") String itemId,
        @Positive(message = "quantity should be more than 0") int requestQuantity
) {
}



