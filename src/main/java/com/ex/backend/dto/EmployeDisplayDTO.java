package com.ex.backend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.ex.backend.model.enums.Country;
import com.ex.backend.model.enums.Identification;

import lombok.AllArgsConstructor;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDisplayDTO {

        private String identificationNumber;

        private String email;

        private String names;

        private String firstSurname;

        private String secondSurname;

        private Country country;

        private Identification identification;

        private String domain;

        private String department;
        
        private String status;
}
