package com.kts.cultural_content.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import com.kts.cultural_content.constants.UserConstants;
import com.kts.cultural_content.dto.UserDTO;
import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.exception.ErrorMessage;
import com.kts.cultural_content.exception.ResourceAlreadyExistsException;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.repository.ConfirmationTokenRepository;
import com.kts.cultural_content.repository.UserRepository;
import com.kts.cultural_content.security.auth.JwtAuthenticationRequest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    private String accessToken;

    @Before
    public void login() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest(
                UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD
        );

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO user = response.getBody();
        accessToken = user.getToken().getAccessToken();
    }

    private HttpEntity<Object> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Object> createHttpEntityEditUser(UserEditDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.accessToken);
        return new HttpEntity<>(dto,headers);
    }

    private HttpEntity<Object> createUserDtoRequest(UserRegistrationDTO dto){
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(dto, headers);
    }

    @Test
    public void addNewUserUsernameTaken(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setUsername(UserConstants.DB_USERNAME);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/register",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username '" + dto.getUsername() + "' already exists.", response.getBody().getMessage() );
    }
    //passwords not same
    @Test
    public void addNewUserPasswordNotSame(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setRepeatPassword("repPassword2");

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/register",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Provided passwords must be the same.", response.getBody().getMessage());
    }

    //email taken
    @Test
    public void addNewUserEmailTaken(){
            UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setEmail(UserConstants.DB_EMAIL);

        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
                "/api/users/public/register",HttpMethod.POST,createUserDtoRequest(dto),ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email '" + dto.getEmail() + "' is taken.", response.getBody().getMessage() );
    }

    //success
    @Test
    public void addNewUserSuccess(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "/api/users/public/register",HttpMethod.POST,createUserDtoRequest(dto),UserDTO.class);

        UserDTO result = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(dto.getUsername(), result.getUsername());
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getEmail(), result.getEmail());

        tokenRepository.deleteById(1L);
        userRepository.deleteById(result.getId());


    }



    //getProfileData
    @Test
    public void getProfileData(){
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.GET, createHttpEntity(), UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO dto = response.getBody();
        System.out.println(dto.toString());
        assertEquals(UserConstants.DB_USERNAME, dto.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME, dto.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, dto.getLastName());
        assertEquals(UserConstants.DB_EMAIL, dto.getEmail());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, dto.isActivated());

    }


    //edit profile
    //mail in use
    @Test
    public void editUserEmailAlreadyTaken(){
        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName("NewName");
        dto.setLastName("NewSurname");
        dto.setEmail("markoMarkovic@maildrop.cc");

        ResponseEntity<ResourceAlreadyExistsException> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.PUT, createHttpEntityEditUser(dto), ResourceAlreadyExistsException.class);

        System.out.println("MY EXSEPSN " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResourceAlreadyExistsException.class, response.getBody());

    }

    //success
    @Test
    public void editUserSuccess(){
        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName("NewName");
        dto.setLastName("NewSurname");
        dto.setEmail("newEmail@maildrop.com");

        ResponseEntity<UserEditDTO> response = restTemplate.exchange(
                "/api/users/my-profile", HttpMethod.PUT, createHttpEntityEditUser(dto), UserEditDTO.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserEditDTO result = response.getBody();

        User u = userRepository.findByUsername(UserConstants.DB_USERNAME);
        assertEquals(u.getFirstName(), result.getFirstName());
        assertEquals(u.getLastName(), result.getLastName());
        assertEquals(u.getEmail(), result.getEmail());

        dto.setFirstName(UserConstants.DB_FIRST_NAME);
        dto.setLastName(UserConstants.DB_LAST_NAME);
        dto.setEmail(UserConstants.DB_EMAIL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
