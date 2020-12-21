package com.kts.cultural_content.repository;

import com.kts.cultural_content.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {

    CategoryType findByName(String name);

    CategoryType findByNameAndIdNot(String name, Long id);

    List<CategoryType> findByCategoryId(Long categoryId);

    CategoryType findByCategoryIdAndId(Long categoryId, Long id);
}
