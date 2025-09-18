package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arcaelo.homeworldbackend.model.Card;

public interface CardRepository extends JpaRepository<Card, String>, JpaSpecificationExecutor<Card>{
    
}
