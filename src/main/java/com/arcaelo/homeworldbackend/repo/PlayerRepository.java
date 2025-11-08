package com.arcaelo.homeworldbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arcaelo.homeworldbackend.model.Player;
public interface PlayerRepository extends JpaRepository<Player, Long>{
    Optional<Player> findByEmail(String email);
}
