package com.arcaelo.homeworldbackend.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arcaelo.homeworldbackend.util.JwtBlacklist;
import com.arcaelo.homeworldbackend.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtBlacklist jwtBlacklist;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtBlacklist jwtBlacklist){
        authManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtBlacklist = jwtBlacklist;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        Authentication authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authResult = authManager.authenticate(authRequest);
        String token = jwtTokenUtil.generateToken(authResult.getName());
        return new LoginResponse(token);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        final String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            try{
                jwtBlacklist.add(token);
                request.getSession().invalidate();
                SecurityContextHolder.clearContext();
            }catch(Exception e){
                System.out.println("JwtFilter: " + e.getMessage());
            }
        }
    }

    private record LoginRequest(String username, String password){}
    private record LoginResponse(String token){}
}
