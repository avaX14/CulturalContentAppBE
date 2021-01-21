package com.kts.cultural_content.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.kts.cultural_content.model.CulturalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository  extends JpaRepository<CulturalOffer, Long> {
    
    Page<CulturalOffer> findAll(Pageable page);
}
