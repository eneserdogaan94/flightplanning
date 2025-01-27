package com.example.flightplanning.controller;

import com.example.flightplanning.entity.User;
import com.example.flightplanning.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

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

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Yanlış kullanıcı adı veya şifre!");
        }
    }

    // Basit logout örneği: Frontend token'ı siler.
    // Sunucuda JWT stateful saklanmıyorsa, logout server tarafında ek kontrol gerektirmez.
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT tabanlı yaklaşımda sunucuya ek işlem gerekmeyebilir.
        // Gerekirse token'ı veritabanında black-list tutabilirsiniz.
        return ResponseEntity.ok("Çıkış yapıldı");
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

    static class LoginResponse {
        private final String token;
        public LoginResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
