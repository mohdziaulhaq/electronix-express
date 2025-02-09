package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.Category;
import org.springframework.data.domain.Page;
import com.zia.electronix.express.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    //search
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
}
