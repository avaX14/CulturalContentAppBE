package com.kts.cultural_content.repository;

import com.kts.cultural_content.constants.OffersConstants;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.kts.cultural_content.model.CategoryType;
import com.kts.cultural_content.model.CulturalOffer;
import org.springframework.data.domain.PageRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOffersRepositoryIntegrationTest {
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Test
    public void whenFindAllReturnOffers() {
        Page<CulturalOffer> offers = offerRepository.findAll(PageRequest.of(0, 5));
        assertEquals(2, offers.getTotalElements());
        assertEquals(2, offers.getContent().size());
        assertEquals(OffersConstants.DB_1_ID, offers.getContent().get(0).getId());
        assertEquals(OffersConstants.DB_2_ID, offers.getContent().get(1).getId());
    }

    @Test
    public void whenFindAllReturnEmptyPage() {
        Page<CulturalOffer> offers = offerRepository.findAll(PageRequest.of(1, 5));
        assertEquals(2, offers.getTotalElements());
        assertTrue(offers.getContent().isEmpty());
    }

    @Test
    public void whenSearchOffersJustCityName() {
        List<CulturalOffer> offers = offerRepository.search("Novi Sad");
        assertEquals(2, offers.size());
        assertEquals("Novi Sad", offers.get(0).getAddress().getCity());
    }

    @Test
    public void whenSearchOffersJustCategoryType() {
        CategoryType categoryType = categoryTypeRepository.findById(1L).orElse(null);
        List<CulturalOffer> offers = offerRepository.findAllByType(categoryType);
        assertEquals(1, offers.size());
        assertEquals("Muzej Nikole Tesle", offers.get(0).getName());
    }

    @Test
    public void whenSearchOffersJustOfferName() {
        List<CulturalOffer> offers = offerRepository.search("Nikole Tesle");
        assertEquals(1, offers.size());
        assertEquals("Muzej Nikole Tesle", offers.get(0).getName());
    }

    @Test
    public void whenSearchOffersReturnNone() {
        List<CulturalOffer> offers = offerRepository.search("Bioskop");
        assertEquals(0, offers.size());
    }

    @Test
    public void  whenSearchOffersReturnSpecificOffer() {
        List<CulturalOffer> offers = offerRepository.search("Muzej Nikole Tesle");
        assertEquals(1, offers.size());
        CulturalOffer offer = offers.get(0);

        assertEquals(Long.valueOf(2L), offer.getId());
        assertEquals("Novi Sad", offer.getAddress().getCity());
        assertEquals("Srbija", offer.getAddress().getCountry());
        assertEquals("Muzej", offer.getType().getName());
    }
}
