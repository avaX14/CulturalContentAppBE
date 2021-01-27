package com.kts.cultural_content.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.kts.cultural_content.model.CategoryType;
import com.kts.cultural_content.model.CulturalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository  extends JpaRepository<CulturalOffer, Long> {
    
    Page<CulturalOffer> findAll(Pageable page);
    List<CulturalOffer> findAllByType(CategoryType type);
    @Query("SELECT o from CulturalOffer o Where o.name LIKE %?1%"
            + " OR o.address.city LIKE %?1% OR o.address.country LIKE %?1% OR o.address.streetName LIKE %?1%")
    List<CulturalOffer> search(String searchParam);

    
    
}
