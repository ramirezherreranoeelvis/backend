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
import com.ex.backend.model.Areas;
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
                logger.info(employeCreate.getDominio().toUpperCase());
                Dominio dominio = Dominio.valueOf(employeCreate.getDominio().toUpperCase());
                if (dominio == null) {
                        return null;
                }
                Areas area = Areas.valueOf(employeCreate.getArea().toUpperCase());
                if (area == null) {
                        return null;
                }
                var correo = this.generacionCorreo(employeCreate.getNombres(), employeCreate.getPrimerApellido(),
                                dominio.getDisplayName());

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
                                .area(area)
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

                Dominio dominio = Dominio.valueOf(employeUpdate.getDominio());

                Areas area = Areas.valueOf(employeUpdate.getArea().toUpperCase());
                if (area == null) {
                        return null;
                }

                if (!employeOptional.get().getNombres().equals(employeUpdate.getNombres()) || !employeOptional.get()
                                .getPrimerApellido().equals(employeUpdate.getPrimerApellido())) {
                        var correo = this.generacionCorreo(employeUpdate.getNombres(),
                                        employeUpdate.getPrimerApellido(),
                                        dominio.getDisplayName());
                        employe.setCorreo(correo);
                }

                employe.setNumeroIdentificacion(employeUpdate.getNumeroIdentificacion());
                employe.setFechaEdicion(new Date());
                employe.setNombres(employeUpdate.getNombres());
                employe.setPrimerApellido(employeUpdate.getPrimerApellido());
                employe.setSegundoApellido(employeUpdate.getSegundoApellido());
                employe.setPais(pais);
                employe.setIdentificacion(identificacion);
                employe.setDominio(dominio);
                employe.setArea(area);
                this.employeRepository.save(employe);
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
                                .dominio(employee.getDominio().getDisplayName())
                                .identificacion(employee.getIdentificacion())
                                .nombres(employee.getNombres())
                                .primerApellido(employee.getPrimerApellido())
                                .segundoApellido(employee.getSegundoApellido())
                                .numeroIdentificacion(employee.getNumeroIdentificacion())
                                .pais(employee.getPais())
                                .estado(employee.getEstado())
                                .area(employee.getArea().getDisplayName())
                                .build()).toList();
        }

        @Override
        public Optional<Employe> findByIdentification(String numeroIdentificacion) {
                return this.employeRepository.findAll().stream()
                                .filter(employe -> employe.getNumeroIdentificacion().equals(numeroIdentificacion))
                                .findFirst();
        }

        @Override
        public ResponseEntity<?> delete(String numeroIdentificacion) {
                var employeOptional = findByIdentification(numeroIdentificacion);
                logger.info("entro el coso este");
                if (employeOptional.isEmpty()) {
                        return ResponseEntity.badRequest().body("No existe empleado para eliminar");
                }
                this.employeRepository.delete(employeOptional.get());
                logger.info("elimino");
                return ResponseEntity.ok(Message.Message("El Empleado a sido eliminado exitosamente"));
        }

        public String generacionCorreo(String nombres, String primerApellido, String dominio) {
                var primerNombre = nombres.split(" ")[0];
                var empleados = this.employeRepository.findAll().stream()
                                .filter(empleado -> empleado.getPrimerApellido()
                                                .equals(primerApellido)
                                                && empleado.getNombres().split(" ")[0].equals(primerNombre))
                                .toList();

                var identifier = empleados;
                var correo = primerNombre + "." + primerApellido + "."
                                + (identifier.size() == 0 ? "" : identifier.get(identifier.size() - 1).getIdEmploye())
                                + "@" + dominio;
                return correo;
        }

        private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

}
