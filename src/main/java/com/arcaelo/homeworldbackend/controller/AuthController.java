package com.arcaelo.homeworldbackend.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arcaelo.homeworldbackend.model.Deck;
import com.arcaelo.homeworldbackend.model.Player;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;
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
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtBlacklist jwtBlacklist, PlayerRepository playerRepository, PasswordEncoder passwordEncoder){
        authManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtBlacklist = jwtBlacklist;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        Authentication authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        Authentication authResult = authManager.authenticate(authRequest);
        String token = jwtTokenUtil.generateToken(authResult.getName());
        return new LoginResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        if(registerRequest.name().length() < 1){
            return ResponseEntity.badRequest().body("Display name required");
        }
        try{
            System.out.println("AuthController: " + registerRequest.email());
            Optional<Player> playerOpt = playerRepository.findByEmail(registerRequest.email());
            if(playerOpt.isEmpty()){
                Player newUser = new Player();
                newUser.setEmail(registerRequest.email());
                newUser.setName(registerRequest.name());
                newUser.setDecks(new HashSet<Deck>());
                newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
                newUser.setRoles(Set.of("USER"));
                playerRepository.save(newUser);
                return ResponseEntity.ok().build();
            }else{
                System.out.println("AuthController: " + playerOpt.get());
                return ResponseEntity.badRequest().body("Email already registered");
            }
        }catch(Exception e){
            System.out.println("AuthController: " + e);
            return ResponseEntity.badRequest().body("Unknown error");
        }
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

    private record LoginRequest(String email, String password){}
    private record RegisterRequest(String email, String name, String password){}
    private record LoginResponse(String token){}
}
