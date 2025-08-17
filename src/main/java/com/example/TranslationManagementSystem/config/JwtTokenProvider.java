package com.example.TranslationManagementSystem.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //securely generate key for HS256
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now()
                        .plusHours(5)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("Validating token: " + token);
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            System.out.println("Invalid token: " + token);
            ex.printStackTrace();
            return false;
        }
    }
}
