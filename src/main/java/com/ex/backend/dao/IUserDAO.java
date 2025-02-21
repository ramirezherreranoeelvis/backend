package com.ex.backend.dao;

import org.springframework.http.ResponseEntity;

public interface IUserDAO {
        
        ResponseEntity<?> register(String email, String password);

        ResponseEntity<?> auth(String email, String password);

        ResponseEntity<?> decrypt(String name);

}
