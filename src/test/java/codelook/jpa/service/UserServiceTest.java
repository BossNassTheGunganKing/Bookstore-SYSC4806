package codelook.jpa.service;

import codelook.jpa.user.model.UserInfo;
import codelook.jpa.user.model.UserRole;
import codelook.jpa.user.repository.UserInfoRepo;
import codelook.jpa.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserInfoRepo userInfoRepo;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userInfoRepo = mock(UserInfoRepo.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userInfoRepo, passwordEncoder);
    }

    @Test
    void testUpdateAccount() {
        // Arrange
        UserInfo existingUser = new UserInfo("testuser", "oldPasswordHash", "oldemail@example.com", UserRole.DEFAULT);
        existingUser.setId(1L);

        when(userInfoRepo.findById(1L)).thenReturn(java.util.Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("newPasswordHash");

        // Act
        userService.updateAccount(1L, "newuser", "newPassword", "newemail@example.com");

        // Assert
        assertEquals("newuser", existingUser.getUsername());
        assertEquals("newPasswordHash", existingUser.getPassword());
        assertEquals("newemail@example.com", existingUser.getEmail());
        verify(userInfoRepo).save(existingUser);
    }
}