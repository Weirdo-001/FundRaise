// File: src/main/java/com/example/fundRaise/CustomUserDetailsService.java (NEW AND ESSENTIAL)
package com.example.fundRaise;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // This method is called by Spring Security's DaoAuthenticationProvider
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email));

        // Create a set of authorities (roles) for the user
        Set<GrantedAuthority> authorities = Collections.singleton(
                // The "ROLE_" prefix is a standard Spring Security convention
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        // Return a Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
