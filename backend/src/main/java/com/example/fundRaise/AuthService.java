// File: src/main/java/com/example/fundRaise/AuthService.java (Corrected & Refined)
package com.example.fundRaise;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Key jwtSigningKey;

    // Make sure 'app.jwt.secret' is defined in your application.properties
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, @Value("${app.jwt.secret}") String jwtSecret) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        // Ensure the secret is long enough for HS512 algorithm
        this.jwtSigningKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public AuthResponse login(LoginDTO loginDTO) {
        AuthResponse response = new AuthResponse();
        try {
            // This is the key part. Spring Security's AuthenticationManager will use
            // the DaoAuthenticationProvider (configured in SecurityConfig) to:
            // 1. Call your CustomUserDetailsService to fetch the user by email.
            // 2. Use the PasswordEncoder to compare the submitted DTO password with the stored hash.
            // If they don't match, it will throw a BadCredentialsException.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            // If authentication is successful, proceed.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Fetch the user details again to build the response
            Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
            if (userOptional.isEmpty()) {
                // This case should theoretically not be reached if authentication succeeds
                throw new IllegalStateException("User not found after successful authentication");
            }
            User user = userOptional.get();
            String token = generateToken(user.getEmail());

            response.setSuccess(true);
            response.setMessage("Login successful");
            response.setToken(token);
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole().name());

        } catch (BadCredentialsException e) {
            // THIS IS THE CRITICAL CHANGE. We specifically catch the right exception.
            System.err.println("Authentication failed: " + e.getMessage());
            response.setSuccess(false);
            response.setError("Invalid email or password");
        } catch (Exception e) {
            // Catch any other unexpected errors during login
            System.err.println("An unexpected error occurred during login: " + e.getMessage());
            response.setSuccess(false);
            response.setError("An internal error occurred. Please try again.");
        }
        return response;
    }

    public AuthResponse signup(SignupDTO signupDTO) {
        AuthResponse response = new AuthResponse();
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            response.setSuccess(false);
            response.setError("Email is already in use");
            return response;
        }
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        // Default new users to the USER role
        user.setRole(Role.USER);

        userRepository.save(user);

        response.setSuccess(true);
        response.setMessage("User registered successfully");
        return response;
    }

    private String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 86400000; // Token valid for 24 hours
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(jwtSigningKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
