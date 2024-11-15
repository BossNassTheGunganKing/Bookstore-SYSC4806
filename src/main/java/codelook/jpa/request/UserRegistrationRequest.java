package codelook.jpa.request;

import codelook.jpa.model.UserRole;

public record UserRegistrationRequest(String username, String password, String email, String role) { }
