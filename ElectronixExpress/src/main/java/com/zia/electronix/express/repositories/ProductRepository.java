package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    //search
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByIsLiveTrue();
}
