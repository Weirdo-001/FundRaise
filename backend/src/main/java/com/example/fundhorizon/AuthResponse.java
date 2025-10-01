package com.example.fundhorizon;

import lombok.Data;

@Data
public class AuthResponse {
    private boolean success;
    private String message;
    private String error;

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