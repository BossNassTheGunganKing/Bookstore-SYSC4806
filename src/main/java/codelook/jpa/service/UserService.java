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

    public UserInfo registerUser(String username, String rawPassword, String email, UserRole role) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserInfo newUser = new UserInfo(username, encodedPassword, email, role);
        System.out.println("Saving user: " + newUser);
        return userInfoRepo.save(newUser);
    }

    public ErrorResponse validateUserRegistration(UserRegistrationRequest registrationRequest, UserRole loggedInRole) {
        Map<String, String> errors = new HashMap<>();
        if(registrationRequest.username() == null || registrationRequest.username().isBlank()){
            errors.put("username", "Username is required");
        }
        if(usernameExists(registrationRequest.username())){
            errors.put("username", "Username is already in use");
        }

        if(registrationRequest.password() == null || registrationRequest.password().isBlank()){
            errors.put("password", "Password is required");
        }
        if(registrationRequest.password() != null && registrationRequest.password().length() < 8){
            errors.put("password", "Password must be at least 8 characters");
        }

        if(registrationRequest.email() == null || registrationRequest.email().isBlank()){
            errors.put("email", "Email is required");
        }
        if(registrationRequest.email() != null && !registrationRequest.email().contains("@")){ // more trouble to fully validate emails than it's worth
            errors.put("email", "Email must be a valid email address");
        }

        if(Objects.equals(registrationRequest.role(), valueOf(UserRole.ADMIN)) && !(loggedInRole == UserRole.ADMIN)){
            errors.put("role", "User must be an admin to add an admin role");
        }

        if(errors.isEmpty()){
            return null;
        }
        return new ErrorResponse("Invalid user registration request",errors);

    }

    public List<UserInfo> getAllUsers() {
        return userInfoRepo.findAll();
    }

    /**
     * Get the currently logged-in user from the Security Context.
     */
    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserInfo> user = userInfoRepo.findByUsername(username);
            return user.orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No authenticated user found");
    }

    /**
     * Get the role of the currently logged-in user.
     */
    public UserRole getCurrentUserRole() {
        UserInfo currentUser = getCurrentUser();
        return currentUser.getRole();
    }
}