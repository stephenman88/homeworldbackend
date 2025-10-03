package com.arcaelo.homeworldbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.CardSpecHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import com.arcaelo.homeworldbackend.model.CardMapper;
import com.arcaelo.homeworldbackend.model.CardDTO;
import com.arcaelo.homeworldbackend.model.Card;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImp implements CardService {
    private final ObjectMapper objectMapper;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final WebClient webClient;

    public CardServiceImp(CardRepository cardRepository, CardMapper cardMapper, WebClient.Builder webClientBuilder, ObjectMapper objectMapper, @Value("${gaApiUrl}") String gaApiUrl){
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.webClient = webClientBuilder.baseUrl(gaApiUrl).build();
        this.objectMapper = objectMapper;
    }

    @Override
    public List<CardDTO> pullCards() throws RuntimeException{
        try{
            JsonNode results = webClient.get().uri("/card/search").retrieve().bodyToMono(JsonNode.class).block();
            System.out.println("data array: " + results.path("data"));
            List<CardDTO> cardDTOs = convertToDTOs(results.path("data"));

            List<Card> cards = cardDTOs.stream()
                .map(dto -> {return convertToEntity(dto);}).toList();
            List<Card> savedCards = cardRepository.saveAll(cards);
            List<CardDTO> savedCardDTOs = savedCards.stream()
                .map(c -> convertToDTO(c)).toList();
            return savedCardDTOs;
        }catch(Exception e){
            throw new RuntimeException("Failed to pull cards", e);
        }
    }

    @Override
    public List<CardDTO> getAllCards(){
        return cardRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Transactional
    @Override
    public Optional<CardDTO> getCardById(String id){
        return cardRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public List<CardDTO> getCardsByParameter(
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
    ){
        Specification<Card> specs = Specification.allOf(
            CardSpecHelper.hasCardClasses(cardClass),
            CardSpecHelper.processInteger(costMemoryOperator, costMemory, CardSpecHelper.FIELDS.INTEGER.COST_MEMORY),
            CardSpecHelper.processInteger(costReserveOperator, costReserve, CardSpecHelper.FIELDS.INTEGER.COST_RESERVE),
            CardSpecHelper.processInteger(durabilityOperator, durability, CardSpecHelper.FIELDS.INTEGER.DURABILITY),
            CardSpecHelper.containsText(effectPart, CardSpecHelper.FIELDS.STRING.EFFECT),
            CardSpecHelper.hasElements(elements),
            CardSpecHelper.containsText(flavor, CardSpecHelper.FIELDS.STRING.FLAVOR),
            CardSpecHelper.processInteger(levelOperator, level, CardSpecHelper.FIELDS.INTEGER.LEVEL),
            CardSpecHelper.containsText(namePart, CardSpecHelper.FIELDS.STRING.NAME),
            CardSpecHelper.processInteger(powerOperator, power, CardSpecHelper.FIELDS.INTEGER.POWER),
            CardSpecHelper.isFast(speed),
            CardSpecHelper.containsText(slug, CardSpecHelper.FIELDS.STRING.SLUG),
            CardSpecHelper.hasSubtypes(subtypes),
            CardSpecHelper.hasTypes(types)
        );

        List<CardDTO> returnDTOs = cardRepository.findAll(specs)
            .stream().map(this::convertToDTO).toList();
        return returnDTOs;
    };

    @Override
    public CardDTO saveCard(CardDTO cardDTO){
        Card card = convertToEntity(cardDTO);
        return convertToDTO(cardRepository.save(card));
    }

    @Override
    public CardDTO updateCard(String id, CardDTO cardDTO){
        Card card = cardRepository.findById(id).orElseThrow();
        Card isCard = convertToEntity(cardDTO);
        card.setClasses(isCard.getClasses());
        card.setCostMemory(isCard.getCostMemory());
        card.setCostReserve(isCard.getCostReserve());
        card.setDurability(isCard.getDurability());
        card.setEditions(isCard.getEditions());
        card.setEffect(isCard.getEffect());
        card.setEffectRaw(isCard.getEffectRaw());
        card.setFlavor(isCard.getFlavor());
        card.setLastUpdate(isCard.getLastUpdate());
        card.setLegality(isCard.getLegality());
        card.setLevel(isCard.getLevel());
        card.setLife(isCard.getLife());
        card.setName(isCard.getName());
        card.setPower(isCard.getPower());
        card.setReferencedBy(isCard.getReferencedBy());
        card.setReferences(isCard.getReferences());
        card.setRule(isCard.getRule());
        card.setSpeed(isCard.getSpeed());
        card.setSlug(isCard.getSlug());
        card.setSubtypes(isCard.getSubtypes());
        card.setTypes(isCard.getTypes());
        return convertToDTO(cardRepository.save(card));
    }

    @Override
    public void deleteCard(String id){
        cardRepository.deleteById(id);
    }

    private CardDTO convertToDTO(Card card){
        return cardMapper.toDTO(card);
    }

    private Card convertToEntity(CardDTO cardDTO){
        return cardMapper.toEntity(cardDTO);
    }

    private CardDTO convertToDTO(JsonNode jsonNode) throws JsonProcessingException{
        return objectMapper.treeToValue(jsonNode, CardDTO.class);
    }

    private List<CardDTO> convertToDTOs(JsonNode jsonNode) throws JsonProcessingException{
        ArrayList<CardDTO> returnList = new ArrayList<CardDTO>();

        for(int i = 0; jsonNode.get(i) != null; i++){
            JsonNode node = jsonNode.path(i);
            CardDTO dto = new CardDTO();
            dto.setUUID(node.path("uuid").asText());
            
            HashSet<String> classes = new HashSet<String>();
            for(int c = 0; node.path("classes").get(c) != null; c++){
                classes.add(node.path("classes").path(c).asText());
            }
            dto.setClasses(classes);
            dto.setCostMemory(node.path("cost_memory").isNull() ? null : node.path("cost_memory").numberValue().intValue());
            dto.setCostReserve(node.path("cost_reserve").isNull() ? null : node.path("cost_reserve").numberValue().intValue());
            dto.setDurability(node.path("durability").isNull() ? null : node.path("durability").numberValue().intValue());
            
            ArrayList<String> editionIds = new ArrayList<String>();
            for(int c = 0; node.path("editions").get(c) != null; c++){
                editionIds.add(node.path("editions").path(c).path("uuid").asText());
            }
            dto.setEditionIds(editionIds);

            HashSet<String> elements= new HashSet<String>();
            for(int c = 0; node.path("elements").get(c) != null; c++){
                elements.add(node.path("elements").path(c).asText());
            }
            dto.setElements(elements);

            dto.setEffect(node.path("effect").isNull() ? null : node.path("effect").asText());
            dto.setEffectRaw(node.path("effect_raw").isNull() ? null : node.path("effect_raw").asText());
            dto.setFlavor(node.path("flavor").isNull() ? null : node.path("flavor").asText());
            dto.setLegality(null);
            dto.setLevel(node.path("level").isNull() ? null : node.path("level").numberValue().intValue());
            dto.setLife(node.path("life").isNumber() ? node.path("life").asInt() : null);
            dto.setName(node.path("name").asText());
            dto.setPower(node.path("power").isNumber() ? node.path("power").intValue() : null);

            List<HashMap<String, String>> referencedBy = new ArrayList<HashMap<String, String>>();
            for(int c = 0; node.path("referenced_by").get(c) != null; c++){
                JsonNode rbNode = node.path("referenced_by").path(c);
                HashMap<String, String> rb = new HashMap<String, String>();

                rb.put("direction", rbNode.path("direction").asText());
                rb.put("kind", rbNode.path("kind").asText());
                rb.put("name", rbNode.path("name").asText());
                rb.put("slug", rbNode.path("slug").asText());
                referencedBy.add(rb);
            }
            dto.setReferencedBy(referencedBy);

            List<HashMap<String, String>> referenceList = new ArrayList<HashMap<String, String>>();
            for(int c = 0; node.path("references").get(c) != null; c++){
                HashMap<String, String> references = new HashMap<String, String>();
                references.put("direction", node.path("references").path("direction").asText());
                references.put("kind", node.path("references").path("kind").asText());
                references.put("name", node.path("references").path("name").asText());
                references.put("slug", node.path("references").path("slug").asText());
                referenceList.add(references);
            }
            
            dto.setReferences(referenceList);

            HashSet<HashMap<String, String>> rules = new HashSet<HashMap<String, String>>();
            for(int c = 0; node.path("rule").get(c) != null; c++){
                HashMap<String, String> rule = new HashMap<String, String>();
                rule.put("date_added", node.path("rule").path(c).path("date_added").asText());
                rule.put("description", node.path("description").path(c).path("description").asText());
                rule.put("title", node.path("title").path(c).path("title").asText());
                rules.add(rule);
            }
            dto.setRule(rules);

            dto.setSpeed(node.path("speed").isBoolean() ? node.path("speed").booleanValue() : null);
            dto.setSlug(node.path("slug").isNull() ? null : node.path("slug").asText());
            
            HashSet<String> subtypes = new HashSet<String>();
            for(int c = 0; node.path("subtypes").get(c) != null; c++){
                subtypes.add(node.path("subtypes").path(c).asText());
            }
            dto.setSubtypes(subtypes);

            HashSet<String> types = new HashSet<String>();
            for(int c = 0; node.path("types").get(c) != null; c++){
                types.add(node.path("types").path(c).asText());
            }
            dto.setTypes(types);

            returnList.add(dto);
        }

        return returnList;
    }
}
