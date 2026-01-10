package com.arcaelo.homeworldbackend.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.annotation.PostConstruct;

@Component
public class JwtBlacklist {
    @Value("${jwtExpire}")
    private int expir;
    private Cache<String, Boolean> tokenBlacklist;

    @PostConstruct
    public void init(){
        tokenBlacklist = Caffeine.newBuilder()
            .expireAfterWrite(expir, TimeUnit.MILLISECONDS)
            .build();
    }

    public void add(String token){
        tokenBlacklist.put(token, true);
    }

    public boolean isRevoked(String token){
        return tokenBlacklist.getIfPresent(token) != null;
        
    }
}
