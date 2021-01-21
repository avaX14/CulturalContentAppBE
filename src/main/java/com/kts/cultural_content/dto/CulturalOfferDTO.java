package com.kts.cultural_content.dto;

import javax.validation.constraints.NotNull;

import com.kts.cultural_content.model.CulturalOffer;

public class CulturalOfferDTO {
    private Long id;

    @NotNull(message = "Name of offer must be provided")
    private String name;

    @NotNull(message = "Address of offer must be provided")
    private AddressDTO address;

    private String description;

    private String imagePath;


    public CulturalOfferDTO(){

    }

    public CulturalOfferDTO(CulturalOffer offer){
        this.id = offer.getId();
        this.name = offer.getName();
        this.address = new AddressDTO(offer.getAddress());
        this.description = offer.getDescription();
        this.imagePath = offer.getImagePath();
    }


    public CulturalOfferDTO(Long id, String name, AddressDTO address, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return this.address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CulturalOfferDTO id(Long id) {
        setId(id);
        return this;
    }

    public CulturalOfferDTO name(String name) {
        setName(name);
        return this;
    }

    public CulturalOfferDTO address(AddressDTO address) {
        setAddress(address);
        return this;
    }


    public CulturalOfferDTO description(String description) {
        setDescription(description);
        return this;
    }

    public CulturalOfferDTO imagePath(String imagePath) {
        setImagePath(imagePath);
        return this;
    }

}
