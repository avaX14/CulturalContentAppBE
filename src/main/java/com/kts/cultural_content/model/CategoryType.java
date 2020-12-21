package com.kts.cultural_content.model;

import javax.persistence.*;

@Entity
public class CategoryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    /* Fetch type odredjuje da li ce se ucitati i sve veze sa odgovarajucim objektom cim se sam objekat inicijalno ucita
       FetchType.EAGER -> ucitace se i sve veze sa objektom odmah
       FetchType.LAZY -> ucitace se tek pri eksplicitnom trazenju povezanih objekata
    */
    /* CascadeType.REFRESH -> oznacava da ce se prilikom osvezavanja tipa kategorije osveziti i sama kategorija.
       Osvezavanje podrazumeva ucitavanje poslednje sacuvanog stanja u bazi, cime se ponistavaju naknadne nesacuvane promene.

       Refresh operations re-read the value of a given instance from the database.
       In some cases, we may change an instance after persisting in the database, but later we need to undo those changes.
       In that kind of scenario, this may be useful.
       When we use this operation with CascadeType REFRESH,
       the child entity also gets reloaded from the database whenever the parent entity is refreshed.

    */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)

    private CulturalContentCategory category;

    public CategoryType() {
    }

    public CategoryType(String name) {
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

    public CulturalContentCategory getCategory() {
        return category;
    }

    public void setCategory(CulturalContentCategory category) {
        this.category = category;
    }
}
