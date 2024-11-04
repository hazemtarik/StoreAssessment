package com.hazem.inventoryservice.service;

import com.hazem.inventoryservice.model.Item;

import java.util.List;

public interface StockService {


    List<Item> getAvailableStock(List<String> Ids);

    void decreaseStock(List<String> Ids, int amount);

}
