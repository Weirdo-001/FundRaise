package com.example.fundhorizon;

import lombok.Data;

@Data
public class SignupDTO {
    private String name;
    private String email;
    private String password;

    // Again, @Data generates these. You can remove them.
     public String getEmail() {
     	return email;
     }
     public String getName() {
     	return name;
     }
     public String getPassword() {
     	return password;
     }
}