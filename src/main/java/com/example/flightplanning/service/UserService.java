package com.example.flightplanning.service;

import com.example.flightplanning.dto.request.SignupRequest;
import com.example.flightplanning.entity.Role;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(SignupRequest signupRequest) {
        User newUser = new User();
        newUser.setFirstName(signupRequest.getFirstName());
        newUser.setLastName(signupRequest.getLastName());
        newUser.setCity(signupRequest.getCity());
        newUser.setUsername(signupRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
    }
}
