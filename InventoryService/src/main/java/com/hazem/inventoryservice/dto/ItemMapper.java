package com.hazem.inventoryservice.dto;

import com.hazem.inventoryservice.model.Item;

public class ItemMapper {


    public static ItemDTO toItemDTO(Item item) {
        return ItemDTO.builder()
                .itemId(item.getId())
                .availableQuantity(item.getQuantity())
                .build();
    }

}
