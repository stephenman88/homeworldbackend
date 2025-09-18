package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arcaelo.homeworldbackend.model.CardSet;

public interface CardSetRepository extends JpaRepository<CardSet, String>{
    
}
