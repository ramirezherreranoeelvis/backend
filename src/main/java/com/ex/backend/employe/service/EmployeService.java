package com.ex.backend.employe.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ex.backend.employe.dao.IEmployeeDAO;
import com.ex.backend.employe.dto.EmployeCreate;
import com.ex.backend.employe.dto.EmployeMostrar;
import com.ex.backend.employe.dto.EmployeUpdate;
import com.ex.backend.employe.repository.IEmployeRepository;
import com.ex.backend.employe.repository.IEntryRepository;
import com.ex.backend.message.Message;
import com.ex.backend.model.Dominio;
import com.ex.backend.model.Employe;
import com.ex.backend.model.Identificacion;
import com.ex.backend.model.Ingreso;
import com.ex.backend.model.Pais;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeService implements IEmployeeDAO {

        @Autowired
        private IEmployeRepository employeRepository;
        @Autowired
        private IEntryRepository entryRepository;

        @Override
        public Optional<Employe> create(EmployeCreate employeCreate) {
                Pais pais = Pais.valueOf(employeCreate.getPais().toUpperCase());
                if (pais == null) {
                        return null;
                }
                Dominio dominio = Dominio.valueOf(pais.getDisplayName());

                var primerNombre = employeCreate.getNombres().split(" ")[0];

                var empleados = this.employeRepository.findAll().stream()
                                .filter(empleado -> empleado.getPrimerApellido()
                                                .equals(employeCreate.getPrimerApellido())
                                                && empleado.getNombres().split(" ")[0].equals(primerNombre))
                                .toList();

                var identifier = empleados;
                var correo = primerNombre + "." + employeCreate.getPrimerApellido() + "."
                                + (identifier.size() == 0 ? "" : identifier.get(identifier.size() - 1).getIdEmploye())
                                + "@" + dominio;

                var identificacion = Identificacion.valueOf(employeCreate.getIdentificacion().toUpperCase());
                if (identificacion == null) {
                        return null;
                }
                return Optional.of(this.employeRepository.save(Employe.builder()
                                .numeroIdentificacion(employeCreate.getNumeroIdentificacion())
                                .correo(correo)
                                .nombres(employeCreate.getNombres())
                                .primerApellido(employeCreate.getPrimerApellido())
                                .segundoApellido(employeCreate.getSegundoApellido())
                                .pais(pais)
                                .identificacion(identificacion)
                                .dominio(dominio)
                                .build()));
        }

        @Override
        public ResponseEntity<?> update(EmployeUpdate employeUpdate) {
                var employeOptional = this.employeRepository.findAll().stream().filter(
                                e -> e.getNumeroIdentificacion().equals(employeUpdate.getNumeroIdentificacion()))
                                .findFirst();
                if (employeOptional.isEmpty()) {
                        return ResponseEntity.badRequest().body("Al parecer no existe el empleado");
                }
                var employe = employeOptional.get();
                var pais = Pais.valueOf(employeUpdate.getPais().toUpperCase());
                var identificacion = Identificacion.valueOf(employeUpdate.getIdentificacion().toUpperCase());
                Dominio dominio = Dominio.valueOf(pais.getDisplayName());
                employe.setPais(pais);
                employe.setIdentificacion(identificacion);
                employe.setDominio(dominio);
                return ResponseEntity.ok(Message.Message("Se actualizo Exitosamente"));
        }

        @Override
        public ResponseEntity<?> salida(String numeroIdentificacion) {
                var salidaFaltante = this.entryRepository.findAll().stream()
                                .filter(e -> e.getEmploye().getNumeroIdentificacion().equals(numeroIdentificacion))
                                .filter(e -> e.getTimeExit() == null).findFirst();
                if (salidaFaltante.isEmpty()) {
                        return ResponseEntity.badRequest().body("No hay un ingreso para registrar su salida");
                }
                salidaFaltante.get().setTimeExit(new Date());

                return this.entryRepository.save(salidaFaltante.get()) == null
                                ? ResponseEntity.badRequest().body("No se pudo registrar salida")
                                : ResponseEntity.ok().body(Message.Message("Se regisgro la salida correctamente "));
        }

        @Override
        public ResponseEntity<?> entrada(String numeroIdentificacion) {

                var salidaFaltante = this.entryRepository.findAll().stream()
                                .filter(e -> e.getEmploye().getNumeroIdentificacion().equals(numeroIdentificacion))
                                .filter(e -> e.getTimeExit() == null).findFirst();
                if (salidaFaltante.isPresent()) {
                        return ResponseEntity.badRequest().body("Este usuario aun tiene una salida faltante");
                }
                this.entryRepository.save(
                                Ingreso.builder().employe(findByIdentification(numeroIdentificacion).get()).build());
                return ResponseEntity.ok().body(Message.Message("Se registro el ingreso correctamente"));
        }

        @Override
        public List<EmployeMostrar> findEmployess() {
                return this.employeRepository.findAll().stream().map(employee -> EmployeMostrar
                                .builder()
                                .correo(employee.getCorreo())
                                .dominio(employee.getDominio())
                                .identificacion(employee.getIdentificacion())
                                .nombres(employee.getNombres())
                                .primerApellido(employee.getPrimerApellido())
                                .segundoApellido(employee.getSegundoApellido())
                                .numeroIdentificacion(employee.getNumeroIdentificacion())
                                .pais(employee.getPais())
                                .build()).toList();
        }

        @Override
        public Optional<Employe> findByIdentification(String numeroIdentificacion) {
                return this.employeRepository.findAll().stream()
                                .filter(employe -> employe.getNumeroIdentificacion().equals(numeroIdentificacion))
                                .findFirst();
        }

}
