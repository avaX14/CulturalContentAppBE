package com.kts.cultural_content.service.offer;

import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.helper.CulturalOfferMapper;
import com.kts.cultural_content.model.Address;
import com.kts.cultural_content.model.CulturalOffer;
import com.kts.cultural_content.repository.AddressRepository;
import com.kts.cultural_content.repository.OfferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public CulturalOffer addOffer(CulturalOfferDTO offer) {

        CulturalOffer cultural_offer = CulturalOfferMapper.toEntity(offer);
        Address address = new Address(offer.getAddress());
        addressRepository.save(address);
       
        cultural_offer.setAddress(address);
        offerRepository.save(cultural_offer);

        return cultural_offer;
    }

	@Override
	public Page<CulturalOffer> getAllCulturalOffer(Pageable pageable) {
		return offerRepository.findAll(pageable);
	}

	@Override
	public CulturalOffer editCulturalOffer(CulturalOfferDTO event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CulturalOffer getCulturalOfferById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
