package com.hazem.orderservice.service.inventory;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazem.orderservice.config.app.HttpUtils;
import com.hazem.orderservice.config.shared.ApiResponse;
import com.hazem.orderservice.dto.inventory.ItemStock;
import com.hazem.orderservice.dto.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {


    private final RestTemplate restTemplate;

    @Value("${app.inventory-service.url}")
    private String inventoryServiceUrl;


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @Override
    public List<ItemStock> getItemStock(List<String> itemIdList) {
        ApiResponse body = HttpUtils.execute(itemIdList, restTemplate, inventoryServiceUrl, HttpMethod.POST);

        return OBJECT_MAPPER.convertValue(
                body.data(),
                new TypeReference<List<ItemStock>>() {
                }
        );
    }

    @Override
    public String updateStockToPlaceOrder(Set<OrderItem> orderItemList) {
        ApiResponse body = HttpUtils.execute(orderItemList, restTemplate, inventoryServiceUrl + "/update", HttpMethod.PUT);
        return body.data().toString();
    }


}
