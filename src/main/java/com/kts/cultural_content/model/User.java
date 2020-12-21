package com.kts.cultural_content.model;

import javax.persistence.*;

@Entity
//Specifikacija naziva tabele u bazi
@Table(name = "user_table")
public class User {

    //@Id - oznaka da je atribut surogatni kljuc
    //@GeneratedValue - podesavanje strategije generisanja kljuceva
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column - atribut klase se mapira na kolonu tabele
    //nullable i unique su ogranicenja na moguce vrednosti kolone
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //Svaki entity mora imati konstruktor bez parametara
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
