package com.zia.electronix.express.controller;

import com.zia.electronix.express.dtos.ApiResponseMessage;
import com.zia.electronix.express.dtos.CreateOrderRequest;
import com.zia.electronix.express.dtos.OrderDto;
import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> deleteOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Order deleted")
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //get orders of the user
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    //update cart
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto order, @PathVariable String orderId) {
        OrderDto updatedOrder = orderService.updateOrder(order, orderId);
        return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
    }
}
