package com.zia.electronix.express.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    //PENDING, DISPATCHED, DELIVERED
    private String orderStatus;

    //NOT PAID, PAID
    private String paymentStatus;
    private String billingName;
    private String billingPhone;
    private String billingAddress;

    private double orderAmount;
    private Date orderedDate;

    private Date expectedDeliveryDate;

    private Date deliveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}
