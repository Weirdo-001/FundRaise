package com.example.fundRaise;

import lombok.Data;

@Data
public class AuthResponse {
    private boolean success;
    private String message;
    private String error;

    // --- ADD/UPDATE THESE FIELDS ---
    private String token;
    private String name;
    private String email;
    private String role;

    // Add getters and setters for the token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Again, @Data generates these. Remove if you prefer.
     public void setSuccess(boolean success) {
     	this.success = success;
     }
     public void setError(String error) {
     	this.error = error;
     }
     public void setMessage(String message) {
     	this.message = message;
     }
     public boolean isSuccess() {
     	return success;
     }
}