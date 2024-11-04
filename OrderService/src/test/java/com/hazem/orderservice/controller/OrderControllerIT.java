package com.hazem.orderservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.hazem.orderservice.config.shared.ApiResponse;
import com.hazem.orderservice.dto.order.OrderDTO;
import com.hazem.orderservice.dto.order.OrderItem;
import com.hazem.orderservice.dto.order.OrderRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIT {


    @Autowired
    private MockMvc mockMvc;

    private static OrderDTO orderDTO;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    @Order(1)
    public void placeOrderTesting() throws Exception {
        String json = new Gson().toJson(OrderRequest.builder()
                .orderItems(
                        Set.of(
                                OrderItem.builder()
                                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                                        .requestQuantity(10)
                                        .build()
                        )
                )
                .build());
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderItemsList.size()").value(1))
                .andReturn();
        String dataJson = mvcResult.getResponse().getContentAsString();
        ApiResponse apiResponse = OBJECT_MAPPER.readValue(dataJson, ApiResponse.class);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        orderDTO = OBJECT_MAPPER.convertValue(apiResponse.data(), new TypeReference<OrderDTO>() {
        });
    }

    @Test
    public void placeOrderTestingShouldGetNotFoundStatus() throws Exception {
        String json = new Gson().toJson(OrderRequest.builder()
                .orderItems(
                        Set.of(
                                OrderItem.builder()
                                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af1")
                                        .requestQuantity(10000)
                                        .build()
                        )
                )
                .build());
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void placeOrderTestingShouldGetBadRequestStatus() throws Exception {
        String json = new Gson().toJson(OrderRequest.builder()
                .orderItems(
                        Set.of(
                                OrderItem.builder()
                                        .itemId("3655191e-40db-42d9-9846-c28a9ed420af")
                                        .requestQuantity(10000)
                                        .build()
                        )
                )
                .build());
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @Order(2)
    public void getOrderDetails() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/order/" + orderDTO.OrderId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
