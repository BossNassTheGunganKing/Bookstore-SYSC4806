package codelook.jpa.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String passwordHash;

    private String email;

    private UserRole role;

    public UserInfo() {}

    public UserInfo(String username, String passwordHash, String email, UserRole role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPasswordHash(String encodedPassword) {
        this.passwordHash = encodedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    public String getRoleWithPrefix() {
        return "ROLE_" + role.name();
    }
}
