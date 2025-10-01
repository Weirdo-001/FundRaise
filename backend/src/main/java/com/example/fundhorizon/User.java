package com.example.fundhorizon;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Lombok's @Data annotation already generates getters and setters,
    // so these manual getter/setter methods are redundant.
    // You can remove them if you are using Lombok.
     public String getPassword() {
     	return password;
     }
    
     public void setName(String name) {
     	this.name = name;
     }
    
     public void setEmail(String email) {
     	this.email = email;
     }
    
     public void setPassword(String password) {
     	this.password = password;
     }
}