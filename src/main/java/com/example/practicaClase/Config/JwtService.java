package com.example.practicaClase.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {

    private final String secretKey = "pQ1MqycZ7bqZcO3Gx0HDVjNyrI8AmdWCuqPg8T8D+cM=";
    private final long expirationTime = 86400000; // 24 hours in milliseconds

    public String generateToken(String mail, String role) {
        return Jwts.builder()
                .setSubject(mail)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String mail) {
        return (mail.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public Authentication getAuthentication(String token) {
        String mail = extractUsername(token);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                mail, "", Collections.singleton(new SimpleGrantedAuthority("USER")));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}

