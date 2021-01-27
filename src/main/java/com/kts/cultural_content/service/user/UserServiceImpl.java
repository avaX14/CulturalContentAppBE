package com.kts.cultural_content.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kts.cultural_content.common.TimeProvider;
import com.kts.cultural_content.common.consts.UserRoles;
import com.kts.cultural_content.dto.UserEditDTO;
import com.kts.cultural_content.dto.UserRegistrationDTO;
import com.kts.cultural_content.exception.ApiRequestException;
import com.kts.cultural_content.exception.ResourceAlreadyExistsException;
import com.kts.cultural_content.exception.ResourceNotFoundException;
import com.kts.cultural_content.helper.UserMapper;
import com.kts.cultural_content.model.ConfirmationToken;
import com.kts.cultural_content.model.User;
import com.kts.cultural_content.repository.AuthorityRepository;
import com.kts.cultural_content.repository.ConfirmationTokenRepository;
import com.kts.cultural_content.repository.UserRepository;
import com.kts.cultural_content.service.email.MailSenderService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("User with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new ResourceNotFoundException("User with username '" + username + "' doesn't exist.");

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserRegistrationDTO userInfo) {
        if (userRepository.findByUsername(userInfo.getUsername()) != null) {
            throw new ApiRequestException("Username '" + userInfo.getUsername() + "' already exists.");
        }

        if (!userInfo.getPassword().equals(userInfo.getRepeatPassword())) {
            throw new ApiRequestException("Provided passwords must be the same.");
        }

        if (userRepository.findByEmail(userInfo.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("Email '" + userInfo.getEmail() + "' is taken.");
        }

        User user = createNewUserObject(userInfo);
        userRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepository.save(token);

        mailSenderService.sendRegistrationMail(token);

        return user;
    }

    private User createNewUserObject(UserRegistrationDTO userInfo) {
        User user = UserMapper.toEntity(userInfo);
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setLastPasswordResetDate(timeProvider.nowTimestamp());
        user.getUserAuthorities().add(authorityRepository.findByName(UserRoles.ROLE_USER));

        return user;
    }

    @Override
    public boolean activateAccount(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation token doesn't exist.");
        }

        if (confirmationToken.isUsed()) {
            throw new ApiRequestException("This token has been already used.");
        }

        User user = confirmationToken.getUser();
        long timeDifference = timeProvider.timeDifferenceInMinutes(timeProvider.now(), confirmationToken.getDatetimeCreated());

        if (timeDifference < 30) {
            user.setActivatedAccount(true);
            userRepository.save(user);
            confirmationToken.setUsed(true);
            tokenRepository.save(confirmationToken);
            return true;
        } else {
            tokenRepository.delete(confirmationToken);
            userRepository.delete(user);
            throw new ApiRequestException("Confirmation token timed out.");
        }

    }

    @Override
    public User getMyProfileData() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User editUser(UserEditDTO userInfo) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        User dbUser = userRepository.findByEmail(userInfo.getEmail());

        if(dbUser != null) {
            if (dbUser.getId() != user.getId()) {
                throw new ApiRequestException("Email '" + userInfo.getEmail() + "' is taken.");
            }
        }

        user.setEmail(userInfo.getEmail());

        userRepository.save(user);

        return user;
    }

}
