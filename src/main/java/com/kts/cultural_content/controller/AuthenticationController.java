package com.kts.cultural_content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kts.cultural_content.dto.PasswordChangerDTO;
import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.model.UserTokenState;
import com.kts.cultural_content.security.auth.JwtAuthenticationRequest;
import com.kts.cultural_content.service.user.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        return new ResponseEntity<>(userDetailsService.login(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {
        return new ResponseEntity<>(userDetailsService.refreshAuthenticationToken(request), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@Valid @RequestBody PasswordChangerDTO passwordChanger) {
        userDetailsService.changePassword(passwordChanger.getOldPassword(), passwordChanger.getNewPassword());
        return ResponseEntity.ok().build();
    }

}
