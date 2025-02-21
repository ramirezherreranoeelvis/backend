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

                if (ttlMillis >= 0) {
                        long expMillis = nowMillis + ttlMillis;
                        Date exp = new Date(expMillis);
                        builder.setExpiration(exp);
                }

                // Builds the JWT and serializes it to a compact, URL-safe string
                return builder.compact();
        }

        public Claims getValue(String jwt) {
                try {
                        Claims claims = parseToken(jwt);
                        return claims;
                } catch (Exception e) {
                        logger.info("Error al validar el token: {}" + e.getMessage());
                        throw new RuntimeException("Token inválido", e);
                }
        }

        public String getKey(String jwt) {
                try {
                        Claims claims = parseToken(jwt);
                        return claims.getId();
                } catch (Exception e) {
                        logger.info("Error al validar el token: {}" + e.getMessage());
                        throw new RuntimeException("Token inválido", e);
                }
        }

        private Claims parseToken(String jwt) {
                return Jwts.parser()
                                .setSigningKey(Base64.getDecoder().decode(key))
                                .parseClaimsJws(jwt)
                                .getBody();
        }

}
