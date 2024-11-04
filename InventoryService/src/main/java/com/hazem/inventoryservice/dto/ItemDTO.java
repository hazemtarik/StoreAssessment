package com.hazem.inventoryservice.dto;


import lombok.Builder;

@Builder
public record ItemDTO(String itemId, int availableQuantity) {
}
