package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
