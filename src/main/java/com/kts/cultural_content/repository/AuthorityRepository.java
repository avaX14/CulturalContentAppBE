package com.kts.cultural_content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kts.cultural_content.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
