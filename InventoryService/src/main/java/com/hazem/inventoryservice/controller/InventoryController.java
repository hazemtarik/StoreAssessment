package com.hazem.inventoryservice.controller;


import com.hazem.inventoryservice.config.shared.ApiResponse;
import com.hazem.inventoryservice.config.shared.Response;
import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemRequestDTO;
import com.hazem.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;


    @PostMapping
    public ResponseEntity<ApiResponse> getItemsByIds(@RequestBody Set<String> ids) {
        List<ItemDTO> items = inventoryService.getItemsByIds(ids);
        return Response.createSuccessResponse(HttpStatus.OK, items);
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateStockToPlaceOrder(@RequestBody List<ItemRequestDTO> itemRequestDTOList) {
        inventoryService.updateStockToPlaceOrder(itemRequestDTOList);
        return Response.createSuccessResponse(HttpStatus.OK, "order updated successfully");
    }

}
