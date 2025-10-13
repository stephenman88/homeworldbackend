package com.arcaelo.homeworldbackend.service;

import org.springframework.stereotype.Service;
import com.arcaelo.homeworldbackend.repo.CardSetRepository;
import com.arcaelo.homeworldbackend.model.CardSetDTO;
import com.arcaelo.homeworldbackend.model.CardSet;
import com.arcaelo.homeworldbackend.model.CardSetMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;

@Service
public class CardSetServiceImp implements CardSetService{
    private final CardSetRepository cardSetRepository;
    private final CardSetMapper cardSetMapper;

    public CardSetServiceImp(CardSetRepository cardSetRepository, CardSetMapper cardSetMapper){
        this.cardSetRepository = cardSetRepository;
        this.cardSetMapper = cardSetMapper;
    }

    @Transactional
    @Override
    public CardSetDTO extractCardSet(JsonNode editionNode){
        try{
            CardSetDTO cardSetDTO = convertToDTO(editionNode.path("set"));
            HashSet<String> emptyEditionIds = new HashSet<String>();
            cardSetDTO.setCardEditionsIds(emptyEditionIds);
            CardSet cardSet = convertToEntity(cardSetDTO);
            CardSet savedCardSet = cardSetRepository.save(cardSet);
            return convertToDTO(savedCardSet);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public List<CardSetDTO> updateEditions(HashMap<String, Set<String>> cardSetIdEditionIdPairs){
        try{
            Set<String> ids = cardSetIdEditionIdPairs.keySet();
            List<CardSetDTO> updatedDTOs = new ArrayList<CardSetDTO>();
            for(String id : ids){
                CardSet cs = cardSetRepository.findById(id).orElseThrow();
                CardSetDTO dto = convertToDTO(cs);
                dto.setCardEditionsIds(cardSetIdEditionIdPairs.get(id));
                if(cardSetRepository.existsById(id)){
                    CardSetDTO updatedCs = updateCardSet(id, dto);
                    updatedDTOs.add(updatedCs);
                }else{
                    CardSetDTO updatedCs = saveCardSet(dto);
                    updatedDTOs.add(updatedCs);
                }
            }
            return updatedDTOs;
        }catch(Exception e){
            System.out.println("updateEditions Exception: " + e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override 
    public List<CardSetDTO> getAllCardSets(){
        return cardSetRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Transactional
    @Override
    public Optional<CardSetDTO> getCardSetById(String id){
        return cardSetRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public CardSetDTO saveCardSet(CardSetDTO cardSetDTO){
        CardSet cardSet = convertToEntity(cardSetDTO);
        CardSet updatedCardSet = cardSetRepository.save(cardSet);
        return convertToDTO(updatedCardSet);
    }

    @Transactional
    @Override
    public CardSetDTO updateCardSet(String id, CardSetDTO cardSetDTO){
        CardSet temp = convertToEntity(cardSetDTO);
        CardSet cardSet = cardSetRepository.findById(id).orElseThrow();
        cardSet.setLanguage(temp.getLanguage());
        cardSet.setLastUpdate(temp.getLastUpdate());
        cardSet.setName(temp.getName());
        cardSet.setPrefix(temp.getPrefix());
        cardSet.setReleaseDate(temp.getReleaseDate());
        cardSet.setCardEditions(temp.getCardEditions());
        return convertToDTO(cardSetRepository.save(cardSet));
    }

    @Transactional
    @Override
    public void deleteCardSet(String id){
        cardSetRepository.deleteById(id);
    }

    private CardSetDTO convertToDTO(CardSet cardSet){
        return cardSetMapper.toDTO(cardSet);
    }

    private CardSet convertToEntity(CardSetDTO cardSetDTO){
        return cardSetMapper.toEntity(cardSetDTO);
    }

    private CardSetDTO convertToDTO(JsonNode jsonNode) throws JsonProcessingException{
        CardSetDTO dto = new CardSetDTO();
        dto.setId(jsonNode.path("id").asText());
        dto.setLanguage(jsonNode.path("language").asText());
        dto.setLastUpdate(jsonNode.path("last_update").asText());
        dto.setName(jsonNode.path("name").asText());
        dto.setPrefix(jsonNode.path("prefix").asText());
        LocalDateTime releaseDate = LocalDateTime.parse(jsonNode.path("release_date").asText());
        dto.setReleaseDate(releaseDate);
        return dto;
    }
}
