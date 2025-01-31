package com.example.flightplanning.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SignupRequestTest {

    private SignupRequest signupRequestUnderTest;

    @BeforeEach
    void setUp() {
        signupRequestUnderTest = new SignupRequest();
    }

    @Test
    void testFirstNameGetterAndSetter() {
        final String firstName = "firstName";
        signupRequestUnderTest.setFirstName(firstName);
        assertThat(signupRequestUnderTest.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void testLastNameGetterAndSetter() {
        final String lastName = "lastName";
        signupRequestUnderTest.setLastName(lastName);
        assertThat(signupRequestUnderTest.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testCityGetterAndSetter() {
        final String city = "city";
        signupRequestUnderTest.setCity(city);
        assertThat(signupRequestUnderTest.getCity()).isEqualTo(city);
    }

    @Test
    void testUsernameGetterAndSetter() {
        final String username = "username";
        signupRequestUnderTest.setUsername(username);
        assertThat(signupRequestUnderTest.getUsername()).isEqualTo(username);
    }

    @Test
    void testPasswordGetterAndSetter() {
        final String password = "password";
        signupRequestUnderTest.setPassword(password);
        assertThat(signupRequestUnderTest.getPassword()).isEqualTo(password);
    }
}
