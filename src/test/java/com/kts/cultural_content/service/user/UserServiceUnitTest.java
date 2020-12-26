package com.kts.cultural_content.service.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import com.kts.cultural_content.common.TimeProvider;
import com.kts.cultural_content.common.consts.UserRoles;
import com.kts.cultural_content.constants.SecurityContextConstants;
import com.kts.cultural_content.constants.UserConstants;
import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.exception.ApiRequestException;
import com.kts.cultural_content.exception.ResourceNotFoundException;
import com.kts.cultural_content.model.Authority;
import com.kts.cultural_content.model.ConfirmationToken;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.repository.AuthorityRepository;
import com.kts.cultural_content.repository.ConfirmationTokenRepository;
import com.kts.cultural_content.repository.UserRepository;
import com.kts.cultural_content.service.email.MailSenderService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthorityRepository authorityRepository;

    @MockBean
    private ConfirmationTokenRepository tokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TimeProvider timeProvider;

    @MockBean
    private MailSenderService mailSenderService;


    @Before
    public void setup() throws ParseException {
        SecurityContext securityContext = SecurityContextConstants.returnMockedSecurityContext();
        SecurityContextHolder.setContext(securityContext);
    }

    //find by id false id
    @Test(expected = ResourceNotFoundException.class)
    public void whenUserIdNotExists(){
        Mockito.when(userRepository.findById(UserConstants.MOCK_ID)).thenReturn(Optional.empty());
        userService.findById(UserConstants.MOCK_ID);
    }

    @Test
    public void whenUserIDExists(){
        User user = new User();
        Long userID = 55L;
        user.setId(userID);
        user.setUsername("petarp");
        user.setFirstName("Petar");
        user.setLastName("Petrovic");


        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        User resultUser = userService.findById(userID);
        assertEquals(userID,resultUser.getId());
        assertEquals("petarp",resultUser.getUsername());
        assertEquals("Petar",resultUser.getFirstName());
        assertEquals("Petrovic",resultUser.getLastName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUsernameNotExists(){
        // Mockito.when(userRepository.findByUsername(UserConstants.MOCK_USERNAME)).thenThrow(NoSuchElementException.class);
        Mockito.when(userRepository.findByUsername(UserConstants.MOCK_USERNAME)).thenReturn(null);
        userService.findByUsername(UserConstants.MOCK_USERNAME);
    }

    @Test
    public void whenUserWithUsernameExists(){
        User user = new User();
        String username = "petarp";
        Long userID = 55L;
        user.setId(userID);
        user.setUsername(username);
        user.setFirstName("Petar");
        user.setLastName("Petrovic");

        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        User result = userService.findByUsername(username);

        assertEquals(userID,result.getId());
        assertEquals("petarp",result.getUsername());
        assertEquals("Petar",result.getFirstName());
        assertEquals("Petrovic",result.getLastName());

    }

    //findAll
    @Test
    public void getAllUsers(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(15L);
        user1.setUsername("petarp");

        User user2 = new User();
        user2.setId(16L);
        user2.setUsername("markom");
        users.add(user1);
        users.add(user2);


        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.findAll();

        assertEquals(users.size(), result.size());
        assertEquals(user1.getId(),result.get(0).getId());
        assertEquals(user1.getUsername(),result.get(0).getUsername());


        assertEquals(user2.getId(),result.get(1).getId());
        assertEquals(user2.getUsername(),result.get(1).getUsername());

    }

    //addUser
    //username exception
    @Test(expected = ApiRequestException.class)
    public void addUserUsernameAlreadyExists(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        User user = new User();
        user.setUsername(dto.getUsername());
        Mockito.when(userRepository.findByUsername(dto.getUsername())).thenReturn(user);
        userService.addUser(dto);

    }

    //password exception
    @Test(expected = ApiRequestException.class)
    public void addUserPasswordsNotSame(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        dto.setRepeatPassword("newPassword");

        userService.addUser(dto);
    }
    //email exception
    @Test(expected = ApiRequestException.class)
    public void addUserEmailAlreadyTaken(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        Mockito.when(userRepository.findByEmail(dto.getEmail())).thenReturn(new User());

        userService.addUser(dto);
    }

    //success
    @Test
    public void addUserSuccess(){
        UserRegistrationDTO dto = UserConstants.returnUserRegistrationDto();
        Authority aut = new Authority();
        aut.setName(UserRoles.ROLE_USER);
        aut.setId(1L);
        Timestamp time  = new Timestamp(new Date().getTime());

        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn(dto.getPassword());
        Mockito.when(userRepository.findByUsername(dto.getUsername())).thenReturn(null);
        Mockito.when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);
        Mockito.when(authorityRepository.findByName(UserRoles.ROLE_USER)).thenReturn(aut);
        Mockito.when(timeProvider.nowTimestamp()).thenReturn(time);

        User result = userService.addUser(dto);

        assertEquals(dto.getUsername(), result.getUsername());
        assertEquals(dto.getPassword(),result.getPassword());
        assertEquals(dto.getFirstName(),result.getFirstName());
        assertEquals(dto.getLastName(),result.getLastName());
        assertEquals(dto.getEmail(),result.getEmail());
        assertEquals("ROLE_USER",result.getUserAuthorities().iterator().next().getName());
        assertFalse(result.isActivatedAccount());
        assertEquals(time, result.getLastPasswordResetDate());


    }
    //activateAccount
    //no token exception
    @Test(expected = ResourceNotFoundException.class)
    public void activateAccountNoToken(){
        String token = "$token";
        Mockito.when(tokenRepository.findByToken(token)).thenReturn(null);

        userService.activateAccount(token);
    }

    //token used exception
    @Test(expected = ApiRequestException.class)
    public void activateAccountTokenUsed(){
        String token = "$token";
        ConfirmationToken fakeToken = new ConfirmationToken();
        fakeToken.setUsed(true);
        Mockito.when(tokenRepository.findByToken(token)).thenReturn(fakeToken);

        userService.activateAccount(token);
    }

    //token timeout
    @Test(expected = ApiRequestException.class)
    public void activateAccountTokenTimeout(){
        String token = "$token";
        ConfirmationToken fakeToken = new ConfirmationToken();
        fakeToken.setUsed(false);
        Mockito.when(tokenRepository.findByToken(token)).thenReturn(fakeToken);
        Mockito.when(timeProvider.timeDifferenceInMinutes(timeProvider.now(), fakeToken.getDatetimeCreated())).thenReturn(50L);

        userService.activateAccount(token);
    }

    //activated user
    @Test
    public void activateAccount(){
        String token = "$token";
        ConfirmationToken fakeToken = new ConfirmationToken();
        User user = new User();
        fakeToken.setUser(user);
        fakeToken.setUsed(false);
        Mockito.when(tokenRepository.findByToken(token)).thenReturn(fakeToken);
        Mockito.when(timeProvider.timeDifferenceInMinutes(timeProvider.now(), fakeToken.getDatetimeCreated())).thenReturn(20L);


        assertTrue(userService.activateAccount(token));
    }

    //getMyProfileData
    @Test
    public void getMyProfileData(){
        User user = new User();
        user.setUsername("petarp");
        user.setFirstName("Petar");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        User result = userService.getMyProfileData();

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getFirstName(), result.getFirstName());
    }

    //TeditUser
    //email already used
    @Test(expected = ApiRequestException.class)
    public void editUserEmailTaken(){
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Petar");
        user1.setLastName("Petrovic");
        user1.setEmail("petar@gmail.com");


        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Marko");
        user2.setLastName("Markovic");
        user2.setEmail("marko@gmail.com");

        UserEditDTO dto = new UserEditDTO();
        dto.setEmail("marko@gmail.com");
        dto.setFirstName("Petar");
        dto.setLastName("Petrovic");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user1);
        Mockito.when(userRepository.findByEmail("marko@gmail.com")).thenReturn(user2);

        userService.editUser(dto);
    }
    //success
    @Test
    public void editUserSuccess(){
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Petar");
        user1.setLastName("Petrovic");
        user1.setEmail("petar@gmail.com");

        UserEditDTO dto = new UserEditDTO();
        dto.setEmail("petarp@gmail.com");
        dto.setFirstName("Petar");
        dto.setLastName("Nemanjic");

        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user1);
        Mockito.when(userRepository.findByEmail("petarp@gmail.com")).thenReturn(null);

        User result = userService.editUser(dto);
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getEmail(),result.getEmail());
    }
    
}
