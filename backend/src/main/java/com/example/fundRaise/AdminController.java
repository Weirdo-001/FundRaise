// File: src/main/java/com/example/fundRaise/AdminController.java (Updated)

package com.example.fundRaise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Make sure all these are imported
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000") // Also add CrossOrigin here
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. GET ALL USERS (You already have this)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // 2. DELETE A USER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Make sure you don't delete the main admin or the logged-in admin
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}