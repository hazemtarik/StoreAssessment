package com.hazem.orderservice.service.inventory;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazem.orderservice.config.app.HttpUtils;
import com.hazem.orderservice.config.exception.ServerErrorException;
import com.hazem.orderservice.config.shared.ApiResponse;
import com.hazem.orderservice.dto.inventory.ItemStock;
import com.hazem.orderservice.dto.order.OrderItem;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImp implements InventoryService {


    private final RestTemplate restTemplate;

    @Value("${app.inventory-service.url}")
    private String inventoryServiceUrl;


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    @CircuitBreaker(name = "itemStockService", fallbackMethod = "getItemStockFallback")
    public List<ItemStock> getItemStock(List<String> itemIdList) {
        ApiResponse body = HttpUtils.execute(itemIdList, restTemplate, inventoryServiceUrl, HttpMethod.POST);

        return OBJECT_MAPPER.convertValue(
                body.data(),
                new TypeReference<List<ItemStock>>() {
                }
        );
    }

    @Override
    @CircuitBreaker(name = "inventoryPlaceOrderService", fallbackMethod = "updateStockToPlaceOrderFallback")
    public String updateStockToPlaceOrder(Set<OrderItem> orderItemList) {
        ApiResponse body = HttpUtils.execute(orderItemList, restTemplate, inventoryServiceUrl + "/update", HttpMethod.PUT);
        return body.data().toString();
    }


    public List<ItemStock> getItemStockFallback(Exception e) {
        log.warn("Fallback due to: {}", e.getMessage());
        throw new ServerErrorException("The service is currently unavailable");
    }

    public String updateStockToPlaceOrderFallback(Exception e) {
        log.warn("Fallback due to: {}", e.getMessage());
        throw new ServerErrorException("The service is currently unavailable");
    }


}
