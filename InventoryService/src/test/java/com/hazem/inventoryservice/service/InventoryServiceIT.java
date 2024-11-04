package com.hazem.inventoryservice.service;


import com.hazem.inventoryservice.config.exception.ItemNotFoundException;
import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemRequestDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Order(1)
public class InventoryServiceIT {


    @Autowired
    private InventoryServiceImp underTest;


    @Test
    @Order(1)
    public void givenSetOfItemIdsShouldReturnExistsItems() {
        List<ItemDTO> items = underTest.getItemsByIds(Set.of("3655191e-40db-42d9-9846-c28a9ed420af"));
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals(items.getFirst().availableQuantity(), 100);
    }

    @Test
    public void givenSetOfItemIdsShouldThrowItemNotFoundException() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> underTest.updateStockToPlaceOrder(List.of(
                ItemRequestDTO.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed4205f")
                        .requestQuantity(5)
                        .build()
        )));
    }


    @Test
    public void givenSetOfItemIdsShouldThrowInsufficientStockException() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> underTest.updateStockToPlaceOrder(List.of(
                ItemRequestDTO.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed4205f")
                        .requestQuantity(555)
                        .build()
        )));
    }

    @Test
    @Order(10)
    public void givenSetOfItemIdsShouldUpdateStockToPlaceOrder() {
        underTest.updateStockToPlaceOrder(List.of(
                ItemRequestDTO.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                        .requestQuantity(1)
                        .build()
        ));
        List<ItemDTO> items = underTest.getItemsByIds(Set.of("3655191e-40db-42d9-9846-c28a9ed420af"));
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals(99, items.getFirst().availableQuantity());
    }

}
