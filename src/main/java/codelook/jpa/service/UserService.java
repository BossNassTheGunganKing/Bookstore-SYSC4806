package codelook.jpa.service;

import codelook.jpa.model.UserRole;
import codelook.jpa.repository.UserInfoRepo;
import codelook.jpa.request.ErrorResponse;
import codelook.jpa.request.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import codelook.jpa.model.UserInfo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

import static java.lang.String.valueOf;

@Service
public class UserService {

    private final UserInfoRepo userInfoRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserInfoRepo userInfoRepo, PasswordEncoder passwordEncoder) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return userInfoRepo.existsUserInfoByUsername(username);
    }

    public UserInfo registerUser(UserRegistrationRequest userRegistrationRequest) {
        String encodedPassword = passwordEncoder.encode(userRegistrationRequest.password());
        UserInfo newUser = new UserInfo(userRegistrationRequest.username(), encodedPassword, userRegistrationRequest.password(), userRegistrationRequest.role());
        System.out.println("Saving user: " + newUser);
        return userInfoRepo.save(newUser);
    }

}