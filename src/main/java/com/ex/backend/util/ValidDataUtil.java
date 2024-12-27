package com.ex.backend.util;

public class ValidDataUtil {
        public static boolean namesValid(String name) {
                if (name == null || name.isEmpty()) {
                        return false;
                }
                if (!name.matches("[a-zA-Z]+")) {
                        return false;
                }
                return true;
        }

        public static boolean identificationNumberValid(String identificationNumber) {
                if (identificationNumber == null || identificationNumber.isEmpty()) {
                        return false;
                }
                if (identificationNumber.length() > 20) {
                        return false;
                }
                if (!identificationNumber.matches("[a-zA-Z0-9]+")) {
                        return false;
                }
                return true;
        }

}
