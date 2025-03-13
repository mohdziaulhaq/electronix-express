package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.dtos.CreateOrderRequest;
import com.zia.electronix.express.dtos.OrderDto;
import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.entities.*;
import com.zia.electronix.express.exception.ResourceNotFoundException;
import com.zia.electronix.express.repositories.CartRepository;
import com.zia.electronix.express.repositories.OrderRepository;
import com.zia.electronix.express.repositories.UserRepository;
import com.zia.electronix.express.services.OrderService;
import com.zia.electronix.express.utilities.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
       User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
       Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found with given ID"));
       List<CartItem> cartItems = cart.getItems();
       if(cartItems.size() <=0){
           throw new ResourceNotFoundException("Cart Items Not Found");
       }

        Order order = Order.builder().billingName(request.getBillingName())
                .billingPhone(request.getBillingPhone())
                .billingAddress(request.getBillingAddress())
                .orderedDate(new Date())
                .deliveryDate(null)
                .paymentStatus(request.getPaymentStatus())
                .orderStatus(request.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
        AtomicReference<Double> orderAmount = new AtomicReference<>(0.0);

       List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

          OrderItem orderItem = OrderItem.builder()
                   .quantity(cartItem.getQuantity())
                   .product(cartItem.getProduct())
                   .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                   .order(order).build();
            orderAmount.set((orderAmount.get() + cartItem.getTotalPrice()));
           return orderItem;
       }).collect(Collectors.toList());
    //orderitems, amount

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
       return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Not Found"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        List<Order> orders = user.getOrderList();
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(OrderDto order, String orderId) {
       Order orderToBeUpdated =  orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Not Found"));
       orderToBeUpdated.setOrderStatus(order.getOrderStatus());
       orderToBeUpdated.setPaymentStatus(order.getPaymentStatus());
       orderToBeUpdated.setBillingAddress(order.getBillingAddress());
       orderToBeUpdated.setBillingPhone(order.getBillingPhone());
       orderToBeUpdated.setBillingName(order.getBillingName());

       Order savedOrder = orderRepository.save(orderToBeUpdated);

       return modelMapper.map(savedOrder, OrderDto.class);
    }
}
