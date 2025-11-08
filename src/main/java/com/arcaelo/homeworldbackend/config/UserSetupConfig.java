package com.arcaelo.homeworldbackend.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arcaelo.homeworldbackend.model.Deck;
import com.arcaelo.homeworldbackend.model.Player;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Configuration
public class UserSetupConfig {
    @Value("${superAdmin}")
    private String adminEmail;
    @Value("${superPassword}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initUsers(PlayerRepository repo, PasswordEncoder passwordEncoder){
        return args -> {
            if (repo.findByEmail(adminEmail).isEmpty()) {
                Player admin = new Player();
                admin.setEmail(adminEmail);
                admin.setDecks(new HashSet<Deck>());
                admin.setName("Original Admin");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRoles(Set.of("USER", "ADMIN"));
                repo.save(admin);
            }
        };
    }
}
