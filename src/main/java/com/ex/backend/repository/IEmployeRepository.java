package com.ex.backend.repository;

import org.springframework.stereotype.Repository;

import com.ex.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IEmployeRepository extends JpaRepository<Employee, Integer> {

}
