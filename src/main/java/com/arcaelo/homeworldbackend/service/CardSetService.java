package com.arcaelo.homeworldbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.arcaelo.homeworldbackend.model.CardSetDTO;
import com.fasterxml.jackson.databind.JsonNode;

public interface CardSetService {
    CardSetDTO extractCardSet(JsonNode apiResults);
    List<CardSetDTO> getAllCardSets();
    Optional<CardSetDTO> getCardSetById(String id);
    CardSetDTO saveCardSet(CardSetDTO cardSetDTO);
    CardSetDTO updateCardSet(String id, CardSetDTO cardSetDTO);
    List<CardSetDTO> updateEditions(HashMap<String, Set<String>> cardSetIdEditionIdPairs);
    void deleteCardSet(String id);
}
