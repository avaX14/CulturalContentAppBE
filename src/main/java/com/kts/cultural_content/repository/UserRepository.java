package com.kts.cultural_content.repository;

import com.kts.cultural_content.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Primer repozitorijuma u kojem su navedene metode koje po odredjenoj konstrukciji naziva prave upit u bazu.
 * Metode za pretragu pocinju sa find*By* dok u nastavku sadrze nazive atributa iz modela, konkatenirane sa
 * And, Or, Between, LessThan, GreaterThan, Like, itd. uz dodavanje pomocnih uslova poput Containing, AllIgnoringCase, itd.
 */
/*
 * Pri startovanju Spring kontejnera trigerovace se Spring Data infrastruktura koja ce kreirati binove za repozitorijume.
 * Proci se kroz metode navedene u svakom repozitorijumu i pokusati da konstruise upite koji ce se pozivati pri pozivu metoda.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
