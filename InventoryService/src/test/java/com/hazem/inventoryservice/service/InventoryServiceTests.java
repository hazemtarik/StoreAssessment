package com.hazem.inventoryservice.service;


import com.hazem.inventoryservice.config.exception.InsufficientStockException;
import com.hazem.inventoryservice.config.exception.ItemNotFoundException;
import com.hazem.inventoryservice.dto.ItemDTO;
import com.hazem.inventoryservice.dto.ItemRequestDTO;
import com.hazem.inventoryservice.model.Item;
import com.hazem.inventoryservice.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryServiceTests {


    @InjectMocks
    private InventoryServiceImp inventoryService;

    @Mock
    private ItemRepository itemRepository;

    private List<Item> itemList;
    private List<ItemRequestDTO> itemRequestDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        itemList = new ArrayList<>(List.of(
                Item.builder()
                        .id("1001")
                        .name("Macbook")
                        .quantity(100)
                        .build(),
                Item.builder()
                        .id("1002")
                        .name("Laptop")
                        .quantity(20)
                        .build()
        ));

    }


    @Test
    public void givenExistItemIdListShouldGetItemDtoList() {
        Mockito.when(itemRepository.findAllById(Mockito.anySet())).thenReturn(itemList);
        List<ItemDTO> itemDTOList = inventoryService.getItemsByIds(Mockito.anySet());
        Assertions.assertEquals(itemDTOList.size(), itemList.size());
    }

    @Test
    public void givenNotExistItemIdListShouldGetNothing() {
        Mockito.when(itemRepository.findAllById(Mockito.anySet())).thenReturn(List.of());
        List<ItemDTO> itemDTOList = inventoryService.getItemsByIds(Mockito.anySet());
        Assertions.assertEquals(0, itemDTOList.size());
    }


    @Test
    public void givenNotExistItemIdThrowItemNotFoundException() {
        Mockito.when(itemRepository.findItemForOrder(Mockito.anyString())).thenThrow(ItemNotFoundException.class);
        Assertions.assertThrows(ItemNotFoundException.class, () -> itemRepository.findItemForOrder(Mockito.anyString()));

        inventoryService.getItemsByIds(Mockito.anySet());
        Assertions.assertThrows(ItemNotFoundException.class, () -> itemRepository.findItemForOrder(Mockito.anyString()));
    }

    @Test
    public void givenExceededQuantityThrowInsufficientStockException() {
        itemRequestDTOList = new ArrayList<>(List.of(
                ItemRequestDTO.builder()
                        .itemId("1001")
                        .requestQuantity(1000)
                        .build()
        ));
        Mockito.when(itemRepository.findItemForOrder("1001")).thenReturn(Optional.ofNullable(itemList.getFirst()));
        Assertions.assertThrows(InsufficientStockException.class, () -> inventoryService.updateStockToPlaceOrder(itemRequestDTOList));
    }


}
