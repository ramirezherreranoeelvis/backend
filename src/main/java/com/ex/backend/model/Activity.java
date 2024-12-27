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
@Table(name = "activity")
public class Activity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idIngreso;

        @ManyToOne
        @JoinColumn(name = "idEmployee", nullable = false)
        private Employee employee;

        @Column(name = "entryTime")
        @Temporal(TemporalType.TIMESTAMP)
        private Date entryTime;

        @Column(name = "exitTime")
        @Temporal(TemporalType.TIMESTAMP)
        private Date exitTime;

        @PrePersist
        protected void onCreate() {
                entryTime = new Date();
        }
}
