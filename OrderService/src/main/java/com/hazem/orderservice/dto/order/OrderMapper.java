package com.hazem.orderservice.dto.order;

import com.hazem.orderservice.model.Item;
import com.hazem.orderservice.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderMapper {


    public static Order toOrder(OrderRequest orderRequest) {
        String id = UUID.randomUUID().toString();
        return Order.builder()
                .id(id)
                .items(itemList(id, orderRequest))
                .status("SUBMITTED")
                .orderDate(LocalDateTime.now())
                .build();
    }

    public static OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .OrderId(order.getId())
                .orderItemsList(orderItemList(order.getItems()))
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .build();
    }


    private static List<Item> itemList(String orderId, OrderRequest orderRequest) {
        return orderRequest.orderItems().stream()
                .map(orderItem -> Item.builder()
                        .itemId(orderItem.itemId())
                        .order(Order.builder()
                                .id(orderId)
                                .build())
                        .quantity(orderItem.requestQuantity())
                        .build()
                ).toList();
    }


    private static List<OrderItem> orderItemList(List<Item> itemList) {
        return itemList.stream()
                .map(item -> OrderItem.builder()
                        .itemId(item.getItemId())
                        .requestQuantity(item.getQuantity())
                        .build()
                ).toList();
    }

}
