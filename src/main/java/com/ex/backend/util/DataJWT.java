package com.ex.backend.util;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

import io.jsonwebtoken.SignatureAlgorithm;

@Data
@Builder
public class DataJWT {
        private int id;
        private Date now;
        private String email;
        private String issuer;
        private Date expiration;
        private SignatureAlgorithm signatureAlgorithm;
        
}
