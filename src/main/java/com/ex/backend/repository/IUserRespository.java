package com.ex.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.backend.model.User;

@Repository
public interface IUserRespository extends JpaRepository<User, Integer> {

        boolean existsByEmail(String email);

        User findByEmail(String email);

}
