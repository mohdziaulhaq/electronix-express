package com.zia.electronix.express.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private double totalPrice;

    private ProductDto product;
}
