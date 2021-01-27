package com.kts.cultural_content.service.offer;

import java.util.ArrayList;
import java.util.List;

import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.exception.ApiRequestException;
import com.kts.cultural_content.helper.CulturalOfferMapper;
import com.kts.cultural_content.model.Address;
import com.kts.cultural_content.model.CategoryType;
import com.kts.cultural_content.model.CulturalOffer;
import com.kts.cultural_content.repository.AddressRepository;
import com.kts.cultural_content.repository.CategoryTypeRepository;
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
	
	@Autowired
	private CategoryTypeRepository categoryTypeRepository;

    @Override
    public CulturalOffer addOffer(CulturalOfferDTO offer) {

		if (!categoryTypeRepository.findById(offer.getTypeId()).isPresent()) {
            throw new ApiRequestException("Categoty type not found");
		}
		
        CulturalOffer cultural_offer = CulturalOfferMapper.toEntity(offer);
        Address address = new Address(offer.getAddress());
		addressRepository.save(address);
        cultural_offer.setAddress(address);
	
		CategoryType categoryType = categoryTypeRepository.findById(offer.getTypeId()).orElse(null);
		cultural_offer.setType(categoryType);
		
        offerRepository.save(cultural_offer);

        return cultural_offer;
    }

	@Override
	public Page<CulturalOffer> getAllCulturalOffer(Pageable pageable) {
		return offerRepository.findAll(pageable);
	}

	@Override
	public List<CulturalOfferDTO> findAllByCategoryType( Long typeId) {
		CategoryType categoryType = categoryTypeRepository.findById(typeId).orElse(null);
		List<CulturalOffer> allOffers =  offerRepository.findAllByType(categoryType);
		List<CulturalOfferDTO> allOffersDTO = new ArrayList<>();
		allOffers.forEach(x -> allOffersDTO.add(new CulturalOfferDTO(x)));
		return allOffersDTO;
	}

	

	@Override
	public CulturalOffer editCulturalOffer(CulturalOfferDTO offer) {
		CulturalOffer o = offerRepository.findById(offer.getId()).orElseThrow(() -> new ApiRequestException("Invalid id of offer"));

		o.setName(offer.getName());
		o.setAddress(new Address(offer.getAddress()));
        o.setDescription(offer.getDescription());

        offerRepository.save(o);

        return o;
	}

	@Override
	public CulturalOffer getCulturalOfferById(Long id) {
		CulturalOffer offer = offerRepository.findById(id).orElse(null);
		return offer;
	}

	@Override
	public List<CulturalOfferDTO> searchOffers(String searchParam) {
		List<CulturalOffer> allOffers =  offerRepository.search(searchParam);
		List<CulturalOfferDTO> allOffersDTO = new ArrayList<>();
		allOffers.forEach(x -> allOffersDTO.add(new CulturalOfferDTO(x)));
		return allOffersDTO;
	}


	@Override
    public void deleteOffer(Long id) throws Exception {
        CulturalOffer existingCulturalOffer = offerRepository.findById(id).orElse(null);
        if(existingCulturalOffer == null){
            throw new Exception("Cultural offer with given id doesn't exist");
        }
        offerRepository.delete(existingCulturalOffer);
    }

    
}
