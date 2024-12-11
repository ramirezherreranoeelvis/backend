package com.ex.backend.employe.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.ex.backend.model.Identificacion;
import com.ex.backend.model.Pais;

import lombok.AllArgsConstructor;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeMostrar {

        private String numeroIdentificacion;

        private String correo;

        private String nombres;

        private String primerApellido;

        private String segundoApellido;

        private Pais pais;

        private Identificacion identificacion;

        private String dominio;

        private String area;
        private String estado;
}
