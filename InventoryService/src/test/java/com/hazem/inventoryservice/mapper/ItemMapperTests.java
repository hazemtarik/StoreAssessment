package com.hazem.inventoryservice.mapper;

import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemMapper;
import com.hazem.inventoryservice.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemMapperTests {


    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id("1001")
                .name("macbook")
                .quantity(100)
                .build();
    }


    @Test
    public void givenItemShouldMappedToDTO() {
        ItemDTO itemDTO = ItemMapper.toItemDTO(item);
        Assertions.assertNotNull(itemDTO);
        Assertions.assertEquals(itemDTO.itemId(), item.getId());
        Assertions.assertEquals(itemDTO.availableQuantity(), item.getQuantity());
    }
}
