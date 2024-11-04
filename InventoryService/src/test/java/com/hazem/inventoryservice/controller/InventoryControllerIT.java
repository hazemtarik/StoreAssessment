package com.hazem.inventoryservice.controller;


import com.google.gson.Gson;
import com.hazem.inventoryservice.dto.ItemRequestDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class InventoryControllerIT {

    @Autowired
    private MockMvc mockMvc;



    @Test
    @Order(1)
    public void givenSetOfItemIdsShouldReturnExistsItems() throws Exception {
        String json = new Gson().toJson(List.of("3655191e-40db-42d9-9846-c28a9ed420af"));
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].availableQuantity").value(99));
    }

    @Test
    @Order(2)
    public void givenSetOfItemRequestShouldOUpdateStock() throws Exception {
        String ItemRequestJsom = new Gson().toJson(List.of(
                ItemRequestDTO.builder()
                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                        .requestQuantity(5)
                        .build()
        ));
        mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON).content(ItemRequestJsom))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        String json = new Gson().toJson(List.of("3655191e-40db-42d9-9846-c28a9ed420af"));
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].availableQuantity").value(94));

    }

}
