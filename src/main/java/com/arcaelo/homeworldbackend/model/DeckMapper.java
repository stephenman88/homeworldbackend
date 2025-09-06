package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Mapper(componentModel = "spring")
public abstract class DeckMapper{

    @Autowired
    protected PlayerRepository playerRepository;

    @Mapping(source = "player.id", target = "playerId")
    public abstract DeckDTO toDTO(Deck deck);

    @Mapping(source = "playerId", target = "player.id")
    public Deck toEntity(DeckDTO deckDTO){
        if (deckDTO == null) return null;

        Deck deck = new Deck();
        deck.setId(deckDTO.getId());

        if(deckDTO.getPlayerId() != null){
            Player player = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found: " + deckDTO.getPlayerId()));
            deck.setPlayer(player);
        }
        return deck;
    }
}