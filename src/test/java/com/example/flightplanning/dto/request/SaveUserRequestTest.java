package com.example.flightplanning.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SaveUserRequestTest {

    private SaveUserRequest saveUserRequestUnderTest;

    @BeforeEach
    void setUp() {
        saveUserRequestUnderTest = new SaveUserRequest();
    }

    @Test
    void testFirstNameGetterAndSetter() {
        final String firstName = "firstName";
        saveUserRequestUnderTest.setFirstName(firstName);
        assertThat(saveUserRequestUnderTest.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void testLastNameGetterAndSetter() {
        final String lastName = "lastName";
        saveUserRequestUnderTest.setLastName(lastName);
        assertThat(saveUserRequestUnderTest.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testCityGetterAndSetter() {
        final String city = "city";
        saveUserRequestUnderTest.setCity(city);
        assertThat(saveUserRequestUnderTest.getCity()).isEqualTo(city);
    }

    @Test
    void testUsernameGetterAndSetter() {
        final String username = "username";
        saveUserRequestUnderTest.setUsername(username);
        assertThat(saveUserRequestUnderTest.getUsername()).isEqualTo(username);
    }

    @Test
    void testPasswordGetterAndSetter() {
        final String password = "password";
        saveUserRequestUnderTest.setPassword(password);
        assertThat(saveUserRequestUnderTest.getPassword()).isEqualTo(password);
    }
}
