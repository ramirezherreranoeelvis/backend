package com.ex.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {
        @Value("${security.jwt.secret}")
        private String key;

        @Value("${security.jwt.issuer}")
        private String issuer;

        @Value("${security.jwt.ttlMillis}")
        private long ttlMillis;

        private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

        /**
         * Create a new token.
         */
        public String create(String id, String subject) {

                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                
                // sign JWT with our ApiKey secret
                byte[] apiKeySecretBytes = Base64.getDecoder().decode(key);
                
                Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

                // set the JWT Claims
                JwtBuilder builder = Jwts.builder()
                                .setId(id)
                                .setIssuedAt(now)
                                .setSubject(subject)
                                .setIssuer(issuer)
                                .signWith(signatureAlgorithm, signingKey);
                                logger.info("A");
                if (ttlMillis >= 0) {
                        long expMillis = nowMillis + ttlMillis;
                        Date exp = new Date(expMillis);
                        builder.setExpiration(exp);
                }
                logger.info("B");
                // Builds the JWT and serializes it to a compact, URL-safe string
                return builder.compact();
        }

        /**
         * Method to validate and read the JWT
         */
        public String getValue(String jwt) {
                // This line will throw an exception if it is not a signed JWS (as
                // expected)
                Claims claims = Jwts.parser()
                                .setSigningKey(Base64.getDecoder().decode(key))
                                .parseClaimsJws(jwt)
                                .getBody();

                return claims.getSubject();
        }

        /**
         * Method to validate and read the JWT
         * 
         */
        public String getKey(String jwt) {
                // This line will throw an exception if it is not a signed JWS (as
                // expected)
                Claims claims = Jwts.parser()
                                .setSigningKey(Base64.getDecoder().decode(key))
                                .parseClaimsJws(jwt).getBody();

                return claims.getId();
        }

}
