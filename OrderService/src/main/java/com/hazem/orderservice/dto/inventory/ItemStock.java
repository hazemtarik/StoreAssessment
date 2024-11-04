package com.hazem.orderservice.dto.inventory;


import lombok.Builder;

@Builder
public record ItemStock(String itemId, int availableQuantity) {
}
