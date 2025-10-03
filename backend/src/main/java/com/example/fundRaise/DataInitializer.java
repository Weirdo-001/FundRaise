package com.example.fundRaise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring will automatically inject the beans it knows about (UserRepository and PasswordEncoder)
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // We check if an admin already exists to avoid creating a new one on every application restart
        if (userRepository.findByEmail("admin@fundhorizon.com").isEmpty()) {

            System.out.println("No ADMIN user found. Creating a default admin...");

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@fundhorizon.com");
            // IMPORTANT: Never store plain text passwords. Always encode them.
            admin.setPassword(passwordEncoder.encode("adminpass123")); // Change this to a strong password
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Default ADMIN user has been created.");
        }
    }
}