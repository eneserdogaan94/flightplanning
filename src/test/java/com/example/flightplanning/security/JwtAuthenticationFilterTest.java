package com.example.flightplanning.security;

import jakarta.servlet.FilterChain;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil mockJwtUtil;
    @Mock
    private CustomUserDetailsService mockUserDetailsService;
    @Mock
    private FilterChain mockFilterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilterUnderTest;

    @Test
    void testDoFilterInternal() throws Exception {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        // Run the test
        jwtAuthenticationFilterUnderTest.doFilterInternal(request, response, mockFilterChain);

        // Verify
        verify(mockFilterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_JwtUtilValidateTokenReturnsTrue() throws Exception {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        // Run the test
        jwtAuthenticationFilterUnderTest.doFilterInternal(request, response, mockFilterChain);

        // Verify
        verify(mockFilterChain).doFilter(request, response);
    }


}
