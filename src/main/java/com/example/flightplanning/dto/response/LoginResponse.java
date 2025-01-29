package com.example.flightplanning.dto.response;

public class LoginResponse {
    private final String message;
    private final String token;
    private final String role;

    public LoginResponse(String message, String token, String role) {
        this.message = message;
        this.token = token;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}

