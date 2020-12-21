package com.kts.cultural_content.repository;

import com.kts.cultural_content.model.CulturalContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalContentCategoryRepository extends JpaRepository<CulturalContentCategory, Long> {

    CulturalContentCategory findByName(String name);

//    @Query(value = "select * from category c where c.name = ?1", nativeQuery = true)
//    CulturalContentCategory findByName(String name);

    CulturalContentCategory findByNameAndIdNot(String name, Long id);
}
