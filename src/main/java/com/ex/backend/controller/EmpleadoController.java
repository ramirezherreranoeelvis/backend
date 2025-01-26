package com.ex.backend.controller;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.backend.dao.IEmployeeDAO;
import com.ex.backend.dto.EmployeCreateDTO;
import com.ex.backend.util.ValidDataUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EmpleadoController {

        @Autowired
        private IEmployeeDAO employeeDAO;

        @GetMapping("")
        public ResponseEntity<?> employees() {
                return ResponseEntity.ok(employeeDAO.findEmployess());
        }

        @PostMapping("/create")
        public ResponseEntity<?> createEmployee(@RequestBody EmployeCreateDTO employeCreate) {
                if (isMissingData.test(employeCreate)) {
                        return ResponseEntity.badRequest().body("Faltan datos");
                }
                if (!ValidDataUtil.identificationNumberValid(employeCreate.getIdentificationNumber())) {
                        return ResponseEntity.badRequest().body("Número de Identificación No Valido");
                }
                if (this.employeeDAO.verifyEmployeeExistence(employeCreate.getIdentificationNumber())) {
                        return ResponseEntity.badRequest().body("El número de identificación ya está en uso");
                }

                if (!isNamesValid.test(employeCreate)) {
                        return ResponseEntity.badRequest().body("No se permite caracteres especiales como ñ o tildes");
                }

                return this.employeeDAO.create(employeCreate);
        }

        @PutMapping("/update")
        public ResponseEntity<?> updateDataEmployed(@RequestBody EmployeCreateDTO employeUpdate,
                        @RequestParam String identificationNumber) {
                // Validar si faltan datos
                if (isMissingData.test(employeUpdate)) {
                        return ResponseEntity.badRequest().body("Faltan datos");
                }
                if (!ValidDataUtil.identificationNumberValid(employeUpdate.getIdentificationNumber())) {
                        return ResponseEntity.badRequest().body("Número de Identificación No Valido");
                }
                // Validar caracteres inválidos en nombres
                if (!isNamesValid.test(employeUpdate)) {
                        return ResponseEntity.badRequest().body("No se permiten caracteres especiales como ñ o tildes");
                }

                // Verificar si el empleado a actualizar existe
                if (!employeeDAO.verifyEmployeeExistence(identificationNumber)) {
                        return ResponseEntity.badRequest().body("El empleado a actualizar no existe");
                }

                // Verificar si el número de identificación ya está en uso
                if (employeeDAO.verifyEmployeeExistence(employeUpdate.getIdentificationNumber(), identificationNumber)) {
                        return ResponseEntity.badRequest().body("El número de identificación ya está en uso");
                }

                var result = this.employeeDAO.update(employeUpdate, identificationNumber);
                return result;
        }

        @PostMapping("/entry")
        public ResponseEntity<?> registerEntry(@RequestParam String identificationNumber) {
                if (identificationNumber.equals(null)) {
                        return ResponseEntity.badRequest().body("Ingrese un id Valido");
                }
                if (this.employeeDAO.findByIdentification(identificationNumber).isEmpty()) {
                        return ResponseEntity.badRequest().body("El empleado a registrar ingreso no existe");
                }
                var value = employeeDAO.entrada(identificationNumber);

                return value;
        }

        @PostMapping("/exit")
        public ResponseEntity<?> registerExit(@RequestParam String identificationNumber) {
                if (identificationNumber.equals(null)) {
                        return ResponseEntity.badRequest().body("Ingrese un id Valido");
                }
                if (this.employeeDAO.findByIdentification(identificationNumber).isEmpty()) {
                        return ResponseEntity.badRequest().body("El empleado a registrar ingreso no existe");
                }
                var value = employeeDAO.salida(identificationNumber);

                return value;
        }

        @DeleteMapping("/delete")
        public ResponseEntity<?> deleteEmployee(@RequestParam String identificationNumber) {
                return this.employeeDAO.delete(identificationNumber);
        }

        Predicate<EmployeCreateDTO> isMissingData = employeCreate -> Stream.of(
                        employeCreate.getIdentification(),
                        employeCreate.getNames(),
                        employeCreate.getIdentificationNumber(),
                        employeCreate.getCountry(),
                        employeCreate.getFirstSurname(),
                        employeCreate.getSecondSurname())
                        .anyMatch(Objects::isNull);

        Predicate<EmployeCreateDTO> isNamesValid = employeCreate -> Stream.of(
                        employeCreate.getNames(),
                        employeCreate.getFirstSurname(),
                        employeCreate.getSecondSurname())
                        .allMatch(ValidDataUtil::namesValid);
}
