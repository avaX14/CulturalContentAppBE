package com.kts.cultural_content.model;

import javax.persistence.*;

@Entity
@Table(name="offers")
public class CulturalOffer {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="rating")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private CategoryType type;


    public CulturalOffer() {
    }

    public CulturalOffer(Long id, String name, String description, Address address, String longitude, String latitude, String imagePath, CategoryType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.imagePath = imagePath;
        this.type = type;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CategoryType getType() {
        return this.type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public CulturalOffer id(Long id) {
        setId(id);
        return this;
    }

    public CulturalOffer name(String name) {
        setName(name);
        return this;
    }

    public CulturalOffer description(String description) {
        setDescription(description);
        return this;
    }

    public CulturalOffer address(Address address) {
        setAddress(address);
        return this;
    }


    public CulturalOffer imagePath(String imagePath) {
        setImagePath(imagePath);
        return this;
    }

    public CulturalOffer type(CategoryType type) {
        setType(type);
        return this;
    }

}
