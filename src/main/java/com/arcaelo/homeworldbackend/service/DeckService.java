package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.DeckDTO;

import java.util.List;
import java.util.Optional;

public interface DeckService {
    List<DeckDTO> getAllDecks();
    Optional<DeckDTO> getDeckById(Long id);
    DeckDTO saveDeck(DeckDTO deckDTO);
    DeckDTO updateDeck(Long id, DeckDTO deckDTO);
    void deleteDeck(Long id);
}
