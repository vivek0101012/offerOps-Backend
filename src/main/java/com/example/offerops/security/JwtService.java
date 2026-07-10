package com.example.offerops.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {


    @Value("${jwt.expiry}")
    private  int jwtexpiry;

    @Value("${jwt.secret}")
    private  String secret;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(Long Id, String email) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(email)
                .claim("userId",Id)
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(jwtexpiry)))
                .claim("role", "USER")
                .signWith(getSigningKey())
                .compact();
    }


    public Claims parseClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token) {

        return parseClaims(token)
                .getSubject();
    }
    public Date extractExpiration(String token){

        return parseClaims(token)
                .getExpiration();
    }

    public boolean isTokenExpired(String token){

        return extractExpiration(token)
                .before(new Date());
    }
    public boolean isTokenValid(
            String token){

        String username =
                extractUsername(token);

        return !isTokenExpired(token);
    }

    public Long extractuserId(String token){
        return  parseClaims(token).get("userId",Long.class);
    }

}
