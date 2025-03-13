package com.zia.electronix.express.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {

    private String orderId;

    //PENDING, DISPATCHED, DELIVERED
    private String orderStatus = "PENDING";

    //NOT PAID, PAID
    private String paymentStatus = "NOT_PAID";

    private String billingName;

    private String billingPhone;

    private String billingAddress;

    private double orderAmount;

    private Date orderedDate;

    private Date expectedDeliveryDate;

    private Date deliveryDate;

//    private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
