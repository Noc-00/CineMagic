package com.cinemagic.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

//Esta clase crea, valida y extrae informacion del token
@Component
public class JwtUtil {

    // Clave secreta firmar y verificar los tokens JWT
    private final Key key;

    //Se inyecta la clave desde application.properties (jwt.secret)
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // La clave debe tener al menos 256 bits = 32 bytes (32 chars si ASCII)
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Metodo que genera el token JWT
    public String generateToken(String username, String role) {
        long expirationMillis = 1000 * 60 * 60 * 24;
        return Jwts.builder()
                .setSubject(username)
                .claim("rol", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Metodo que valida el token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            // logger.warn("Token inválido: " + ex.getMessage());
            return false;
        }
    }

    //Se extrae el nombre de usuario
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //Se extrae su rol
    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("rol", String.class));
    }

    //Metodo para extraer X claim del token
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}