package com.oracle.football.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(String subject){
        return generateToken(subject, Map.of());
    }

    public String generateToken(String subject, String ...scopes){
        return generateToken(subject, Map.of("scopes", scopes));
    }

    public String generateToken(String subject, List<String> scopes) {
        return generateToken(subject, Map.of("scopes", scopes));
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .claims()
                .subject(subject)
                .issuer("football-oracle")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(validityInMilliseconds)))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    public boolean isTokenExpired(String jwt) {
        Date now = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(now);
    }

}
