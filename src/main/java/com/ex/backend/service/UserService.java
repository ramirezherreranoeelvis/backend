package com.ex.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ex.backend.dao.IUserDAO;
import com.ex.backend.message.Message;
import com.ex.backend.model.User;
import com.ex.backend.repository.IUserRespository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserDAO {

        @Override
        public ResponseEntity<?> register(String email, String password) {
                try {
                        // Check if the user already exists
                        if (userRepository.existsByEmail(email)) {
                                return ResponseEntity.badRequest().body("User already exists");
                        }
                        // encript the password
                        var passwordEncoder = this.passwordEncoder.encode(password);
                        // Create a new user
                        var user = User.builder().email(email).password(passwordEncoder).build();
                        this.userRepository.save(user);
                        return ResponseEntity.ok(Message.message("User registered successfully", user));
                } catch (Exception e) {
                        return ResponseEntity.internalServerError().body("User registered successfully");
                }

        }

        @Override
        public ResponseEntity<?> auth(String email, String password) {
                try {
                        // Check if the user exists
                        if (!userRepository.existsByEmail(email)) {
                                return ResponseEntity.badRequest().body("User not found");
                        }
                        // Get the user
                        var user = userRepository.findByEmail(email);
                        
                        // Check if the password is correct
                        if (!passwordEncoder.matches(password, user.getPassword())) {
                                return ResponseEntity.badRequest().body("Invalid password");
                        }
                        // Return the user
                        return ResponseEntity.ok(Message.message("User authenticated successfully", user));
                } catch (Exception e) {
                        return ResponseEntity.internalServerError().body("Error authenticating user");
                }
                
        }

        private final IUserRespository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserService(IUserRespository userRespository) {
                this.userRepository = userRespository;
                this.passwordEncoder = new BCryptPasswordEncoder();
        }

}
