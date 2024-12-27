package com.ex.backend.model;

/**
 *
 * @author Gatomontes
 */
public enum Domain {

        USA("global.com.us"),
        COLOMBIA("global.com.co");

        private final String displayName;

        Domain(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }
}
