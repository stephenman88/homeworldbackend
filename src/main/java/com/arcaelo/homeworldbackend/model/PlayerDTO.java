package com.arcaelo.homeworldbackend.model;

import java.util.Set;

public record PlayerDTO(Long id, String email, String password, Set<DeckDTO> deckDTOs) {
    
}
