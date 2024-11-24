package codelook.jpa.config;

import codelook.jpa.repository.UserInfoRepo;
import codelook.jpa.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/allBooks", "/allListings", "/account", "/account/edit", "/orders", "allGenres").hasAnyAuthority("ROLE_DEFAULT", "ROLE_PUBLISHER", "ROLE_ADMIN")
                        .requestMatchers("/newBook", "/newListing", "/authors").hasAnyAuthority("ROLE_PUBLISHER", "ROLE_ADMIN")
                        .requestMatchers("/users/view", "/allOrders", "/admin/orders").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirect to login with a logout message
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403") // Redirect to the custom "Permission Denied" page
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}