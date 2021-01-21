package  com.kts.cultural_content.service.offer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import  com.kts.cultural_content.dto.*;
import  com.kts.cultural_content.model.CulturalOffer;

import java.util.List;

public interface OfferService {
    CulturalOffer addOffer(CulturalOfferDTO event);
    Page<CulturalOffer> getAllCulturalOffer(Pageable pageable);
    CulturalOffer editCulturalOffer(CulturalOfferDTO event);
    CulturalOffer getCulturalOfferById(Long id);
}
