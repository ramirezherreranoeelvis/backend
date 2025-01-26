package com.ex.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ex.backend.dao.IUserDAO;
import com.ex.backend.dto.UserCredentialsDTO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {

        @PostMapping("/register")
        public ResponseEntity<?> register(@Valid @RequestBody UserCredentialsDTO credentials) {
                var email = credentials.getEmail();
                var password = credentials.getPassword();
                return ResponseEntity.ok(this.userDAO.register(email, password));
        }

        // create method auth
        @PostMapping("/auth")
        public ResponseEntity<?> auth(@Valid @RequestBody UserCredentialsDTO credentials) {
                var password = credentials.getPassword();
                var email = credentials.getEmail();
                return this.userDAO.auth(email, password);
        }

        private IUserDAO userDAO;

        public LoginController(IUserDAO userDAO) {
                this.userDAO = userDAO;
        }

}
// 1a%&126sD21