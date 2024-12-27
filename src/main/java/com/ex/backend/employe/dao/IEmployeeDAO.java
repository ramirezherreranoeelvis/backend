package com.ex.backend.employe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.ex.backend.employe.dto.EmployeCreate;
import com.ex.backend.employe.dto.EmployeDisplay;
import com.ex.backend.model.Employee;

public interface IEmployeeDAO {

        ResponseEntity<?> create(EmployeCreate employeCreate);

        List<EmployeDisplay> findEmployess();

        ResponseEntity<?> update(EmployeCreate employeUpdate, String numeroIdentificacion);

        ResponseEntity<?> salida(String numeroIdentificacion);

        ResponseEntity<?> entrada(String numeroIdentificacion);

        Optional<Employee> findByIdentification(String numeroIdentificacion);

        ResponseEntity<?> delete(String numeroIdentificacion);

        boolean verifyEmployeeExistence(String identificationNumber);

        boolean verifyEmployeeExistence(String identificationNumber, String identificationNumberExcept);
}
