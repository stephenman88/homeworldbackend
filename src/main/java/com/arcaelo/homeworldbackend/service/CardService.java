package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.CardDTO;

import java.util.List;
import java.util.Optional;

public interface CardService {
    List<CardDTO> pullAllCards();
    List<CardDTO> pullCards(Integer page);
    List<CardDTO> getAllCards();
    Optional<CardDTO> getCardById(String id);
    List<CardDTO> getCardsByParameter(
        List<String> cardClass,
        String costMemoryOperator,
        Integer costMemory,
        String costReserveOperator,
        Integer costReserve,
        String durabilityOperator,
        Integer durability,
        String effectPart,
        List<String> rarities,
        List<String> cardSetIds,
        String themaCharmOperator,
        Integer themaCharm,
        String themaFerocityOperator,
        Integer themaFerocity,
        String themaGraceOperator,
        Integer themaGrace,
        String themaMystiqueOperator,
        Integer themaMystique,
        String themaSumOperator,
        Integer themaSum,
        String themaValorOperator,
        Integer themaValor,
        List<String> elements,
        String flavor,
        String legalityFormat,
        String limitOperator,
        Integer limit,
        String levelOperator,
        Integer level,
        String lifeOperator,
        Integer life,
        String namePart,
        String powerOperator,
        Integer power,
        Boolean speed,
        String slug,
        List<String> subtypes,
        List<String> types,
        String id
        );
    CardDTO saveCard(CardDTO cardDTO);
    CardDTO updateCard(String id, CardDTO cardDTO);
    void deleteCard(String id);
}
