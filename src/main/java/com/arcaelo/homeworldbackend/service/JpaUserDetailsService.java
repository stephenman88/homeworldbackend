package com.arcaelo.homeworldbackend.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arcaelo.homeworldbackend.model.Player;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService{
    private final PlayerRepository playerRepo;

    public JpaUserDetailsService(PlayerRepository playerRepository){
        playerRepo = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Player player = playerRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("JpaUserDetailsService.java loadUserByUsername: " + email + " not found."));

        return User.builder()
            .username(player.getEmail())
            .password(player.getPassword())
            .authorities(player.getRoles().stream().map(SimpleGrantedAuthority::new).toList())
            .build();
    }
}