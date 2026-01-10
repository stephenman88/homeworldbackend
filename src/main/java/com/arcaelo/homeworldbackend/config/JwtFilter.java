package com.arcaelo.homeworldbackend.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arcaelo.homeworldbackend.service.JpaUserDetailsService;
import com.arcaelo.homeworldbackend.util.JwtBlacklist;
import com.arcaelo.homeworldbackend.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtBlacklist jwtBlacklist;

    public JwtFilter(JwtTokenUtil jwtTokenUtil, JpaUserDetailsService jpaUserDetailsService, JwtBlacklist jwtBlacklist){
        this.jwtTokenUtil = jwtTokenUtil;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.jwtBlacklist = jwtBlacklist;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            try{
                if(jwtBlacklist.isRevoked(token)){throw new Exception("token blacklisted");};
                String email = jwtTokenUtil.parseToken(token);
                UserDetails user = jpaUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }catch(Exception e){
                System.out.println("JwtFilter: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
