package com.zia.electronix.express.dtos;

import com.zia.electronix.express.entities.Cart;
import com.zia.electronix.express.entities.Product;
import jakarta.persistence.*;

public class CartItemDto {

    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrice;

}
