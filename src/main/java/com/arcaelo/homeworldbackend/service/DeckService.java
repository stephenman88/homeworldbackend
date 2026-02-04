package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.DeckDTO;
import com.arcaelo.homeworldbackend.model.DeckRequestBodyDTO;
import com.arcaelo.homeworldbackend.model.DeckResponseDTO;
import com.arcaelo.homeworldbackend.model.DeckSimpleResponseDTO;

import java.util.List;
import java.util.Optional;

public interface DeckService {
    List<DeckDTO> getAllDecks();
    List<DeckDTO> getAllDecks(Long playerId);
    List<DeckResponseDTO> getAllDecksAsResponse(Long playerId);
    List<DeckSimpleResponseDTO> getAllDecksAsSimpleResponse(Long playerId);
    List<DeckSimpleResponseDTO> getSampleDecks();
    Optional<DeckDTO> getDeckById(Long id);
    Optional<DeckResponseDTO> getDeckResponseById(Long id);
    DeckDTO saveDeck(DeckDTO deckDTO);
    DeckDTO updateDeck(Long id, DeckDTO deckDTO);
    void deleteDeck(Long id);
    DeckResponseDTO convertToResponseDTO(DeckDTO deck);
    DeckDTO convertToDTO(DeckRequestBodyDTO deckRequestBodyDTO);
}
