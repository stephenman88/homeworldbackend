package com.arcaelo.homeworldbackend.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Mapper(componentModel = "spring")
public abstract class DeckMapper{

    @Autowired
    protected PlayerRepository playerRepository;

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "displayCard.id", target = "displayCardId")
    @Mapping(source = "deckList", target="deckListIds")
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

    protected Long mapCardPieceToCardPieceIds(CardPiece cardPiece){
        if(cardPiece == null) return null;
        return cardPiece.getId();
    }

    protected List<Long> mapDeckListToDeckListIds(List<CardPiece> deckList){
        if(deckList == null) return null;
        return deckList.stream().map(cp -> cp.getId()).toList();
    }
}