package com.hazem.orderservice.service.order;

import com.hazem.orderservice.dto.order.OrderDTO;
import com.hazem.orderservice.dto.order.OrderRequest;

public interface OrderService {


    OrderDTO placeOrder(OrderRequest orderRequest);

    OrderDTO getOrder(String orderId);


}
