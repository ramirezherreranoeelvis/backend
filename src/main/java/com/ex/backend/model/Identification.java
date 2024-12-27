/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ex.backend.model;

/**
 *
 * @author Gatomontes
 */
public enum Identification {

        CEDULA_DE_CIUDADANIA("cedula de ciudadania"),
        CEDULA_EXTRANJERA("cedula extranjera"),
        PASAPORTE("pasaporte"),
        PERMISO_ESPECIAL("permiso especial");

        private final String displayName;

        Identification(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
