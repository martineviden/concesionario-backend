package com.atos.concesionario.proyecto_concesionario.Jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtUtils {

    // Clave secreta leída desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Duración del token: 24 horas
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    // Generar token JWT con email y roles
    public String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(nowMillis + EXPIRATION_MS);

        return Jwts.builder()
                .subject(email)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token y obtener el email (subject)
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Obtener lista de roles desde los claims
    public List<String> getRolesFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("roles", List.class);
    }

    // Verificar si el token es válido
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Interno: Convertir la clave secreta a Key segura
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}