package com.hazem.orderservice.controller;


import com.hazem.orderservice.config.shared.ApiResponse;
import com.hazem.orderservice.config.shared.Response;
import com.hazem.orderservice.dto.order.OrderItem;
import com.hazem.orderservice.dto.order.OrderRequest;
import com.hazem.orderservice.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @PostMapping("create")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return Response.createSuccessResponse(HttpStatus.CREATED, orderService.placeOrder(orderRequest));
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable String id) {
        return Response.createSuccessResponse(HttpStatus.OK, orderService.getOrder(id));
    }

}
