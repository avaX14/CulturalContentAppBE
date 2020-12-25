package com.kts.cultural_content.service.user;

import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    User addUser(UserRegistrationDTO userInfo);
    boolean activateAccount(String token);
    User getMyProfileData();
    User editUser(UserEditDTO userInfo);
}
