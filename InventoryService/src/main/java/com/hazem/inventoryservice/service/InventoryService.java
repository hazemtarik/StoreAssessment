package com.hazem.inventoryservice.service;

import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemRequestDTO;

import java.util.List;
import java.util.Set;

public interface InventoryService {


    List<ItemDTO> getItemsByIds(Set<String> Ids);

    void updateStockToPlaceOrder(List<ItemRequestDTO> itemRequestList);

}
