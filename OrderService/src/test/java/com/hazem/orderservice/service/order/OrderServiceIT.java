package com.hazem.orderservice.service.order;


import com.hazem.orderservice.config.exception.InsufficientStockException;
import com.hazem.orderservice.config.exception.ResourceNotFoundException;
import com.hazem.orderservice.dto.order.OrderDTO;
import com.hazem.orderservice.dto.order.OrderItem;
import com.hazem.orderservice.dto.order.OrderRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class OrderServiceIT {


    @Autowired
    private OrderServiceImp orderService;


    private static OrderDTO orderDTO;

    private final String INVALID_ORDER_ID = "1001";

    @Test
    @Order(1)
    public void givenOrderRequestShouldPlaceOrder() {
        orderDTO = orderService.placeOrder(
                OrderRequest.builder()
                        .orderItems(
                                Set.of(
                                        OrderItem.builder()
                                                .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                                                .requestQuantity(10)
                                                .build()
                                )
                        )
                        .build()
        );
        Assertions.assertNotNull(orderDTO);
        Assertions.assertEquals(1, orderDTO.orderItemsList().size());
    }


    @Test
    @Order(2)
    public void givenOrderIdShouldGetOrderDetails() {
        OrderDTO order = orderService.getOrder(orderDTO.OrderId());
        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderDTO.OrderId(), order.OrderId());
    }

    @Test
    public void givenOrderIdShouldGetThrow() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(INVALID_ORDER_ID));
    }

    @Test
    public void givenOrderRequestWithUnavailableQuantityShouldThrow() {
        Assertions.assertThrows(InsufficientStockException.class, () -> orderService.placeOrder(
                OrderRequest.builder()
                        .orderItems(
                                Set.of(
                                        OrderItem.builder()
                                                .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                                                .requestQuantity(1000)
                                                .build()
                                )
                        )
                        .build()
        ));
    }


    @Test
    public void givenOrderRequestWithUnavailableItemIdShouldThrow() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(
                OrderRequest.builder()
                        .orderItems(
                                Set.of(
                                        OrderItem.builder()
                                                .itemId("3655191e-40db-42d9-9846-5555555")
                                                .requestQuantity(100)
                                                .build()
                                )
                        )
                        .build()
        ));
    }

}
