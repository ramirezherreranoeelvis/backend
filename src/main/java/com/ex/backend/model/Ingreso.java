package com.ex.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ingreso")
public class Ingreso {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idIngreso;

        @ManyToOne
        @JoinColumn(name = "idEmploye", nullable = false)
        private Employe employe;

        @Column(name = "ingreso")
        @Temporal(TemporalType.TIMESTAMP)
        private Date timeEntry;

        @Column(name = "salida")
        @Temporal(TemporalType.TIMESTAMP)
        private Date timeExit;

        @PrePersist
        protected void onCreate() {
                timeEntry = new Date();
        }
}
