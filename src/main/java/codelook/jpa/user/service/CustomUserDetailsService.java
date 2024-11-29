package codelook.jpa.user.service;

import codelook.jpa.user.model.UserInfo;
import codelook.jpa.user.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepo userInfoRepo;

    @Autowired
    public CustomUserDetailsService(UserInfoRepo userInfoRepo) {
        this.userInfoRepo = userInfoRepo;
    }

    // Load user by username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("Loaded user: " + user.getUsername() + ", Role: " + user.getRoleWithPrefix());
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoleWithPrefix())
                .build();
    }

}
