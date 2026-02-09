package com.arcaelo.homeworldbackend.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.DeckRepository;
import com.arcaelo.homeworldbackend.repo.EditionRepository;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;

@Mapper(componentModel = "spring")
public abstract class DeckMapper{

    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected EditionRepository editionRepository;
    @Autowired
    protected DeckRepository deckRepository;
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
        Edition ed = deck.getDisplayEdition();
        if(ed != null) dto.setDisplayEditionId(deck.getDisplayEdition().getUUID());
        else dto.setDisplayEditionId(null);
        dto.setDeckList(deck.getDeckList().stream().map(dl -> cpMapper.toDTO(dl)).toList());
        return dto;
    }

    @Mapping(source = "displayCard", target = "displayCard")
    @Mapping(source = "deckList", target="deckList")
    public DeckResponseDTO toResponseDTO(Deck deck){
        DeckResponseDTO dto = new DeckResponseDTO();
        dto.setId(deck.getId());
        dto.setHideStatus(deck.getHideStatus());
        Edition ed = deck.getDisplayEdition();
        if(ed != null) dto.setDisplayCardUrl(ed.getImage());
            else dto.setDisplayCardUrl(null);
        dto.setDeckList(deck.getDeckList().stream().map(dl -> cpMapper.toDTO(dl)).toList());
        return dto;
    }

    @Mapping(source = "displayCard", target = "displayCard")
    public DeckSimpleResponseDTO toSimpleResponseDTO(Deck deck){
        DeckSimpleResponseDTO dto = new DeckSimpleResponseDTO();
        dto.setId(deck.getId());
        dto.setHideStatus(deck.getHideStatus());
        Edition ed = deck.getDisplayEdition();
        if(ed != null) dto.setDisplayCardUrl(ed.getImage());
            else dto.setDisplayCardUrl(null);
        return dto;
    }

    public DeckResponseDTO toResponseDTO(DeckDTO deckDTO){
        DeckResponseDTO ret = new DeckResponseDTO();
        ret.setId(deckDTO.getId());
        ret.setDeckList(deckDTO.getDeckList());
        ret.setHideStatus(deckDTO.getHideStatus());
        if(deckDTO.getDisplayEditionId() == null){
            ret.setDisplayCardUrl(null);
        }else{
            Edition ed = editionRepository.findById(deckDTO.getDisplayEditionId()).orElse(null);
            if(ed != null) ret.setDisplayCardUrl(ed.getImage());
            else ret.setDisplayCardUrl(null);
        }
        return ret;
    }

    public DeckSimpleResponseDTO toSimpleResponseDTO(DeckDTO deckDTO){
        DeckSimpleResponseDTO ret = new DeckSimpleResponseDTO();
        ret.setId(deckDTO.getId());
        ret.setHideStatus(deckDTO.getHideStatus());
        if(deckDTO.getDisplayEditionId() == null){
            ret.setDisplayCardUrl(null);
        }else{
            Edition ed = editionRepository.findById(deckDTO.getDisplayEditionId()).orElse(null);
            if(ed != null) ret.setDisplayCardUrl(ed.getImage());
            else ret.setDisplayCardUrl(null);
        }
        return ret;
    }

    @Mapping(source = "playerId", target = "player.id")
    public Deck toEntity(DeckDTO deckDTO){
        if (deckDTO == null) return null;

        Deck deck;
        if(deckDTO.getId() != null){
            Deck oldDeck = deckRepository.findById(deckDTO.getId()).orElse(null);
            if(oldDeck != null){
                deck = oldDeck;
            }else{
                deck = new Deck();
                deck.setId(deckDTO.getId());
            }
        }else{
            deck = new Deck();
            deck.setId(deckDTO.getId());
        }

        if(deckDTO.getPlayerId() != null){
            Player player = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(() -> new IllegalArgumentException("Player not found: " + deckDTO.getPlayerId()));
            deck.setPlayer(player);
        }else{
            return null;
        }
        deck.setHideStatus(deckDTO.getHideStatus());
        if(deckDTO.getDisplayEditionId() == null){
            deck.setDisplayEdition(null);
        }else{
            Edition displayEdition = editionRepository.findById(deckDTO.getDisplayEditionId()).orElse(null);
            deck.setDisplayEdition(displayEdition);
        }
        List<CardPiece> deckList = deckDTO.getDeckList().stream().map(cp -> cpMapper.toEntity(cp)).collect(Collectors.toList());
        if(deck.getDeckList() == null){
            deck.setDeckList(deckList);
        }else{
            deck.getDeckList().clear();
            deck.getDeckList().addAll(deckList);
        }
        
        return deck;
    }

    @Mapping(source = "playerId", target = "player.id")
    public DeckDTO toDTO(DeckRequestBodyDTO deckRequestDTO){
        if (deckRequestDTO == null) return null;

        DeckDTO dto = new DeckDTO();
        dto.setId(null);
        dto.setPlayerId(null);
        dto.setDeckList(deckRequestDTO.getDeckList());
        if(deckRequestDTO.getDisplayEditionId() == null){
            dto.setDisplayEditionId(null);
        }else{
            Edition ed = editionRepository.findById(deckRequestDTO.getDisplayEditionId()).orElse(null);
            if(ed != null) dto.setDisplayEditionId(ed.getUUID());
            else dto.setDisplayEditionId(null);
        }
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