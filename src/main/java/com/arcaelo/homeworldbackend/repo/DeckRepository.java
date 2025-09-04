package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arcaelo.homeworldbackend.model.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long>{
    
}
