package com.example.flightplanning.controller;

import com.example.flightplanning.dto.request.SaveUserRequest;
import com.example.flightplanning.dto.request.SignupRequest;
import com.example.flightplanning.dto.response.ErrorResponse;
import com.example.flightplanning.dto.response.MessageResponse;
import com.example.flightplanning.entity.Role;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.repository.UserRepository;
import com.example.flightplanning.security.JwtUtil;
import com.example.flightplanning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
            newUser.setRole(Role.USER);

            // Veritabanına kaydet
            userRepository.save(newUser);

            return ResponseEntity.ok(new MessageResponse("Kullanıcı başarıyla kaydedildi!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Kayıt sırasında bir hata oluştu."));
        }
    }
}
