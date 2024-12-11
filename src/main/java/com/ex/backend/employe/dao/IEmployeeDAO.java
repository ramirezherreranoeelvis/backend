package com.ex.backend.employe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.ex.backend.employe.dto.EmployeCreate;
import com.ex.backend.employe.dto.EmployeMostrar;
import com.ex.backend.employe.dto.EmployeUpdate;
import com.ex.backend.model.Employe;

public interface IEmployeeDAO {

        Optional<Employe> create(EmployeCreate employeCreate);

        List<EmployeMostrar> findEmployess();

        ResponseEntity<?> update(EmployeUpdate employeUpdate);

        ResponseEntity<?> salida(String numeroIdentificacion);

        ResponseEntity<?> entrada(String numeroIdentificacion);

        Optional<Employe> findByIdentification(String numeroIdentificacion);

        ResponseEntity<?> delete(String numeroIdentificacion);
}
