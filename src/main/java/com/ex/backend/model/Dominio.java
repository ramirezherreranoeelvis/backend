package com.ex.backend.model;

/**
 *
 * @author Gatomontes
 */
public enum Dominio {

        USA("global.com.us"),
        COLOMBIA("global.com.co");

        private final String displayName;

        Dominio(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
