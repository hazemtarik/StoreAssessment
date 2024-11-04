package com.hazem.orderservice.service.inventory;

import com.hazem.orderservice.dto.inventory.ItemStock;
import com.hazem.orderservice.dto.order.OrderItem;

import java.util.List;
import java.util.Set;

public interface InventoryService {


    List<ItemStock> getItemStock(List<String> itemIdList);

    String updateStockToPlaceOrder(Set<OrderItem> orderItemList);
}
