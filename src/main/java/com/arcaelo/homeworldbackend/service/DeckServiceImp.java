package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.DeckDTO;
import com.arcaelo.homeworldbackend.model.DeckMapper;
import com.arcaelo.homeworldbackend.model.DeckRequestBodyDTO;
import com.arcaelo.homeworldbackend.model.DeckResponseDTO;
import com.arcaelo.homeworldbackend.model.Deck;
import com.arcaelo.homeworldbackend.repo.DeckRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckServiceImp implements DeckService{
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    public DeckServiceImp(DeckRepository deckRepository, DeckMapper deckMapper){
        this.deckRepository = deckRepository;
        this.deckMapper = deckMapper;
    }

    @Override
    public List<DeckDTO> getAllDecks(){
        return deckRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DeckResponseDTO> getAllDecksAsResponse(Long playerId){
        Specification<Deck> spec = getByPlayerId(playerId);
        return deckRepository.findAll(spec).stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<DeckDTO> getSampleDecks(){
        Pageable p = Pageable.ofSize(20);
        return deckRepository.findAll(p).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DeckDTO> getAllDecks(Long playerId){
        Specification<Deck> spec = getByPlayerId(playerId);
        return deckRepository.findAll(spec).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<DeckDTO> getDeckById(Long id){
        return deckRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public Optional<DeckResponseDTO> getDeckResponseById(Long id){
        return deckRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Override
    public DeckDTO saveDeck(DeckDTO deckDTO){
        Deck deck = convertToEntity(deckDTO);
        Deck returnDeck = deckRepository.save(deck);
        return convertToDTO(returnDeck);
    }

    @Override
    public DeckDTO updateDeck(Long id, DeckDTO deckDTO){
        Deck deck = deckRepository.findById(id).orElseThrow();
        deck.setPlayer(convertToEntity(deckDTO).getPlayer());
        Deck updatedDeck = deckRepository.save(deck);
        return convertToDTO(updatedDeck);
    }

    @Override
    public void deleteDeck(Long id){
        deckRepository.deleteById(id);
    }

    private DeckDTO convertToDTO(Deck deck){
        return deckMapper.toDTO(deck);
    }

    @Override
    public DeckDTO convertToDTO(DeckRequestBodyDTO deckRequestBodyDTO){
        return deckMapper.toDTO(deckRequestBodyDTO);
    }

    private DeckResponseDTO convertToResponseDTO(Deck deck){
        return deckMapper.toResponseDTO(deck);
    }

    @Override
    public DeckResponseDTO convertToResponseDTO(DeckDTO deck){
        return deckMapper.toResponseDTO(deck);
    }

    private Deck convertToEntity(DeckDTO deckDTO){
        return deckMapper.toEntity(deckDTO);
    }

    private Specification<Deck> getByPlayerId(Long playerId){
        return (root, query, criteriaBuilder) -> {
            if(playerId == null) return null;
            
            return criteriaBuilder.equal(root.get("player").get("id"), playerId);
        };
    }
}
