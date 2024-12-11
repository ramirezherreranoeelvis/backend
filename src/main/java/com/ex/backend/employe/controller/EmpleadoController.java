package com.ex.backend.employe.controller;

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

import com.ex.backend.employe.dao.IEmployeeDAO;
import com.ex.backend.employe.dto.EmployeCreate;
import com.ex.backend.employe.dto.EmployeMostrar;
import com.ex.backend.employe.dto.EmployeUpdate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EmpleadoController {
        Predicate<String> nombresValid = t -> t == null || !t.matches("[a-zA-Z]+") || t.isEmpty();
        Predicate<String> numeroIdentificacion = t -> t == null || !t.matches("[a-zA-Z0-9]+") || t.isEmpty()
                        || t.length() >= 20;
        @Autowired
        private IEmployeeDAO employeeDAO;

        @GetMapping("")
        public ResponseEntity<?> employees() {
                return ResponseEntity.ok(employeeDAO.findEmployess());
        }

        @PostMapping("/create")
        public ResponseEntity<?> createEmployee(@RequestBody EmployeCreate employeCreate) {
                var existen = Stream.of(
                                employeCreate.getIdentificacion(),
                                employeCreate.getNombres(),
                                employeCreate.getNumeroIdentificacion(),
                                employeCreate.getPais(),
                                employeCreate.getPrimerApellido(),
                                employeCreate.getSegundoApellido()).anyMatch(x -> x == null);
                if (existen) {
                        return ResponseEntity.badRequest().body("Faltan datos");
                }

                if (this.employeeDAO.findByIdentification(employeCreate.getNumeroIdentificacion()).isPresent()) {
                        return ResponseEntity.badRequest().body("Ya existe un empleado con esa identificación");
                }

                if (nombresValid.test(employeCreate.getNombres())) {
                        return ResponseEntity.badRequest().body("No se permite caracteres especiales como ñ o tildes");
                }
                if (nombresValid.test(employeCreate.getPrimerApellido())) {
                        return ResponseEntity.badRequest().body("No se permite caracteres especiales como ñ o tildes");
                }
                if (nombresValid.test(employeCreate.getSegundoApellido())) {
                        return ResponseEntity.badRequest().body("No se permite caracteres especiales como ñ o tildes");
                }
                if (numeroIdentificacion.test(employeCreate.getNumeroIdentificacion())) {
                        return ResponseEntity.badRequest().body("Número de Identificación No Valido");
                }
                var result = this.employeeDAO.create(employeCreate);
                return ResponseEntity.ok(EmployeMostrar
                                .builder()
                                .correo(result.get().getCorreo())
                                .dominio(result.get().getDominio().getDisplayName())
                                .identificacion(result.get().getIdentificacion())
                                .nombres(result.get().getNombres())
                                .primerApellido(result.get().getPrimerApellido())
                                .segundoApellido(result.get().getSegundoApellido())
                                .numeroIdentificacion(result.get().getNumeroIdentificacion())
                                .pais(result.get().getPais())
                                .area(result.get().getArea().getDisplayName())
                                .estado(result.get().getEstado())
                                .build());
        }

        @PutMapping("/update")
        public ResponseEntity<?> updateDataEmployed(@RequestBody EmployeUpdate employeUpdate) {
                var existen = Stream.of(
                                employeUpdate.getIdentificacion(),
                                employeUpdate.getDominio(),
                                employeUpdate.getPais(),
                                employeUpdate.getNumeroIdentificacion()).anyMatch(x -> x == null);
                if (existen) {
                        return ResponseEntity.badRequest().body("Faltan datos");
                }

                var reesult = this.employeeDAO.update(employeUpdate);
                return reesult;
        }

        @PostMapping("/ingreso")
        public ResponseEntity<?> ingreso(@RequestParam String numeroIdentificacion) {
                if (numeroIdentificacion.equals(null)) {
                        return ResponseEntity.badRequest().body("Ingrese un id Valido");
                }
                if (this.employeeDAO.findByIdentification(numeroIdentificacion).isEmpty()) {
                        return ResponseEntity.badRequest().body("El empleado a registrar ingreso no existe");
                }
                var value = employeeDAO.entrada(numeroIdentificacion);

                return value;
        }

        @PostMapping("/salida")
        public ResponseEntity<?> salida(@RequestParam String numeroIdentificacion) {
                if (numeroIdentificacion.equals(null)) {
                        return ResponseEntity.badRequest().body("Ingrese un id Valido");
                }
                if (this.employeeDAO.findByIdentification(numeroIdentificacion).isEmpty()) {
                        return ResponseEntity.badRequest().body("El empleado a registrar ingreso no existe");
                }
                var value = employeeDAO.salida(numeroIdentificacion);

                return value;
        }

        @DeleteMapping("/delete")
        public ResponseEntity<?> eliminarEmpleado(@RequestParam String numeroIdentificacion) {
                return this.employeeDAO.delete(numeroIdentificacion);
        }
}
