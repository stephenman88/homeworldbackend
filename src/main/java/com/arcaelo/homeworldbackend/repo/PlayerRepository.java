package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arcaelo.homeworldbackend.model.Player;
public interface PlayerRepository extends JpaRepository<Player, Long>{
    
}
