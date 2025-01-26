package com.ex.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenUtils {

        private final static String ACCES_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvX2lkIiwibmFtZSI6Ikp1YW4gUGVyZXoiLCJpYXQiOjE2NzcwNzQyODcsImV4cCI6MTY3NzA3Nzg4N30.YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXo0NTY3ODk\r\n" + //;
        private final static String REFRESH_TOKENSECRET = "";
        private final static String ISSUER = "ex";
        private final static String ACCES_TOKEN_PREFIX = "Bearer ";
        private final static String REFRESH_TOKEN_PREFIX = "Bearer ";
        private final static long EXPIRATION = 3600000L; // 1 hour
        private final static long REFRESH_EXPIRATION = 604800000L; // 1 week
        private final static String ACCES_TOKEN_HEADER = "Authorization";
        private final static String REFRESH_TOKEN_HEADER = "Refresh";
        private final static String ACCES_TOKEN_ISSUER = "ex";
        private final static String REFRESH_TOKEN_ISSUER = "ex";
        private final static String ACCES_TOKEN_AUDIENCE = "ex";
        private final static String REFRESH_TOKEN_AUDIENCE = "ex";
        private final static String ACCES_TOKEN_JTI = "ex";
        private final static String REFRESH_TOKEN_JTI = "ex";
        private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);
        private static final String ACCES_TOKEN_TYPE = "access_token";
        private static final String REFRESH_TOKEN_TYPE = "refresh_token";
        private static final String ACCES_TOKEN_SCOPE = "ex";
        private static final String REFRESH_TOKEN_SCOPE = "ex";
        private static final String ACCES_TOKEN_ALGORITHM = "HS256";
        private static final String REFRESH_TOKEN_ALGORITHM = "HS256";
        private static final String ACCES_TOKEN_SUBJECT = "ex";
        private static final String REFRESH_TOKEN_SUBJECT = "ex";

        public static String createToken(String name, String email) {
                return null;
        }

}
