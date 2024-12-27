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
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idEmployee;

        @Column(name = "identificationNumber", nullable = false, unique = true, length = 20)
        private String identificationNumber;

        @Column(name = "email", nullable = false, unique = true, length = 300)
        private String email;

        @Column(name = "names", nullable = false)
        private String names;

        @Column(name = "firstSurname", nullable = false)
        private String firstSurname;

        @Column(name = "secondSurname", nullable = false)
        private String secondSurname;

        @Column(name = "country", columnDefinition = "VARCHAR(255)")
        @Enumerated(EnumType.STRING)
        private Country country;

        @Column(name = "identification", columnDefinition = "VARCHAR(50)")
        @Enumerated(EnumType.STRING)
        private Identification identification;

        @Column(name = "domain", columnDefinition = "VARCHAR(20)")
        @Enumerated(EnumType.STRING)
        private Domain domain;

        @Column(name = "registrationDate")
        @Temporal(TemporalType.TIMESTAMP)
        private Date registrationDate;

        @Column(name = "editDate")
        @Temporal(TemporalType.TIMESTAMP)
        private Date editDate;

        @Column(name = "department", columnDefinition = "VARCHAR(60)")
        @Enumerated(EnumType.STRING)
        private Departament department;

        @Column(name = "status")
        private String status;

        @PrePersist
        protected void onCreate() {
                registrationDate = new Date();
                status = "activo";
        }
}
