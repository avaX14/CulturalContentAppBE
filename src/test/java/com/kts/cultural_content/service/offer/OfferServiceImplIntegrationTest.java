package com.kts.cultural_content.service.offer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.kts.cultural_content.constants.OffersConstants;
import com.kts.cultural_content.dto.AddressDTO;
import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.exception.ApiRequestException;
import com.kts.cultural_content.model.CulturalOffer;
import com.kts.cultural_content.repository.OfferRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferServiceImplIntegrationTest {

    @Autowired
    private OfferServiceImpl offerService;

    @Autowired
    private OfferRepository offerRepository;

  
    @Test(expected = ApiRequestException.class)
    public void whenAddOfferWithInvalidType() {
        CulturalOfferDTO offerDTO = new CulturalOfferDTO();
        offerDTO.setTypeId(12L);
        offerService.addOffer(offerDTO);
    }

    @Test
    @Transactional @Rollback(true)
    public void whenAddOfferCreateOffer() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Novi Sad");
        addressDTO.setCountry("Srbija");
        addressDTO.setLatitude(40.23213123);
        addressDTO.setLongitude(19.321421);
        addressDTO.setStreetName("Danila Kisa");
        addressDTO.setStreetNumber("11");

        CulturalOfferDTO offerDto = new CulturalOfferDTO();
        offerDto.setName("New offer");
        offerDto.setTypeId(1L);
        offerDto.setDescription("New offer description");
        offerDto.setAddress(addressDTO);

        CulturalOffer offer = offerService.addOffer(offerDto);

        assertEquals("New offer", offer.getName());
        assertEquals("New offer description", offer.getDescription());
        assertEquals(OffersConstants.DB_1_ID, offer.getType().getId());
        assertEquals("Novi Sad", offer.getAddress().getCity());
        assertEquals("Srbija", offer.getAddress().getCountry());
        assertEquals(40.23213123, offer.getAddress().getLatitude(),0);
        assertEquals(19.321421, offer.getAddress().getLongitude(),0);
        assertEquals("Danila Kisa", offer.getAddress().getStreetName());
        assertEquals("11", offer.getAddress().getStreetNumber());
    }

    // @Test(expected = EventNotFoundException.class)
    // public void whenEditEventThrowEventNotFound() {
    //     EventEditDTO eventDto = new EventEditDTO();
    //     eventDto.setId(EventConstants.NON_EXISTING_DB_ID);
    //     eventService.editEvent(eventDto);
    // }

    // @Test
    // @Transactional @Rollback(true)
    // public void whenEditEvent() {
    //     final String newDescription = "This is new description for event";
    //     final int newPurchaseLimit = 14;
    //     final int newTicketsPerUser = 14;

    //     EventEditDTO eventEditDto = new EventEditDTO();
    //     eventEditDto.setId(EventConstants.DB_1_ID);
    //     eventEditDto.setDescription(newDescription);
    //     eventEditDto.setTicketsPerUser(newTicketsPerUser);
    //     eventEditDto.setPurchaseLimit(newPurchaseLimit);

    //     Event editedEvent = eventService.editEvent(eventEditDto);

    //     assertEquals(newDescription, editedEvent.getDescription());
    //     assertEquals(newTicketsPerUser, editedEvent.getTicketsPerUser());
    //     assertEquals(newPurchaseLimit, editedEvent.getPurchaseLimit());
    // }

    @Test
    public void getAllOffers() {
        Page<CulturalOffer> offers = offerService.getAllCulturalOffer(PageRequest.of(0, 5));

        assertEquals(2, offers.getContent().size());
        assertEquals(OffersConstants.DB_1_ID, offers.getContent().get(0).getId());
        assertEquals(OffersConstants.DB_2_ID, offers.getContent().get(1).getId());
    }

   
    @Test
    public void whenSearchOffersReturnNone() {
        List<CulturalOfferDTO> offers = offerService.searchOffers("Beograd");
        assertEquals(0, offers.size());
    }

  
    @Test
    public void whenSearchOffersReturnSpecificOffer() {
        List<CulturalOfferDTO> offers = offerService.searchOffers("Muzej Nikole Tesle");
        assertEquals(1, offers.size());
        CulturalOfferDTO offer = offers.get(0);

        assertEquals(Long.valueOf(2L), offer.getId());
        assertEquals("Novi Sad", offer.getAddress().getCity());
        assertEquals("Srbija", offer.getAddress().getCountry());
        assertEquals(OffersConstants.DB_1_ID, offer.getTypeId());
    }

    @Test
    public void whenSearchOffersJustCityReturnAll() {
        List<CulturalOfferDTO> offers = offerService.searchOffers("Novi Sad");
        assertEquals(2, offers.size());
    }
}
