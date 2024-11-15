package codelook.jpa.service;

import codelook.jpa.model.UserInfo;
import codelook.jpa.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().name()));
    }

}
