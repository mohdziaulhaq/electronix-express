package com.zia.electronix.express.repositories;

import com.zia.electronix.express.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
