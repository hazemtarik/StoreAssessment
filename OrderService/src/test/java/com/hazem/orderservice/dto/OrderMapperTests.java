package com.hazem.orderservice.dto;

import com.hazem.orderservice.dto.order.OrderDTO;
import com.hazem.orderservice.dto.order.OrderItem;
import com.hazem.orderservice.dto.order.OrderMapper;
import com.hazem.orderservice.dto.order.OrderRequest;
import com.hazem.orderservice.model.Item;
import com.hazem.orderservice.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OrderMapperTests {


    @Test
    public void testToOrderShouldReturnOrder() {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderItems(Set.of(OrderItem.builder()
                        .itemId("1001")
                        .requestQuantity(10)
                        .build()))
                .build();
        Order order = OrderMapper.toOrder(orderRequest);
        Assertions.assertEquals(1, order.getItems().size());
    }


    @Test
    public void testToOrderDTOShouldReturnOrderDTO() {
        String id = UUID.randomUUID().toString();
        Order order = Order.builder()
                .id(id)
                .items(List.of(
                                Item.builder()
                                        .itemId("1001")
                                        .quantity(10)
                                        .build()
                        )
                )
                .build();
        OrderDTO orderDTO = OrderMapper.toOrderDTO(order);
        Assertions.assertEquals(orderDTO.orderItemsList().size(), order.getItems().size());
        Assertions.assertEquals(orderDTO.OrderId(), order.getId());
    }

}
