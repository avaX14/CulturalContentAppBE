package com.kts.cultural_content.controller;

import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.helper.UserMapper;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@RestController -> anotacija kojom se oznacava da je ovaj Spring kontejner zaduzen za prihvatanje klijentskih zahteva i slanje odgovora
@RestController
//@RequestMapping anotacija se može navesti i na nivou klase – tada sve metode unutar kontrolera imaju u svom URL-u prefiks koji je definisan u toj anotaciji
@RequestMapping(value =  "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    private UserMapper userMapper;

    // Metode klase kontrolera su anotirane sa @RequestMapping anotacijom koja opisuje zahtev koji treba biti obrađen u toj metodi (URL i tip HTTP metode)
    /* ResponseEntity objekat može da sadrži:
            telo (podatke) – metode anotirane sa @RequestBody anotacijom sadrže samo telo
            zaglavlje (metapodatke)
            HTTP status kod
    */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();

        return new ResponseEntity<>(toUserDTOList(users), HttpStatus.OK);
    }

    //Parametar je u kontroler moguce poslati kao parametar koji je promenljiva u URL-u zahteva - Path Variable
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.findOne(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        User user;
        try {
             user = userService.create(userMapper.toEntity(userDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        User user;
        try {
            user = userService.update(userMapper.toEntity(userDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        try {
            userService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public UserController() {
        userMapper = new UserMapper();
    }

    private List<UserDTO> toUserDTOList(List<User> users){
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user: users) {
            userDTOS.add(userMapper.toDto(user));
        }
        return userDTOS;
    }
}
