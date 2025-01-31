package com.example.flightplanning.security;

import com.example.flightplanning.entity.Role;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsServiceUnderTest;

    @Test
    void testLoadUserByUsername() {
        // Setup
        // Configure UserRepository.findByUsername(...).
        final User user = new User();
        user.setId(0);
        user.setFirstName("firstName");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final UserDetails result = customUserDetailsServiceUnderTest.loadUserByUsername("username");

        // Verify the results
    }

    @Test
    void testLoadUserByUsername_UserRepositoryReturnsNull() {
        // Setup
        when(mockUserRepository.findByUsername("username")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> customUserDetailsServiceUnderTest.loadUserByUsername("username"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
