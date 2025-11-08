package com.arcaelo.homeworldbackend.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenUtil {
    @Value("${jwtKey}")
    private String keyString;
    private SecretKey key;
    @Value("${jwtExpire}")
    private int expir;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String playerId){
        String jwt = Jwts.builder()
            .subject(playerId)
            .signWith(key, Jwts.SIG.HS512)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expir))
            .compact();
        
        return jwt;
    }

    public String parseToken(String token){
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
