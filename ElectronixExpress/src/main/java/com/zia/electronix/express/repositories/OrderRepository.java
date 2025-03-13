package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.Order;
import com.zia.electronix.express.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}
