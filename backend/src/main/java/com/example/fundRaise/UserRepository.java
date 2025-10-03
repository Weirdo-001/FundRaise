
package com.example.fundRaise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // <-- ADD THIS IMPORT
import java.util.Optional;

@Repository // <-- ADD THIS ANNOTATION
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // This method you added is great, keep it!
    boolean existsByEmail(String email);
}