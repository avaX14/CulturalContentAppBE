package com.kts.cultural_content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.helper.UserMapper;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/public/add-user")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserRegistrationDTO userInfo) {
        User user = userService.addUser(userInfo);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping("/public/verify-account/{token}")
    public ResponseEntity verifyUserAccount(@PathVariable String token) {
        userService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDTO> getMyProfileData() {
        User user = userService.getMyProfileData();
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @PutMapping("/my-profile")
    public ResponseEntity<UserDTO> editMyProfile(@Valid @RequestBody UserEditDTO userInfo) {
        User user = userService.editUser(userInfo);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }
}
