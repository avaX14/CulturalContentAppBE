package com.kts.cultural_content.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.kts.cultural_content.constants.UserConstants;
import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.exception.ApiRequestException;
import com.kts.cultural_content.exception.ResourceNotFoundException;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.repository.ConfirmationTokenRepository;
import com.kts.cultural_content.repository.UserRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplIntegrationTest {

    @Autowired UserServiceImpl userService;

    @Autowired UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired AuthenticationManager authManager;

    @Autowired ConfirmationTokenRepository tokenRepo;


    //find by id
    //id not in db
    @Test(expected = ResourceNotFoundException.class)
    public void userIdNotExists(){
        userService.findById(50L);
    }

    //id in db
    @Test
    public void userIdExists(){
        User user = userService.findById(UserConstants.DB_ID);
        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
        assertEquals(UserConstants.DB_EMAIL,user.getEmail());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, user.isActivatedAccount());

    }

    //getUserByUsername
    //usernameNotInDB
    @Test(expected = ResourceNotFoundException.class)
    public void UserUsernameNotExists(){
        userService.findByUsername("fakeUsername");
    }

    @Test
    public void UserUsernameExists(){
        User user = userService.findByUsername(UserConstants.DB_USERNAME);
        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
    }

    //findAll
    @Test
    public void getAllUsers(){
        List<User> users = userService.findAll();
        assertEquals(3, users.size());
        User user1 = users.get(0);
        User user2 = users.get(1);

        assertEquals(UserConstants.DB_USERNAME, user1.getUsername());
        assertEquals(UserConstants.DB_USER_USERNAME, user2.getUsername());

    }

    // add new user
    //username exception
    @Test(expected = ApiRequestException.class)
    public void addUserUsernameAlreadyExists(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setUsername(UserConstants.DB_USERNAME);
        userService.addUser(dto);
    }

    //password exception
    @Test(expected = ApiRequestException.class)
    public void addUserPasswordNotSame(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setRepeatPassword("notSame");
        userService.addUser(dto);
    }

    //email exception
    @Test(expected = ApiRequestException.class)
    public void addUserEmailAlreadyTaken(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setEmail(UserConstants.DB_EMAIL);
        userService.addUser(dto);
    }

    //success
    @Test
    @Transactional @Rollback(true)
    public void addUserSuccess(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        User user = userService.addUser(dto);
        List<User> users = userService.findAll();

        assertEquals(4, users.size());
        assertFalse(user.isActivatedAccount());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getEmail(), user.getEmail());

    }

    @Test
    public void getMyProfileData(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);


        User user = userService.getMyProfileData();

        assertEquals(UserConstants.DB_USERNAME,user.getUsername());
        assertEquals(UserConstants.DB_FIRST_NAME,user.getFirstName());
        assertEquals(UserConstants.DB_LAST_NAME, user.getLastName());
        assertEquals(UserConstants.DB_ID, user.getId());
        assertEquals(UserConstants.DB_EMAIL,user.getEmail());
        assertEquals(UserConstants.DB_ACTIVATED_ACCOUNT, user.isActivatedAccount());
    }
  
    //edit user
    //taken email
    @Test(expected = ApiRequestException.class)
    public void editUserEmailTaken(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName(UserConstants.DB_FIRST_NAME);
        dto.setLastName(UserConstants.DB_LAST_NAME);
        dto.setEmail("tamaraSimic@maildrop.cc");

        userService.editUser(dto);
    }

    //success
    @Test
    @Transactional @Rollback(true)
    public void editUserSuccess(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(UserConstants.DB_USERNAME, UserConstants.DB_PASSWORD);
        Authentication auth = authManager.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        UserEditDTO dto = new UserEditDTO();
        dto.setFirstName(UserConstants.DB_FIRST_NAME);
        dto.setLastName(UserConstants.DB_LAST_NAME);
        dto.setEmail("test@test.com");

        userService.editUser(dto);
    }

}
