package com.hazem.inventoryservice.service;


import com.hazem.inventoryservice.config.exception.InsufficientStockException;
import com.hazem.inventoryservice.config.exception.ItemNotFoundException;
import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemMapper;
import com.hazem.inventoryservice.dto.ItemRequestDTO;
import com.hazem.inventoryservice.model.Item;
import com.hazem.inventoryservice.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {


    private final ItemRepository itemRepository;


    @Override
    public List<ItemDTO> getItemsByIds(Set<String> Ids) {
        return itemRepository.findAllById(Ids).stream()
                .map(ItemMapper::toItemDTO)
                .toList();
    }

    @Override
    @Transactional
    public void updateStockToPlaceOrder(List<ItemRequestDTO> itemRequestList) {
        itemRequestList.forEach(
                itemRequest -> {
                    Item item = getItemById(itemRequest.itemId());
                    stockItemValidation(item, itemRequest.requestQuantity());
                    item.setQuantity(item.getQuantity() - itemRequest.requestQuantity());
                    itemRepository.save(item);
                }
        );
    }


    private Item getItemById(String id) {
        return itemRepository.findItemForOrder(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Item %s not found", id)));
    }


    private void stockItemValidation(Item item, int requestQuantity) {
        if (item.getQuantity() < requestQuantity)
            throw new InsufficientStockException(String.format("Not enough stock available for %s", item.getId()));
    }


}
