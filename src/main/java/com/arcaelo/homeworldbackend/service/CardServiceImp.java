package com.arcaelo.homeworldbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.CardSpecHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;

import com.arcaelo.homeworldbackend.model.CardMapper;
import com.arcaelo.homeworldbackend.model.CardResponseDTO;
import com.arcaelo.homeworldbackend.model.Edition;
import com.arcaelo.homeworldbackend.model.CardDTO;
import com.arcaelo.homeworldbackend.model.Card;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CardServiceImp implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final WebClient webClient;
    private final EditionService editionService;

    public CardServiceImp(CardRepository cardRepository,
    CardMapper cardMapper,
    WebClient.Builder webClientBuilder,
    @Value("${gaApiUrl}") String gaApiUrl,
    @Lazy EditionService editionService){
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.webClient = webClientBuilder.baseUrl(gaApiUrl)
            .codecs(configurer -> 
                configurer.defaultCodecs().maxInMemorySize(4*1024*1024)
            )
            .build();
        this.editionService = editionService;
    }

    @Transactional
    @Override
    public List<CardDTO> pullAllCards() throws RuntimeException{
        List<CardDTO> returnList = new ArrayList<CardDTO>();
        int pageNumber = 1;
        int resultCount = 0;
        do{
            List<CardDTO> pulledDTOs = pullCards(pageNumber);
            resultCount = pulledDTOs.size();
            returnList.addAll(pulledDTOs);
            pageNumber++;
        }while(resultCount > 0);
        return returnList;
    }

    @Transactional
    @Override
    public List<CardDTO> pullCards(Integer page) throws RuntimeException{
        try{
            JsonNode results = webClient.get().uri(
                    uriBuilder -> uriBuilder.path("/cards/search").queryParam("page", page).build()
                )
                .retrieve().bodyToMono(JsonNode.class).block();
            List<CardDTO> cardDTOs = new ArrayList<CardDTO>();
            HashMap<String, CardDTO> idDTOMap = new HashMap<String, CardDTO>();
            HashMap<String, Set<String>> cardSetEditionIds = new HashMap<String, Set<String>>();
            for(JsonNode cardNode : results.path("data")){
                CardDTO dto = convertToDTO(cardNode);
                cardDTOs.add(dto);
                idDTOMap.put(dto.getUUID(), dto);
                for(JsonNode editionNode : cardNode.path("editions")){
                    JsonNode setNode = editionNode.path("set");
                    if(cardSetEditionIds.containsKey(setNode.path("id").asText())){
                        cardSetEditionIds.get(setNode.path("id").asText()).add(editionNode.path("uuid").asText());
                    }else{
                        HashSet<String> editionIds = new HashSet<String>();
                        editionIds.add(editionNode.path("uuid").asText());
                        cardSetEditionIds.put(setNode.path("id").asText(), editionIds);
                    }

                    for(JsonNode otherOrientationNode : editionNode.path("other_orientations")){
                        String oouuid = otherOrientationNode.path("uuid").asText();
                        if(idDTOMap.containsKey(oouuid)){
                            idDTOMap.get(oouuid).getEditionIds().add(otherOrientationNode.path("edition").path("uuid").asText());
                        }else{
                            CardDTO oo = convertToDTO(otherOrientationNode);
                            cardDTOs.add(oo);
                            idDTOMap.put(oo.getUUID(), oo);
                        }
                    }
                }
            }
            List<Card> cards = cardDTOs.stream()
                .map(dto -> {
                    return convertToEntity(dto);}).toList();
            
            List<Card> savedCards = cardRepository.saveAll(cards);
            List<CardDTO> savedCardDTOs = savedCards.stream()
                .map(c -> convertToDTO(c)).toList();

            editionService.extractEditions(results);
            
            return savedCardDTOs;
        }catch(Exception e){
            throw new RuntimeException("Failed to pull cards", e);
        }
    }

    @Transactional
    @Override
    public List<CardDTO> getAllCards(){
        return cardRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Transactional
    @Override
    public List<CardResponseDTO> getAllCardResponses(){
        return cardRepository.findAll().stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    @Override
    public Optional<CardDTO> getCardById(String id){
        return cardRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public Optional<CardResponseDTO> getCardResponseById(String id){
        return cardRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Transactional
    @Override
    public List<CardResponseDTO> getCardResponsesByParameter(
        List<String> cardClass,
        String costMemoryOperator,
        Integer costMemory,
        String costReserveOperator,
        Integer costReserve,
        String durabilityOperator,
        Integer durability,
        String effectPart,
        List<String> rarities,
        List<String> cardSetPrefix,
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
        String name,
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
            CardSpecHelper.containsText(name, CardSpecHelper.FIELDS.STRING.NAME),
            CardSpecHelper.processInteger(powerOperator, power, CardSpecHelper.FIELDS.INTEGER.POWER),
            CardSpecHelper.processInteger(lifeOperator, life, CardSpecHelper.FIELDS.INTEGER.LIFE),
            CardSpecHelper.isFast(speed),
            CardSpecHelper.containsText(slug, CardSpecHelper.FIELDS.STRING.SLUG),
            CardSpecHelper.hasSubtypes(subtypes),
            CardSpecHelper.hasTypes(types),
            CardSpecHelper.ofCardSet(cardSetPrefix),
            CardSpecHelper.processThema(themaCharmOperator, themaCharm, CardSpecHelper.FIELDS.INTEGER.THEMA_CHARM),
            CardSpecHelper.processThema(themaFerocityOperator, themaFerocity, CardSpecHelper.FIELDS.INTEGER.THEMA_FEROCITY),
            CardSpecHelper.processThema(themaSumOperator, themaSum, CardSpecHelper.FIELDS.INTEGER.THEMA),
            CardSpecHelper.processThema(themaGraceOperator, themaGrace, CardSpecHelper.FIELDS.INTEGER.THEMA_GRACE),
            CardSpecHelper.processThema(themaMystiqueOperator, themaMystique, CardSpecHelper.FIELDS.INTEGER.THEMA_MYSTIQUE),
            CardSpecHelper.processThema(themaValorOperator, themaValor, CardSpecHelper.FIELDS.INTEGER.THEMA_VALOR)
        );

        List<CardResponseDTO> returnDTOs = cardRepository.findAll(specs)
            .stream().map(this::convertToResponseDTO).toList();
        return returnDTOs;
    };

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

    @Transactional
    @Override
    public CardDTO saveCard(CardDTO cardDTO){
        Card card = convertToEntity(cardDTO);
        return convertToDTO(cardRepository.save(card));
    }

    @Transactional
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

    @Transactional
    @Override
    public void deleteCard(String id){
        cardRepository.deleteById(id);
    }

    private CardDTO convertToDTO(Card card){
        return cardMapper.toDTO(card);
    }

    private CardResponseDTO convertToResponseDTO(Card card){
        return cardMapper.toResponseDTO(card);
    }

    private Card convertToEntity(CardDTO cardDTO){
        return cardMapper.toEntity(cardDTO);
    }

    @Override
    public void addOtherOrientationToEdition(JsonNode otherOrientation, Edition edition) throws JsonProcessingException{
        CardDTO cardDTO = convertToDTO(otherOrientation);
        Card card = cardRepository.findById(cardDTO.getUUID()).orElse(convertToEntity(cardDTO));
        Set<Card> oo = new HashSet<Card>();
        if(edition.getOtherOrientation() == null){
            edition.setOtherOrientation(oo);
        }
        edition.getOtherOrientation().add(card);
    }

    @Override
    public CardDTO convertToDTO(JsonNode cardNode) throws JsonProcessingException{
            JsonNode node = cardNode;
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
            if(node.get("editions") != null){
                for(int c = 0; node.path("editions").get(c) != null; c++){
                    editionIds.add(node.path("editions").path(c).path("uuid").asText());
                }
            }else if(node.get("edition") != null){
                editionIds.add(node.path("edition").path("uuid").asText());
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
                references.put("direction", node.path("references").path(c).path("direction").asText());
                references.put("kind", node.path("references").path(c).path("kind").asText());
                references.put("name", node.path("references").path(c).path("name").asText());
                references.put("slug", node.path("references").path(c).path("slug").asText());
                referenceList.add(references);
            }
            
            dto.setReferences(referenceList);

            HashSet<HashMap<String, String>> rules = new HashSet<HashMap<String, String>>();
            for(int c = 0; node.path("rule").get(c) != null; c++){
                HashMap<String, String> rule = new HashMap<String, String>();
                rule.put("date_added", node.path("rule").path(c).path("date_added").asText());
                rule.put("description", node.path("rule").path(c).path("description").asText());
                rule.put("title", node.path("rule").path(c).path("title").asText());
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

            return dto;
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
            for(JsonNode rbNode : node.path("referenced_by")){
                HashMap<String, String> rb = new HashMap<String, String>();

                rb.put("direction", rbNode.path("direction").asText());
                rb.put("kind", rbNode.path("kind").asText());
                rb.put("name", rbNode.path("name").asText());
                rb.put("slug", rbNode.path("slug").asText());
                referencedBy.add(rb);
            }
            dto.setReferencedBy(referencedBy);

            List<HashMap<String, String>> referenceList = new ArrayList<HashMap<String, String>>();
            for(JsonNode rNode : node.path("references")){
                HashMap<String, String> references = new HashMap<String, String>();
                references.put("direction", rNode.path("direction").asText());
                references.put("kind", rNode.path("kind").asText());
                references.put("name", rNode.path("name").asText());
                references.put("slug", rNode.path("slug").asText());
                referenceList.add(references);
            }
            
            dto.setReferences(referenceList);

            HashSet<HashMap<String, String>> rules = new HashSet<HashMap<String, String>>();
            for(int c = 0; node.path("rule").get(c) != null; c++){
                HashMap<String, String> rule = new HashMap<String, String>();
                rule.put("date_added", node.path("rule").path(c).path("date_added").asText());
                rule.put("description", node.path("rule").path(c).path("description").asText());
                rule.put("title", node.path("rule").path(c).path("title").asText());
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
