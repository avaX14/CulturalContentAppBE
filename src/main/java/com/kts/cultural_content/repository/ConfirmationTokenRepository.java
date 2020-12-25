package com.kts.cultural_content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kts.cultural_content.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByToken(String token);
}
