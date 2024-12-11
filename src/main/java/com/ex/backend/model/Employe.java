package com.ex.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employe {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idEmploye;

        @Column(name = "numeroIdentiicacion", nullable = false, unique = true, length = 20)
        private String numeroIdentificacion;

        @Column(name = "correo", nullable = false, unique = true, length = 300)
        private String correo;

        @Column(name = "nombres", nullable = false)
        private String nombres;

        @Column(name = "primerApellido", nullable = false)
        private String primerApellido;

        @Column(name = "segundoApellido", nullable = false)
        private String segundoApellido;

        @Column(name = "pais", columnDefinition = "VARCHAR(255)")
        @Enumerated(EnumType.STRING)
        private Pais pais;

        @Column(name = "identificacion", columnDefinition = "VARCHAR(50)")
        @Enumerated(EnumType.STRING)
        private Identificacion identificacion;

        @Column(name = "dominio", columnDefinition = "VARCHAR(20)")
        @Enumerated(EnumType.STRING)
        private Dominio dominio;

        @Column(name = "fechaRegistro")
        @Temporal(TemporalType.TIMESTAMP)
        private Date fechaRegistro;

        @Column(name = "fechaEdicion")
        @Temporal(TemporalType.TIMESTAMP)
        private Date fechaEdicion;

        @Column(name = "Area", columnDefinition = "VARCHAR(60)")
        @Enumerated(EnumType.STRING)
        private Areas area;

        @Column(name = "Estado")
        private String estado;

        @PrePersist
        protected void onCreate() {
                fechaRegistro = new Date();
                estado = "activo";
        }
}
