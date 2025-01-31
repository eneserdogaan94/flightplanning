package com.example.flightplanning.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private CustomUserDetailsService mockUserDetailsService;

    private SecurityConfig securityConfigUnderTest;

    @BeforeEach
    void setUp() {
        securityConfigUnderTest = new SecurityConfig(mockUserDetailsService);
    }

    @Test
    void testSecurityFilterChain_ThrowsException() {
        // Setup
        final HttpSecurity http = new HttpSecurity(ObjectPostProcessor.identity(),
                new AuthenticationManagerBuilder(ObjectPostProcessor.identity()),
                Map.ofEntries(Map.entry(String.class, "value")));

        // Run the test
        assertThatThrownBy(() -> securityConfigUnderTest.securityFilterChain(http)).isInstanceOf(Exception.class);
    }

    @Test
    void testAuthenticationProvider() {
        // Setup
        // Run the test
        final AuthenticationProvider result = securityConfigUnderTest.authenticationProvider();

        // Verify the results
    }

    @Test
    void testPasswordEncoder() {
        // Setup
        // Run the test
        final PasswordEncoder result = securityConfigUnderTest.passwordEncoder();

        // Verify the results
    }

    @Test
    void testAuthenticationManager_ThrowsException() {
        // Setup
        final AuthenticationConfiguration config = new AuthenticationConfiguration();
        config.setApplicationContext(null);
        config.setObjectPostProcessor(ObjectPostProcessor.identity());

        // Run the test
        assertThatThrownBy(() -> securityConfigUnderTest.authenticationManager(config)).isInstanceOf(Exception.class);
    }
}
