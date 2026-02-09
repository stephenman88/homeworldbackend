package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import com.arcaelo.homeworldbackend.model.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long>, JpaSpecificationExecutor<Deck>{
    void deleteById(@NonNull Long id);
}
