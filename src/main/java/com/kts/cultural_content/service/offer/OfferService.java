package  com.kts.cultural_content.service.offer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import  com.kts.cultural_content.dto.*;
import  com.kts.cultural_content.model.CulturalOffer;

import java.util.List;

public interface OfferService {
    CulturalOffer addOffer(CulturalOfferDTO offer);
    Page<CulturalOffer> getAllCulturalOffer(Pageable pageable);
    CulturalOffer editCulturalOffer(CulturalOfferDTO offer);
    CulturalOffer getCulturalOfferById(Long id);
    List<CulturalOfferDTO> findAllByCategoryType(Long id);
    List<CulturalOfferDTO> searchOffers(String searchParam);
    void deleteOffer(Long id) throws Exception;
}
