package com.hazem.inventoryservice;


import com.hazem.inventoryservice.model.Item;
import com.hazem.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {


    private final InventoryService inventoryService;


    @PostMapping
    public ResponseEntity<List<Item>> getItemsByIds(@RequestBody List<String> ids) {
        List<Item> items = inventoryService.getItemsByIds(ids);
        return ResponseEntity.ok().body(items);
    }

}
