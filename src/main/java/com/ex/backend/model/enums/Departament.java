package com.ex.backend.model.enums;

/**
 *
 * @author Gatomontes
 */
public enum Departament {

        ADMINISTRACION("Administración"),
        FINANCIERA("Financiera"),
        COMPRAS("Compras"),
        INFRAESTRUCTURA("Infraestructura"),
        OPERACION("Operación"),
        TALENTO_HUMANO("Talento Humano"),
        SERVICIO_VARIOS("Servicio Varios");

        private final String displayName;

        Departament(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
