package codelook.jpa.user.service;

import codelook.jpa.user.model.UserInfo;
import codelook.jpa.user.repository.UserInfoRepo;
import codelook.jpa.user.request.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserInfo> user = userInfoRepo.findByUsername(username);
            return user.orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No authenticated user found");
    }

    public void updateAccount(Long userId, String newUsername, String newPassword, String newEmail) {
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update username if provided
        if (newUsername != null && !newUsername.isBlank()) {
            if (!newUsername.equals(user.getUsername()) && usernameExists(newUsername)) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(newUsername);
        }

        // Update password if provided
        if (newPassword != null && !newPassword.isBlank()) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPasswordHash(encodedPassword);
        }

        // Update email if provided
        if (newEmail != null && !newEmail.isBlank()) {
            user.setEmail(newEmail);
        }

        userInfoRepo.save(user);
    }

}