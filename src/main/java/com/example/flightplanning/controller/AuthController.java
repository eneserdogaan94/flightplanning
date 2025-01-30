package com.example.flightplanning.controller;

import com.example.flightplanning.dto.request.LoginRequest;
import com.example.flightplanning.dto.request.SignupRequest;
import com.example.flightplanning.dto.response.ErrorResponse;
import com.example.flightplanning.dto.response.LoginResponse;
import com.example.flightplanning.dto.response.MessageResponse;
import com.example.flightplanning.entity.Role;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.security.JwtUtil;
import com.example.flightplanning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            User user = userRepository.findByUsername(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse("Giriş başarılı", token, user.getRole().name()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Yanlış kullanıcı adı veya şifre!"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new MessageResponse("Çıkış yapıldı"));
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok(Map.of("username", username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
