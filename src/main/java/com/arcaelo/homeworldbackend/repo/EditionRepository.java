package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arcaelo.homeworldbackend.model.Edition;

public interface EditionRepository extends JpaRepository<Edition, String>, JpaSpecificationExecutor<Edition>{
    
}
