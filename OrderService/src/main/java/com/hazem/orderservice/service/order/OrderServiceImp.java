package com.hazem.orderservice.service.order;


import com.hazem.orderservice.config.exception.InsufficientStockException;
import com.hazem.orderservice.config.exception.ResourceNotFoundException;
import com.hazem.orderservice.dto.inventory.ItemStock;
import com.hazem.orderservice.dto.order.OrderDTO;
import com.hazem.orderservice.dto.order.OrderItem;
import com.hazem.orderservice.dto.order.OrderMapper;
import com.hazem.orderservice.dto.order.OrderRequest;
import com.hazem.orderservice.model.Order;
import com.hazem.orderservice.repository.OrderRepository;
import com.hazem.orderservice.service.inventory.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {


    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;


    @Override
    @Transactional
    public OrderDTO placeOrder(OrderRequest orderRequest) {
        List<String> requestItemIdList = getRequestItemIdList(orderRequest);
        List<ItemStock> itemStockList = inventoryService.getItemStock(requestItemIdList);
        OrderRequestValidation(orderRequest, itemStockList);
        inventoryService.updateStockToPlaceOrder(orderRequest.orderItems());
        Order order = OrderMapper.toOrder(orderRequest);
        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toOrderDTO(saveOrder);
    }

    @Override
    public OrderDTO getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("order no. %s not found", orderId))
                );
        return OrderMapper.toOrderDTO(order);
    }


    private List<String> getRequestItemIdList(OrderRequest orderRequest) {
        return orderRequest.orderItems().stream()
                .map(OrderItem::itemId)
                .toList();
    }

    private void OrderRequestValidation(OrderRequest orderRequest, List<ItemStock> itemStockList) {
        if (orderRequest.orderItems().size() != itemStockList.size()) {
            List<String> foundedItemIdList = itemStockList.stream()
                    .map(ItemStock::itemId)
                    .toList();
            List<String> requestItemIdList = getRequestItemIdList(orderRequest);
            String notFoundIds = requestItemIdList.stream()
                    .filter(itemId -> !foundedItemIdList.contains(itemId))
                    .collect(Collectors.joining(", "));
            throw new ResourceNotFoundException(String.format("item no. %s not found", notFoundIds));
        }

        orderStockValidation(orderRequest, itemStockList);
    }

    private void orderStockValidation(OrderRequest orderRequest, List<ItemStock> itemStockList) {
        Map<String, Integer> orderItemsMap = new HashMap<>();
        orderRequest.orderItems().forEach(orderItem -> {
            orderItemsMap.put(orderItem.itemId(), orderItem.requestQuantity());
        });


        String itemIdsNotEnoughStock = itemStockList.stream().filter(itemStock -> {
                    Integer requestQuantity = orderItemsMap.get(itemStock.itemId());
                    return requestQuantity > itemStock.availableQuantity();
                }).map(ItemStock::itemId)
                .collect(Collectors.joining(", "));

        if (!itemIdsNotEnoughStock.isEmpty()) {
            String itemIdHasNotEnoughStock = String.join(", ", itemIdsNotEnoughStock);
            throw new InsufficientStockException(String.format("items no. %s has not enough stock", itemIdHasNotEnoughStock));
        }
    }


}
