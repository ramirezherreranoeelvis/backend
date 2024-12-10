package com.ex.backend.employe.repository;

import org.springframework.stereotype.Repository;

import com.ex.backend.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IEmployeRepository extends JpaRepository<Employe, Integer> {

}
