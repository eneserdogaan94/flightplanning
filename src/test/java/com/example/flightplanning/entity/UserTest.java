package com.example.flightplanning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User();
    }

    @Test
    void testIdGetterAndSetter() {
        final Integer id = 0;
        userUnderTest.setId(id);
        assertThat(userUnderTest.getId()).isEqualTo(id);
    }

    @Test
    void testFirstNameGetterAndSetter() {
        final String firstName = "firstName";
        userUnderTest.setFirstName(firstName);
        assertThat(userUnderTest.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void testLastNameGetterAndSetter() {
        final String lastName = "lastName";
        userUnderTest.setLastName(lastName);
        assertThat(userUnderTest.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testCityGetterAndSetter() {
        final String city = "city";
        userUnderTest.setCity(city);
        assertThat(userUnderTest.getCity()).isEqualTo(city);
    }

    @Test
    void testUsernameGetterAndSetter() {
        final String username = "username";
        userUnderTest.setUsername(username);
        assertThat(userUnderTest.getUsername()).isEqualTo(username);
    }

    @Test
    void testPasswordGetterAndSetter() {
        final String password = "password";
        userUnderTest.setPassword(password);
        assertThat(userUnderTest.getPassword()).isEqualTo(password);
    }

    @Test
    void testRoleGetterAndSetter() {
        final Role role = Role.ADMIN;
        userUnderTest.setRole(role);
        assertThat(userUnderTest.getRole()).isEqualTo(role);
    }
}
