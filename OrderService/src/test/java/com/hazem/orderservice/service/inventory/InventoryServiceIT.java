package com.hazem.orderservice.service.inventory;


import com.hazem.orderservice.config.exception.BadRequestException;
import com.hazem.orderservice.config.exception.ResourceNotFoundException;
import com.hazem.orderservice.dto.inventory.ItemStock;
import com.hazem.orderservice.dto.order.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Order(1)
public class InventoryServiceIT {


    @Autowired
    private InventoryService inventoryService;



    @Test
    public void givenListOfItemsIdsShouldGetListOfItemStock() {
        List<ItemStock> itemStockList = inventoryService.getItemStock(
                List.of("3655191e-40db-42d9-9846-c28a9ed420af")
        );
        Assertions.assertNotNull(itemStockList);
        Assertions.assertEquals(1, itemStockList.size());
    }

    @Test
    public void givenInvalidListOfItemsIdsShouldGetNullItemStock() {
        List<ItemStock> itemStockList = inventoryService.getItemStock(
                List.of("3655191e-40db-42d9-9846-c28a9ed420af1")
        );
        Assertions.assertEquals(0, itemStockList.size());
    }


    @Test
    public void givenListOfOrderItemShouldGeStockLevelDecreased() {
        List<ItemStock> itemStockList = inventoryService.getItemStock(
                List.of("3655191e-40db-42d9-9846-c28a9ed420af")
        );
        Assertions.assertNotNull(itemStockList);
        Assertions.assertEquals(80, itemStockList.getFirst().availableQuantity());

        inventoryService.updateStockToPlaceOrder(
          Set.of(OrderItem.builder()
                          .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                          .requestQuantity(10)
                  .build())
        );

        List<ItemStock> itemStockListAfterPlaceOrder = inventoryService.getItemStock(
                List.of("3655191e-40db-42d9-9846-c28a9ed420af")
        );
        Assertions.assertNotNull(itemStockListAfterPlaceOrder);
        Assertions.assertEquals(70, itemStockListAfterPlaceOrder.getFirst().availableQuantity());
    }

    @Test
    public void givenListOfOrderItemWithUnavailableQuantityShouldThrow() {

        Assertions.assertThrows(BadRequestException.class, () -> inventoryService.updateStockToPlaceOrder(
                Set.of(OrderItem.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                        .requestQuantity(1000)
                        .build())
        ));
    }

    @Test
    public void givenListOfOrderItemWithUnavailableItemShouldThrow() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateStockToPlaceOrder(
                Set.of(OrderItem.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed420a5")
                        .requestQuantity(10)
                        .build())
        ));
    }

}
