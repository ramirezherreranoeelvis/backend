package com.ex.backend.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ex.backend.dao.IEmployeeDAO;
import com.ex.backend.dto.EmployeCreateDTO;
import com.ex.backend.dto.EmployeDisplayDTO;
import com.ex.backend.message.Message;
import com.ex.backend.model.Employee;
import com.ex.backend.model.enums.Country;
import com.ex.backend.model.enums.Departament;
import com.ex.backend.model.enums.Domain;
import com.ex.backend.model.enums.Identification;
import com.ex.backend.repository.IEmployeRepository;
import com.ex.backend.repository.IEntryRepository;
import com.ex.backend.model.Activity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeService implements IEmployeeDAO {

        @Autowired
        private IEmployeRepository employeRepository;
        @Autowired
        private IEntryRepository entryRepository;

        @Override
        public ResponseEntity<?> create(EmployeCreateDTO employeCreate) {
                try {
                        var country = parseEnum(Country.class, employeCreate.getCountry(), "País no válido");
                        var domain = parseEnum(Domain.class, employeCreate.getDomain(), "Dominio no válido");
                        var department = parseEnum(Departament.class, employeCreate.getDepartment(), "Área no válida");
                        var identification = parseEnum(Identification.class,
                                        employeCreate.getIdentification(), "Identificación no válida");
                        logger.info(identification.toString());
                        String correo = this.generacionCorreo(
                                        employeCreate.getNames(),
                                        employeCreate.getFirstSurname(),
                                        domain.getDisplayName());

                        this.employeRepository.save(Employee.builder()
                                        .identificationNumber(employeCreate.getIdentificationNumber())
                                        .email(correo)
                                        .names(employeCreate.getNames())
                                        .firstSurname(employeCreate.getFirstSurname())
                                        .secondSurname(employeCreate.getSecondSurname())
                                        .country(country)
                                        .identification(identification)
                                        .domain(domain)
                                        .department(department)
                                        .build());
                        return ResponseEntity.ok(Message.message("Empleado creado con exito"));
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.internalServerError().body("Error al crear empleado");
                }

        }

        @Override
        public ResponseEntity<?> update(EmployeCreateDTO employeUpdate, String numeroIdentificacion) {
                try {
                        var employe = this.findByIdentification(numeroIdentificacion).get();
                        // valid Enums
                        var country = parseEnum(Country.class, employeUpdate.getCountry(), "País no válido");
                        var domain = parseEnum(Domain.class, employeUpdate.getDomain(), "Dominio no válido");
                        var department = parseEnum(Departament.class, employeUpdate.getDepartment(), "Área no válida");
                        var identification = parseEnum(Identification.class, employeUpdate.getIdentification(),
                                        "Identificación no válida");
                        //
                        if (!employe.getNames().equals(employeUpdate.getNames()) || !employe
                                        .getFirstSurname().equals(employeUpdate.getFirstSurname())) {
                                var correo = this.generacionCorreo(employeUpdate.getNames(),
                                                employeUpdate.getFirstSurname(),
                                                domain.getDisplayName());
                                employe.setEmail(correo);
                        }
                        employe.setIdentificationNumber(employeUpdate.getIdentificationNumber());
                        employe.setEditDate(new Date());
                        employe.setNames(employeUpdate.getNames());
                        employe.setFirstSurname(employeUpdate.getFirstSurname());
                        employe.setSecondSurname(employeUpdate.getSecondSurname());
                        employe.setCountry(country);
                        employe.setIdentification(identification);
                        employe.setDomain(domain);
                        employe.setDepartment(department);
                        this.employeRepository.save(employe);
                        return ResponseEntity.ok(Message.message("Se actualizo Exitosamente"));

                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.internalServerError().body("Error al actualizar empleado");
                }

        }

        @Override
        public ResponseEntity<?> salida(String numeroIdentificacion) {
                var salidaFaltante = this.entryRepository.findAll().stream()
                                .filter(e -> e.getEmployee().getIdentificationNumber().equals(numeroIdentificacion))
                                .filter(e -> e.getExitTime() == null).findFirst();
                if (salidaFaltante.isEmpty()) {
                        return ResponseEntity.badRequest().body("No hay un ingreso para registrar su salida");
                }
                salidaFaltante.get().setExitTime(new Date());

                return this.entryRepository.save(salidaFaltante.get()) == null
                                ? ResponseEntity.badRequest().body("No se pudo registrar salida")
                                : ResponseEntity.ok(Message.message("Se regisgro la salida correctamente "));
        }

        @Override
        public ResponseEntity<?> entrada(String numeroIdentificacion) {

                var salidaFaltante = this.entryRepository.findAll().stream()
                                .filter(e -> e.getEmployee().getIdentificationNumber().equals(numeroIdentificacion))
                                .filter(e -> e.getExitTime() == null).findFirst();
                if (salidaFaltante.isPresent()) {
                        return ResponseEntity.badRequest().body("Este usuario aun tiene una salida faltante");
                }
                this.entryRepository.save(
                                Activity.builder().employee(findByIdentification(numeroIdentificacion).get()).build());
                return ResponseEntity.ok().body(Message.message("Se registro el ingreso correctamente"));
        }

        @Override
        public List<EmployeDisplayDTO> findEmployess() {
                return this.employeRepository.findAll().stream().map(employee -> EmployeDisplayDTO
                                .builder()
                                .email(employee.getEmail())
                                .domain(employee.getDomain().getDisplayName())
                                .identification(employee.getIdentification())
                                .names(employee.getNames())
                                .firstSurname(employee.getFirstSurname())
                                .secondSurname(employee.getSecondSurname())
                                .identificationNumber(employee.getIdentificationNumber())
                                .country(employee.getCountry())
                                .status(employee.getStatus())
                                .department(employee.getDepartment().getDisplayName())
                                .build()).toList();
        }

        @Override
        public Optional<Employee> findByIdentification(String numeroIdentificacion) {
                return this.employeRepository.findAll().stream()
                                .filter(employe -> employe.getIdentificationNumber().equals(numeroIdentificacion))
                                .findFirst();
        }

        @Override
        public ResponseEntity<?> delete(String numeroIdentificacion) {
                var employeOptional = findByIdentification(numeroIdentificacion);
                logger.info("entro el coso este");
                if (employeOptional.isEmpty()) {
                        return ResponseEntity.badRequest().body("No existe empleado para eliminar");
                }
                // ELiminar ingresos y salidas
                var ingresos = this.entryRepository.findAll().stream()
                                .filter(e -> e.getEmployee().getIdentificationNumber().equals(numeroIdentificacion))
                                .toList();
                ingresos.forEach(ingreso -> this.entryRepository.delete(ingreso));
                this.employeRepository.delete(employeOptional.get());
                logger.info("elimino");
                return ResponseEntity.ok(Message.message("El Empleado a sido eliminado exitosamente"));
        }

        @Override
        public boolean verifyEmployeeExistence(String identificationNumber) {
                return this.findByIdentification(identificationNumber).isPresent();
        }

        /*
         * @param identificationNumber: numero de identificacion del empleado que vamos
         * a cambiar
         * 
         * @param numeroIdentificactionExcept: numero de identificacion del empleado que
         * vamos a excluir
         */
        @Override
        public boolean verifyEmployeeExistence(String identificationNumber, String identificationNumberExcept) {
                return this.employeRepository.findAll().stream()
                                .filter(employee -> !employee.getIdentificationNumber()
                                                .equals(identificationNumberExcept))
                                .filter(employee -> employee.getIdentificationNumber().equals(identificationNumber))
                                .findFirst().isPresent();
        }

        private String generacionCorreo(String nombres, String primerApellido, String dominio) {
                var primerNombre = nombres.split(" ")[0];
                var empleados = this.employeRepository.findAll().stream()
                                .filter(empleado -> empleado.getFirstSurname()
                                                .equals(primerApellido)
                                                && empleado.getNames().split(" ")[0].equals(primerNombre))
                                .toList();

                var identifier = empleados;
                var correo = primerNombre + "." + primerApellido + "."
                                + (identifier.size() == 0 ? "" : identifier.get(identifier.size() - 1).getIdEmployee())
                                + "@" + dominio;
                return correo;
        }

        private <T extends Enum<T>> T parseEnum(Class<T> enumType, String value, String errorMessage) {
                try {
                        return Enum.valueOf(enumType, value.toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e) {
                        throw new IllegalArgumentException(errorMessage);
                }
        }

        private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

}
