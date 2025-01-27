package com.example.flightplanning.security;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtSigning1234567890";

    private static final long EXPIRATION_TIME = 86400000; // 1 gün (ms cinsinden)

    /**
     * Kullanıcı adı ile JWT token üretir.
     */
    public String generateToken(String username) {
        try {
            JwtClaims claims = new JwtClaims();
            claims.setSubject(username);
            claims.setIssuedAtToNow();
            claims.setExpirationTimeMinutesInTheFuture(EXPIRATION_TIME / (1000 * 60)); // 1 gün

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
            jws.setKey(new org.jose4j.keys.HmacKey(SECRET_KEY.getBytes()));
            jws.setDoKeyValidation(false); // Key uzunluğunu manuel doğrulamayı kapat

            return jws.getCompactSerialization();
        } catch (Exception e) {
            throw new RuntimeException("JWT token üretim hatası: " + e.getMessage(), e);
        }
    }

    /**
     * JWT token'dan kullanıcı adını çözümler.
     */
    public String getUsernameFromToken(String token) throws MalformedClaimException {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Token geçerliliğini kontrol eder.
     */
    public boolean validateToken(String token) {
        getClaimsFromToken(token);
        return true;
    }

    /**
     * Token'dan Claims objesi döner.
     */
    private JwtClaims getClaimsFromToken(String token) {
        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setVerificationKey(new org.jose4j.keys.HmacKey(SECRET_KEY.getBytes()))
                    .build();

            return jwtConsumer.processToClaims(token);
        } catch (InvalidJwtException e) {
            throw new RuntimeException("JWT doğrulama hatası: " + e.getMessage(), e);
        }
    }
}
