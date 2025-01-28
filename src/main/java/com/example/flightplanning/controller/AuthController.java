package com.example.flightplanning.controller;

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
            // Kullanıcı adını ve şifreyi doğrula
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Doğrulama başarılıysa token üret
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse("Giriş başarılı", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Yanlış kullanıcı adı veya şifre!"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(new ErrorResponse("Hesabınız devre dışı bırakılmış!"));
        } catch (LockedException e) {
            return ResponseEntity.status(423).body(new ErrorResponse("Hesabınız kilitlenmiş!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Bilinmeyen bir hata oluştu."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new MessageResponse("Çıkış yapıldı"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            // Kullanıcıyı veritabanında kontrol et
            Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(signupRequest.getUsername()));
            if (existingUser.isPresent()) {
                return ResponseEntity.status(409).body(new ErrorResponse("Kullanıcı adı zaten mevcut!"));
            }

            // Yeni kullanıcı oluştur
            User newUser = new User();
            newUser.setFirstName(signupRequest.getFirstName());
            newUser.setLastName(signupRequest.getLastName());
            newUser.setCity(signupRequest.getCity());
            newUser.setUsername(signupRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            newUser.setRole(Role.USER); // Varsayılan olarak "USER" rolü atanıyor

            // Veritabanına kaydet
            userRepository.save(newUser);

            return ResponseEntity.ok(new MessageResponse("Kullanıcı başarıyla kaydedildi!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Kayıt sırasında bir hata oluştu."));
        }
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

    // DTO sınıfları
    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class SignupRequest {
        private String firstName;
        private String lastName;
        private String city;
        private String username;
        private String password;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class LoginResponse {
        private final String message;
        private final String token;

        public LoginResponse(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }

    static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

    static class MessageResponse {
        private final String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
