package com.arcaelo.homeworldbackend.model;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Mapper(componentModel = "spring")
public abstract class DeckMapper{

    @Autowired
    protected PlayerRepository playerRepository;

    @Autowired
    protected CardPieceMapper cpMapper;

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "displayCard", target = "displayCard")
    @Mapping(source = "deckList", target="deckList")
    public DeckDTO toDTO(Deck deck){
        DeckDTO dto = new DeckDTO();
        dto.setId(deck.getId());
        dto.setHideStatus(deck.getHideStatus());
        dto.setPlayerId(deck.getPlayer().getId());
        dto.setDisplayCard(cpMapper.toDTO(deck.getDisplayCard()));
        dto.setDeckList(deck.getDeckList().stream().map(dl -> cpMapper.toDTO(dl)).toList());
        return dto;
    }

    @Mapping(source = "displayCard", target = "displayCard")
    @Mapping(source = "deckList", target="deckList")
    public DeckResponseDTO toResponseDTO(Deck deck){
        DeckResponseDTO dto = new DeckResponseDTO();
        dto.setId(deck.getId());
        dto.setHideStatus(deck.getHideStatus());
        dto.setDisplayCard(cpMapper.toDTO(deck.getDisplayCard()));
        dto.setDeckList(deck.getDeckList().stream().map(dl -> cpMapper.toDTO(dl)).toList());
        return dto;
    }

    @Mapping(source = "displayCard", target = "displayCard")
    public DeckSimpleResponseDTO toSimpleResponseDTO(Deck deck){
        DeckSimpleResponseDTO dto = new DeckSimpleResponseDTO();
        dto.setId(deck.getId());
        dto.setHideStatus(deck.getHideStatus());
        dto.setDisplayCard(cpMapper.toDTO(deck.getDisplayCard()));
        return dto;
    }

    public abstract DeckResponseDTO toResponseDTO(DeckDTO deckDTO);

    public abstract DeckSimpleResponseDTO toSimpleResponseDTO(DeckDTO deckDTO);

    @Mapping(source = "playerId", target = "player.id")
    public Deck toEntity(DeckDTO deckDTO){
        if (deckDTO == null) return null;

        Deck deck = new Deck();
        deck.setId(deckDTO.getId());

        if(deckDTO.getPlayerId() != null){
            Player player = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found: " + deckDTO.getPlayerId()));
            deck.setPlayer(player);
        }else{
            return null;
        }
        deck.setHideStatus(deckDTO.getHideStatus());
        CardPiece display = cpMapper.toEntity(deckDTO.getDisplayCard());
        deck.setDisplayCard(display);
        List<CardPiece> deckList = deckDTO.getDeckList().stream().map(cp -> cpMapper.toEntity(cp)).collect(Collectors.toList());
        deck.setDeckList(deckList);
        return deck;
    }

    @Mapping(source = "playerId", target = "player.id")
    public DeckDTO toDTO(DeckRequestBodyDTO deckRequestDTO){
        if (deckRequestDTO == null) return null;

        DeckDTO dto = new DeckDTO();
        dto.setId(null);
        dto.setPlayerId(null);
        dto.setDeckList(deckRequestDTO.getDeckList());
        dto.setDisplayCard(deckRequestDTO.getDisplayCard());
        dto.setHideStatus(deckRequestDTO.getHideStatus());
        return dto;
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