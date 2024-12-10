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
public class EmployeUpdate implements Serializable {

        private String numeroIdentificacion;

        private String pais;

        private String identificacion;

        private String dominio;

}
