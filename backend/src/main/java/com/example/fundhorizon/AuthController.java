package com.example.fundhorizon;

import com.example.fundhorizon.AuthResponse;
import com.example.fundhorizon.LoginDTO;
import com.example.fundhorizon.SignupDTO;
import com.example.fundhorizon.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Adjust if your React app runs on a different port
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        AuthResponse response = authService.login(loginDTO);

        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        // If login is successful, you might want to include a token in the response
        // For example:
        // response.setToken(generateToken(loginDTO.getEmail()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupDTO signupDTO) {
        AuthResponse response = authService.signup(signupDTO);

        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    // You might add a helper method to generate a JWT token here if you plan to use one
    // private String generateToken(String email) {
    //     // Implementation for JWT token generation
    //     return null;
    // }
}