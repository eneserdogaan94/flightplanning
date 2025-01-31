package com.example.flightplanning.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    private LoginRequest loginRequestUnderTest;

    @BeforeEach
    void setUp() {
        loginRequestUnderTest = new LoginRequest();
    }

    @Test
    void testUsernameGetterAndSetter() {
        final String username = "username";
        loginRequestUnderTest.setUsername(username);
        assertThat(loginRequestUnderTest.getUsername()).isEqualTo(username);
    }

    @Test
    void testPasswordGetterAndSetter() {
        final String password = "password";
        loginRequestUnderTest.setPassword(password);
        assertThat(loginRequestUnderTest.getPassword()).isEqualTo(password);
    }
}
