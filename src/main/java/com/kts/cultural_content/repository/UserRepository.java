package com.kts.cultural_content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kts.cultural_content.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
}
