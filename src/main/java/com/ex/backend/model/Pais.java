/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ex.backend.model;

/**
 *
 * @author Gatomontes
 */
public enum Pais {

        USA("USA"),
        COLOMBIA("COLOMBIA");

        private final String displayName;

        Pais(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
