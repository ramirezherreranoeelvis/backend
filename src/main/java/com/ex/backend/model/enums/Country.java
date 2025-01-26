package com.ex.backend.model.enums;

/**
 *
 * @author Gatomontes
 */
public enum Country {

        USA("USA"),
        COLOMBIA("COLOMBIA");

        private final String displayName;

        Country(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
