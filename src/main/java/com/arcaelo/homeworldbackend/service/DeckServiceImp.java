package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.DeckDTO;
import com.arcaelo.homeworldbackend.model.DeckMapper;
import com.arcaelo.homeworldbackend.model.Deck;
import com.arcaelo.homeworldbackend.repo.DeckRepository;
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
    public Optional<DeckDTO> getDeckById(Long id){
        return deckRepository.findById(id).map(this::convertToDTO);
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

    private Deck convertToEntity(DeckDTO deckDTO){
        return deckMapper.toEntity(deckDTO);
    }
}
