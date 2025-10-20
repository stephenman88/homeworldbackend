package com.arcaelo.homeworldbackend.service;

import java.util.List;
import java.util.Optional;

import com.arcaelo.homeworldbackend.model.EditionDTO;
import com.fasterxml.jackson.databind.JsonNode;

public interface EditionService {

    List<EditionDTO> extractEditions(JsonNode gaApiCardList);
    List<EditionDTO> getAllEditions();
    Optional<EditionDTO> getEditionById(String id);
    List<EditionDTO> getEditionsByIds(List<String> ids);
    void saveEdition(EditionDTO editionDTO);
    void updateEdition(String id, EditionDTO editionDTO);
    void deleteEdition(String id);
}
