// File: src/main/java/com/example/fundRaise/AuthController.java (Corrected)
package com.example.fundRaise;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// Crucially, ensure this matches your React app's URL
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    // Use @Valid to trigger validation if you add it to LoginDTO
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponse response = authService.login(loginDTO);
        // If login fails, authService now sets the error.
        // The service logic will throw an exception which is caught.
        // We return a 401 Unauthorized status for security best practices.
        if (!response.isSuccess()) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupDTO signupDTO) {
        AuthResponse response = authService.signup(signupDTO);
        if (!response.isSuccess()) {
            // Bad request (e.g., email exists) is appropriate here.
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
