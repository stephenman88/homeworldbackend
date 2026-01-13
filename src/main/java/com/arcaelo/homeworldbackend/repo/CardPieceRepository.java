package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arcaelo.homeworldbackend.model.CardPiece;

public interface CardPieceRepository extends JpaRepository<CardPiece, Long>, JpaSpecificationExecutor<CardPiece>{

    
}