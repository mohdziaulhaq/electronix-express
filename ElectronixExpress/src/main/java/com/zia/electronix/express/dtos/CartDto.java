package com.zia.electronix.express.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private String cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();


}
