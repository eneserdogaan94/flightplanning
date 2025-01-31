package com.example.flightplanning.service;

import com.example.flightplanning.dto.request.SignupRequest;
import com.example.flightplanning.entity.Role;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testGetAllUsers() {
        // Setup
        // Configure UserRepository.findAll(...).
        final User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCity("city");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        final List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
    }

    @Test
    void testGetAllUsers_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetUserByUsername() {
        // Setup
        // Configure UserRepository.findByUsername(...).
        final User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCity("city");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.getUserByUsername("username");

        // Verify the results
    }

    @Test
    void testSaveUser() {
        // Setup
        final SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("firstName");
        signupRequest.setLastName("lastName");
        signupRequest.setCity("city");
        signupRequest.setUsername("username");
        signupRequest.setPassword("password");

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        userServiceUnderTest.saveUser(signupRequest);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }
}
