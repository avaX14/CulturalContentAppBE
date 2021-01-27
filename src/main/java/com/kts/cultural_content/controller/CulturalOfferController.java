package com.kts.cultural_content.controller;

import java.util.List;

import javax.validation.Valid;

import com.kts.cultural_content.dto.CulturalOfferDTO;
import com.kts.cultural_content.model.CulturalOffer;
import com.kts.cultural_content.service.offer.OfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
public class CulturalOfferController {
    @Autowired  OfferService offerService;

    @CrossOrigin()
    @GetMapping("/public/all")
    public ResponseEntity<Page<CulturalOfferDTO>> getAllOffers(@PageableDefault(size = 10000) Pageable pageable){
        Page<CulturalOfferDTO> offers = offerService.getAllCulturalOffer(pageable).map(x -> new CulturalOfferDTO(x));
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<CulturalOfferDTO> addOffer(@Valid @RequestBody CulturalOfferDTO offer){
        CulturalOffer cultural_offer = offerService.addOffer(offer);
        
        return new ResponseEntity<>(new CulturalOfferDTO(cultural_offer), HttpStatus.OK);
    }

    @PutMapping ("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CulturalOfferDTO> editOffer(@RequestBody CulturalOfferDTO offer){
        return new ResponseEntity<>(new CulturalOfferDTO(offerService.editCulturalOffer(offer)), HttpStatus.OK);
    }

    @CrossOrigin()
    @GetMapping("/public/filterByCategoryType")
    public ResponseEntity<List<CulturalOfferDTO>> filterAllByCategoryType(Long typeId){
        List<CulturalOfferDTO> offers = offerService.findAllByCategoryType(typeId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @CrossOrigin()
    @GetMapping("/public/search")
    public ResponseEntity<List<CulturalOfferDTO>> searchOffers(String searchParam){
        List<CulturalOfferDTO> offers = offerService.searchOffers(searchParam);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<CulturalOfferDTO> getOfferById( @PathVariable("id") Long id){
        CulturalOffer e = offerService.getCulturalOfferById(id);
        return new ResponseEntity<>(new CulturalOfferDTO(e), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteOffer(@PathVariable Long id) {
        try {
            offerService.deleteOffer(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    
}
