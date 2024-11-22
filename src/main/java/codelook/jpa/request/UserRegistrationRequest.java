package codelook.jpa.request;


import codelook.jpa.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")// @Min(value=8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Role cannot be null")
        UserRole role
) { }
