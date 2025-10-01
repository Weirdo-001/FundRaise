package com.example.fundhorizon;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;

    // And again, @Data generates these. Remove if you prefer.
     public String getEmail() {
     	return email;
     }
     public String getPassword() {
     	return password;
     }
}