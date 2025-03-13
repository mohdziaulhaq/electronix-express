package com.zia.electronix.express.services;

import com.zia.electronix.express.dtos.CreateOrderRequest;
import com.zia.electronix.express.dtos.OrderDto;
import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.entities.Order;

import java.util.List;

public interface OrderService {
    //create order
    OrderDto createOrder(CreateOrderRequest request);
    //remove order
    void removeOrder(String orderId);
    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);
    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    OrderDto updateOrder(OrderDto order, String orderId);
}
