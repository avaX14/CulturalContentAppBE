package com.kts.cultural_content.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class CulturalContentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    //mappedBy -> ovom anotacijom se oznacava ko je vlasnik veze
    //CascadeType.ALL -> dozvoljava da se prilikom svakog cuvanja, izmene ili brisanja kategorije cuvaju, menjaju ili brisu i tipovi kategorije.
    // CascadeType.ALL propagates all operations — including Hibernate-specific ones — from a parent to a child entity.
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CategoryType> categoryTypes = new HashSet<>();

    public CulturalContentCategory() {
    }

    public CulturalContentCategory(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryType> getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(Set<CategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
