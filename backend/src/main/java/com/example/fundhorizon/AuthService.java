package com.example.fundhorizon;

import com.example.fundhorizon.AuthResponse;
import com.example.fundhorizon.LoginDTO;
import com.example.fundhorizon.SignupDTO;
import com.example.fundhorizon.User;
import com.example.fundhorizon.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginDTO loginDTO) {
        AuthResponse response = new AuthResponse();

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            response.setSuccess(false);
            response.setError("Invalid email or password");
            return response;
        }

        response.setSuccess(true);
        response.setMessage("Login successful");
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
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        userRepository.save(user);

        response.setSuccess(true);
        response.setMessage("User registered successfully");
        return response;
    }
}