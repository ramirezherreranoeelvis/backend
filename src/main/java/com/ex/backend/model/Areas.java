package com.ex.backend.model;

/**
 *
 * @author Gatomontes
 */
public enum Areas {

        ADMINISTRACION("Administración"),
        FINANCIERA("Financiera"),
        COMPRAS("Compras"),
        INFRAESTRUCTURA("Infraestructura"),
        OPERACION("Operación"),
        TALENTO_HUMANO("Talento Humano"),
        SERVICIO_VARIOS("Servicio Varios");

        private final String displayName;

        Areas(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
