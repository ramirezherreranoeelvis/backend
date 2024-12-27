package com.ex.backend.employe.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeCreate implements Serializable{

        private String identificationNumber;

        private String names;

        private String firstSurname;

        private String secondSurname;

        private String country;

        private String identification;

        private String domain;

        private String department;

}
