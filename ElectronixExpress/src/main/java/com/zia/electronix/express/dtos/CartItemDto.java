package com.zia.electronix.express.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrice;

}
