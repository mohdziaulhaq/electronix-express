package com.zia.electronix.express.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;

    private String productName;

    private String description;

    private double price;

    private double discountedPrice;

    private long quantityAvailable;

    private Date dateAdded;

    private boolean live;

    private boolean inStock;

    private String productImageName;

    private CategoryDto category;
}
