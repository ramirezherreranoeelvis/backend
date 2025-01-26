package com.ex.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.ex.backend.dto.EmployeCreateDTO;
import com.ex.backend.dto.EmployeDisplayDTO;
import com.ex.backend.model.Employee;

public interface IEmployeeDAO {

        ResponseEntity<?> create(EmployeCreateDTO employeCreate);

        List<EmployeDisplayDTO> findEmployess();

        ResponseEntity<?> update(EmployeCreateDTO employeUpdate, String numeroIdentificacion);

        ResponseEntity<?> salida(String numeroIdentificacion);

        ResponseEntity<?> entrada(String numeroIdentificacion);

        Optional<Employee> findByIdentification(String numeroIdentificacion);

        ResponseEntity<?> delete(String numeroIdentificacion);

        boolean verifyEmployeeExistence(String identificationNumber);

        boolean verifyEmployeeExistence(String identificationNumber, String identificationNumberExcept);
}
