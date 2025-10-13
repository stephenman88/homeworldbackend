package com.arcaelo.homeworldbackend.service;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import com.arcaelo.homeworldbackend.model.EditionDTO;
import com.fasterxml.jackson.databind.JsonNode;

public interface EditionService {

    List<EditionDTO> extractEditions(JsonNode gaApiCardList);
    List<EditionDTO> getAllEditions();
    Optional<EditionDTO> getEditionById(String id);
    List<EditionDTO> getEditionsByIds(List<String> ids);
    List<EditionDTO> getEditionsByParameters(
        String cardId,
        Set<String> collaborators,
        String collectorNumber,
        String configuration,
        String effect,
        String effectHtml,
        String effectRaw,
        String flavor,
        String illustrator,
        String image,
        String orientation,
        String rarityOperator,
        Integer rarity,
        String slug,
        String themaCharmOperator,
        Integer themaCharm,
        String themaFerocityOperator,
        Integer themaFerocity,
        String themaSumOperator,
        Integer themaSum,
        String themaGraceOperator,
        Integer themaGrace,
        String themaMystiqueOperator,
        Integer themaMystique,
        String themaValorOperator,
        Integer themaValor,
        String uuid
    );
    void saveEdition(EditionDTO editionDTO);
    void updateEdition(String id, EditionDTO editionDTO);
    void deleteEdition(String id);
}
