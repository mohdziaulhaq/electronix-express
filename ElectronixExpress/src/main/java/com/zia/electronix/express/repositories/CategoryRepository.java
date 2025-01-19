package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByTitleContaining(String keywords);
}
