package com.zia.electronix.express.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productId;

    private String productName;

    @Column(length = 10000)
    private String description;

    private double price;

    private double discountedPrice;

    private long quantityAvailable;

    private Date dateAdded;

    private boolean live;

    private boolean inStock;

    private String productImageName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
