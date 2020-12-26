package com.kts.cultural_content.constants;

import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.model.User;

public class UserConstants {

    // Login data for existing user (admin)
    public static final Long DB_ID = 1L;
    public static final String DB_USERNAME = "MarkoMarkovic12";
    public static final String DB_PASSWORD = "test1234";


    public static final String DB_USERNAME_NON_EXIST = "nikolaNikolic";

    public static final String DB_CHANGED_PASSWORD = "newPass";


    //admin other info
    public static final String DB_EMAIL = "markoMarkovic@maildrop.cc";
    public static final boolean DB_ACTIVATED_ACCOUNT = true;
    public static final String DB_FIRST_NAME = "Marko";
    public static final String DB_LAST_NAME = "Markovic";


    public static final Long DB_USER_ID = 2L;
    public static final String DB_USER_USERNAME = "tamaraSimic6";
    public static final String DB_USER_PASSWORD = "test1234";

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_USERNAME = "MarkoMarkovic12";
    public static final String MOCK_PASSWORD = "test1234";

    public static final String MOCK_NEW_PASSWORD = "test234";

    public static final Long MOCK_ID_OTHER = 2L;
    public static final String MOCK_USERNAME_OTHER = "tamaraSimic6";
    public static final String MOCK_PASSWORD_OTHER = "test1234";


    public static final String NEW_USERNAME = "test1234";
    public static final String NEW_PASSWORD = "test1234";


    private UserConstants() {
    }

    public static User returnLoggedUser() {
        User user = new User();
        user.setId(UserConstants.MOCK_ID);
        user.setUsername(UserConstants.MOCK_USERNAME);
        user.setPassword(UserConstants.MOCK_PASSWORD);
        return user;
    }

    public static UserRegistrationDTO returnUserRegistrationDto(){
        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setUsername("nikolaNikolic");
        user.setFirstName("Nikola");
        user.setLastName("Nikolic");
        user.setEmail("nikolan@gmail.com");
        user.setPassword("test1234");
        user.setRepeatPassword("test1234");
        return user;
    }
}
