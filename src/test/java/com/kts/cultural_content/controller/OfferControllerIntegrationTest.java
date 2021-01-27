package com.kts.cultural_content.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import com.kts.cultural_content.constants.UserConstants;
import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.exception.ErrorMessage;
import com.kts.cultural_content.repository.OfferRepository;
import com.kts.cultural_content.security.auth.JwtAuthenticationRequest;
import com.kts.cultural_content.dto.AddressDTO;
import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.constants.OffersConstants;
import com.kts.cultural_content.utils.RestResponsePage;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OfferRepository offerRepository;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }


    @Test
    public void whenGetAllOffersReturnOffers() {
        final int page = 0;
        final int size = 5;

        ParameterizedTypeReference<RestResponsePage<CulturalOfferDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<CulturalOfferDTO>>() {};

        ResponseEntity<RestResponsePage<CulturalOfferDTO>> response = restTemplate.exchange(
                "/api/offers/public/all?page=" + page + "&size=" + size, HttpMethod.GET, null, responseType);

        List<CulturalOfferDTO> offers = response.getBody().getContent();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, offers.size());
        assertEquals(OffersConstants.DB_1_ID, offers.get(0).getId());
        assertEquals(OffersConstants.DB_2_ID, offers.get(1).getId());
    }

    @Test
    public void whenGetAllOffersReturnEmptyPage() {
        final int page = 1;
        final int size = 5;

        ParameterizedTypeReference<RestResponsePage<CulturalOfferDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<CulturalOfferDTO>>() {};

        ResponseEntity<RestResponsePage<CulturalOfferDTO>> response = restTemplate.exchange(
                "/api/offers/public/all?page=" + page + "&size=" + size, HttpMethod.GET, null, responseType);

        List<CulturalOfferDTO> offers = response.getBody().getContent();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(offers.isEmpty());
        assertEquals(2, response.getBody().getTotalElements());
    }

    private HttpEntity<CulturalOfferDTO> createOfferDtoRequest(CulturalOfferDTO offerDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(offerDTO, headers);
    }

    private CulturalOfferDTO createOfferDto() {
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

        return offerDto;

    }



  

  
    @Test
    public void whenAddOffer() {
        CulturalOfferDTO offerDTO = createOfferDto();

        ResponseEntity<CulturalOfferDTO> response = restTemplate.exchange(
                "/api/offers", HttpMethod.POST, createOfferDtoRequest(offerDTO), CulturalOfferDTO.class);

        CulturalOfferDTO createdOffer = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdOffer);
        assertNotNull(createdOffer.getId());
        assertEquals(offerDTO.getName(), createdOffer.getName());
        assertEquals(offerDTO.getDescription(), createdOffer.getDescription());
        assertEquals(offerDTO.getTypeId(), createdOffer.getTypeId());
        assertEquals(offerDTO.getAddress().getCity(), createdOffer.getAddress().getCity());
        assertEquals(offerDTO.getAddress().getCountry(), createdOffer.getAddress().getCountry());
        assertEquals(offerDTO.getAddress().getLatitude(), createdOffer.getAddress().getLatitude(),0);
        assertEquals(offerDTO.getAddress().getLongitude(), createdOffer.getAddress().getLongitude(),0);
        assertEquals(offerDTO.getAddress().getStreetName(), createdOffer.getAddress().getStreetName());
        assertEquals(offerDTO.getAddress().getStreetNumber(), createdOffer.getAddress().getStreetNumber());

        offerRepository.deleteById(createdOffer.getId());
    }

   

    @Test
    public void whenEditEventEventNotFound() {
        CulturalOfferDTO offerDto = new CulturalOfferDTO();
        offerDto.setId(15L);
        offerDto.setDescription("This is new description");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/offers/", HttpMethod.PUT, createOfferDtoRequest(offerDto), ErrorMessage.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid id of event", response.getBody().getMessage());
    }

    @Test
    public void whenSearchOffersReturnNone() {
        ParameterizedTypeReference<List<CulturalOfferDTO>> responseType = new ParameterizedTypeReference<List<CulturalOfferDTO>>() {};
        HttpEntity<String> httpEntity = new HttpEntity<>("Beograd");

        ResponseEntity<List<CulturalOfferDTO>> response = restTemplate.exchange(
                "/api/offers/public/search", HttpMethod.GET, httpEntity, responseType);

        List<CulturalOfferDTO> offers = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(offers.isEmpty());
    }

    @Test
    public void whenSearchOffersReturnSpecificOffer() {
        ParameterizedTypeReference<List<CulturalOfferDTO>> responseType = new ParameterizedTypeReference<List<CulturalOfferDTO>>() {};
        HttpEntity<String> httpEntity = new HttpEntity<>("Muzej Nikole Tesle");

        ResponseEntity<List<CulturalOfferDTO>> response = restTemplate.exchange(
                "/api/offers/public/search", HttpMethod.GET, httpEntity, responseType);
        List<CulturalOfferDTO> offers = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, offers.size());
        assertEquals(Long.valueOf(2L), offers.get(0).getId());
        assertEquals("Muzej Nikole Tesle", offers.get(0).getName());
    }

    @Test
    public void whenSearchOffersJustCityReturnAll() {
        ParameterizedTypeReference<List<CulturalOfferDTO>> responseType = new ParameterizedTypeReference<List<CulturalOfferDTO>>() {};
        HttpEntity<String> httpEntity = new HttpEntity<String>("Novi Sad");


        ResponseEntity<List<CulturalOfferDTO>> response = restTemplate.exchange(
                "/api/offers/public/search", HttpMethod.GET, httpEntity, responseType);
        System.out.println(response.toString());

        List<CulturalOfferDTO> offers = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, offers.size());
    }
}
