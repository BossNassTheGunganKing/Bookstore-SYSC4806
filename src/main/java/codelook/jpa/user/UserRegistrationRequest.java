package codelook.jpa.user;


import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record UserRegistrationRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")// @Min(value=8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Role cannot be null")
        UserRole role
) { }
