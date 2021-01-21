package com.kts.cultural_content.helper;

import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.model.CulturalOffer;

public class CulturalOfferMapper {
    
    private CulturalOfferMapper(){

    }

    public static CulturalOffer toEntity(CulturalOfferDTO offer){
        CulturalOffer cultural_offer = new CulturalOffer();
        cultural_offer.setName(offer.getName());
        cultural_offer.setDescription(offer.getDescription());
        return cultural_offer;
    }
}
