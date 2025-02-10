package com.zia.electronix.express.dtos;

import com.zia.electronix.express.entities.CartItem;
import com.zia.electronix.express.entities.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDto {

    private String cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();


}
