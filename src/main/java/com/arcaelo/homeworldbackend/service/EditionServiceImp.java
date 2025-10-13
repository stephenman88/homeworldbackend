package com.arcaelo.homeworldbackend.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.arcaelo.homeworldbackend.repo.EditionRepository;
import com.arcaelo.homeworldbackend.model.Edition;
import com.arcaelo.homeworldbackend.model.EditionDTO;
import com.arcaelo.homeworldbackend.model.EditionMapper;
import com.arcaelo.homeworldbackend.repo.EditionSpecHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class EditionServiceImp implements EditionService{
    private final EditionRepository editionRepository;
    private final EditionMapper editionMapper;
    private final CardSetService cardSetService;
    private final CardService cardService;

    public EditionServiceImp(EditionRepository editionRepository, EditionMapper editionMapper, CardSetService cardSetService, @Lazy CardService cardService){
        this.editionMapper = editionMapper;
        this.editionRepository = editionRepository;
        this.cardSetService = cardSetService;
        this.cardService = cardService;
    }

    @Transactional
    @Override
    public List<EditionDTO> extractEditions(JsonNode gaApiCardList){
        try{

            List<Edition> allEditions = new ArrayList<Edition>();
            for(JsonNode cardData : gaApiCardList.path("data")){
                JsonNode editionsList = cardData.path("editions");
                for(JsonNode edition : editionsList){
                    cardSetService.extractCardSet(edition);
                    EditionDTO dto = convertToDTO(edition);
                    Edition entity = convertToEntity(dto);
                    
                    for(JsonNode ooNode : edition.path("other_orientations")){
                        cardService.addOtherOrientationToEdition(ooNode, entity);
                        JsonNode innerEditionNode = ooNode.path("edition");
                        cardSetService.extractCardSet(innerEditionNode);
                        EditionDTO innerEditionDTO = convertToDTO(innerEditionNode);
                        Edition innerEditionEntity = convertToEntity(innerEditionDTO);
                        allEditions.add(innerEditionEntity);

                        Set<String> ooEditionIds;
                        if(entity.getOtherOrientationEditionIds() == null){
                            ooEditionIds = new HashSet<String>();
                            entity.setOtherOrientationEditionIds(ooEditionIds);
                        }else{
                            ooEditionIds = entity.getOtherOrientationEditionIds();
                        }
                        ooEditionIds.add(innerEditionEntity.getUUID());
                    }

                    System.out.println("EditionServiceImp line 63: other orientation size is "+ entity.getOtherOrientation().size());
                    allEditions.add(entity);
                }
            }
            if(allEditions.size() == 0) return null;
            
            List<Edition> savedEditions = editionRepository.saveAll(allEditions);

            return savedEditions.stream().map(e -> convertToDTO(e)).toList();
        }catch(Exception e){
            System.out.println("extractEditions exception: " + e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public List<EditionDTO> getAllEditions(){
        return editionRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Transactional
    @Override
    public Optional<EditionDTO> getEditionById(String id){
        return editionRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public List<EditionDTO> getEditionsByIds(List<String> ids){
        return editionRepository.findAllById(ids).stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Transactional
    @Override
    public List<EditionDTO> getEditionsByParameters(
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
    ){
        return editionRepository.findAll(
            Specification.allOf(EditionSpecHelper.isCard(cardId),
            EditionSpecHelper.hasCollaborators(collaborators),
            EditionSpecHelper.containsString(collectorNumber, EditionSpecHelper.FIELDS.STRING.COLLECTOR_NUMBER),
            EditionSpecHelper.containsString(configuration, EditionSpecHelper.FIELDS.STRING.CONFIGURATION),
            EditionSpecHelper.containsString(effect, EditionSpecHelper.FIELDS.STRING.EFFECT),
            EditionSpecHelper.containsString(effectHtml, EditionSpecHelper.FIELDS.STRING.EFFECT_HTML),
            EditionSpecHelper.containsString(effectRaw, EditionSpecHelper.FIELDS.STRING.EFFECT_RAW),
            EditionSpecHelper.containsString(flavor, EditionSpecHelper.FIELDS.STRING.FLAVOR),
            EditionSpecHelper.containsString(illustrator, EditionSpecHelper.FIELDS.STRING.ILLUSTRATOR),
            EditionSpecHelper.containsString(image,EditionSpecHelper.FIELDS.STRING.IMAGE),
            EditionSpecHelper.containsString(orientation, EditionSpecHelper.FIELDS.STRING.ORIENTATION),
            EditionSpecHelper.processInteger(rarityOperator, rarity, EditionSpecHelper.FIELDS.INTEGER.RARITY),
            EditionSpecHelper.containsString(slug, EditionSpecHelper.FIELDS.STRING.SLUG),
            EditionSpecHelper.processInteger(themaCharmOperator, themaCharm, EditionSpecHelper.FIELDS.INTEGER.THEMA_CHARM_FOIL),
            EditionSpecHelper.processInteger(themaCharmOperator, themaCharm, EditionSpecHelper.FIELDS.INTEGER.THEMA_CHARM_NONFOIL),
            EditionSpecHelper.processInteger(themaFerocityOperator, themaFerocity, EditionSpecHelper.FIELDS.INTEGER.THEMA_FEROCITY_FOIL),
            EditionSpecHelper.processInteger(themaFerocityOperator, themaFerocity, EditionSpecHelper.FIELDS.INTEGER.THEMA_FEROCITY_NONFOIL),
            EditionSpecHelper.processInteger(themaGraceOperator, themaGrace, EditionSpecHelper.FIELDS.INTEGER.THEMA_GRACE_FOIL),
            EditionSpecHelper.processInteger(themaGraceOperator, themaGrace, EditionSpecHelper.FIELDS.INTEGER.THEMA_GRACE_NONFOIL),
            EditionSpecHelper.processInteger(themaMystiqueOperator, themaMystique, EditionSpecHelper.FIELDS.INTEGER.THEMA_MYSTIQUE_FOIL),
            EditionSpecHelper.processInteger(themaMystiqueOperator, themaMystique, EditionSpecHelper.FIELDS.INTEGER.THEMA_MYSTIQUE_NONFOIL),
            EditionSpecHelper.processInteger(themaValorOperator, themaValor, EditionSpecHelper.FIELDS.INTEGER.THEMA_VALOR_FOIL),
            EditionSpecHelper.processInteger(themaValorOperator, themaValor, EditionSpecHelper.FIELDS.INTEGER.THEMA_VALOR_NONFOIL)
        )).stream()
        .map(this::convertToDTO).toList();
    }

    @Transactional
    @Override
    public void saveEdition(EditionDTO editionDTO){
        editionRepository.save(convertToEntity(editionDTO));
    }

    @Transactional
    @Override
    public void updateEdition(String id, EditionDTO editionDTO){
        Edition og = editionRepository.findById(id).orElseThrow();
        Edition c = convertToEntity(editionDTO);
        og.setCard(c.getCard());
        og.setCollaborators(c.getCollaborators());
        og.setCollectorNumber(c.getCollectorNumber());
        og.setConfiguration(c.getConfiguration());
        og.setEffect(c.getEffect());
        og.setEffectHtml(c.getEffectHtml());
        og.setEffectRaw(c.getEffectRaw());
        og.setFlavor(c.getFlavor());
        og.setIllustrator(c.getIllustrator());
        og.setImage(c.getImage());
        og.setOrientation(c.getOrientation());
        og.setOtherOrientation(c.getOtherOrientation());
        og.setRarity(c.getRarity());
        og.setSlug(c.getSlug());
        og.setCardSet(c.getCardSet());
        og.setThemaCharmFoil(c.getThemaCharmFoil());
        og.setThemaFerocityFoil(c.getThemaFerocityFoil());
        og.setThemaFoil(c.getThemaFoil());
        og.setThemaGraceFoil(c.getThemaGraceFoil());
        og.setThemaMystiqueFoil(c.getThemaMystiqueFoil());
        og.setThemaValorFoil(c.getThemaValorFoil());
        og.setThemaCharmNonfoil(c.getThemaCharmNonfoil());
        og.setThemaFerocityNonfoil(c.getThemaFerocityNonfoil());
        og.setThemaNonfoil(c.getThemaNonfoil());
        og.setThemaGraceNonfoil(c.getThemaGraceNonfoil());
        og.setThemaMystiqueNonfoil(c.getThemaMystiqueNonfoil());
        og.setThemaValorNonfoil(c.getThemaValorNonfoil());

        editionRepository.save(og);
    }

    @Transactional
    @Override
    public void deleteEdition(String id){
        editionRepository.deleteById(id);
    }

    private EditionDTO convertToDTO(Edition edition){
        return editionMapper.toDTO(edition);
    }

    private Edition convertToEntity(EditionDTO editionDTO){
        return editionMapper.toEntity(editionDTO);
    }

    private EditionDTO convertToDTO(JsonNode jsonNode) throws JsonProcessingException{
        EditionDTO dto = new EditionDTO();
        dto.setCardId(jsonNode.path("card_id").asText());
        Set<String> collabs = new HashSet<String>();
        for(JsonNode collab : jsonNode.path("collaborators")){
            collabs.add(collab.asText());
        }
        dto.setCollaborators(collabs);
        dto.setCollectorNumber(jsonNode.path("collector_number").asText());
        dto.setConfiguration(jsonNode.path("configuration").asText());
        dto.setEffect(jsonNode.path("effect").isTextual() ? jsonNode.path("effect").asText() : null);
        dto.setEffectHtml(jsonNode.path("effect_html").isTextual() ? jsonNode.path("effect_html").asText() : null);
        dto.setEffectRaw(jsonNode.path("effect_raw").isNull() ? null : jsonNode.path("effect_raw").asText());
        dto.setFlavor(jsonNode.path("flavor").isNull() ? null : jsonNode.path("flavor").asText());
        dto.setIllustrator(jsonNode.path("illustrator").isNull() ? null : jsonNode.path("illustrator").asText());
        dto.setImage(jsonNode.path("image").asText());
        
        dto.setOrientation(jsonNode.path("orientation").isNull() ? null : jsonNode.path("orientation").asText());
        Set<String> otherOrientationIds = new HashSet<String>();
        dto.setOtherOrientationCardId(otherOrientationIds);
        dto.setRarity(jsonNode.path("rarity").intValue());
        dto.setSlug(jsonNode.path("slug").asText());
        dto.setCardSetId(jsonNode.path("set").path("id").asText());
        dto.setThemaCharmFoil(jsonNode.path("thema_charm_foil").isNull() ? null : jsonNode.path("thema_charm_foil").intValue());
        dto.setThemaFerocityFoil(jsonNode.path("thema_ferocity_foil").isNull() ? null : jsonNode.path("thema_ferocity_foil").intValue());
        dto.setThemaFoil(jsonNode.path("thema_foil").isNull() ? null : jsonNode.path("thema_foil").intValue());
        dto.setThemaGraceFoil(jsonNode.path("thema_grace_foil").isNull() ? null : jsonNode.path("thema_grace_foil").intValue());
        dto.setThemaMystiqueFoil(jsonNode.path("thema_mystique_foil").isNull() ? null : jsonNode.path("thema_mystique_foil").intValue());
        dto.setThemaValorFoil(jsonNode.path("thema_valor_foil").isNull() ? null : jsonNode.path("thema_valor_foil").intValue());
        dto.setThemaCharmNonfoil(jsonNode.path("thema_charm_nonfoil").isNull() ? null : jsonNode.path("thema_charm_nonfoil").intValue());
        dto.setThemaFerocityNonfoil(jsonNode.path("thema_ferocity_nonfoil").isNull() ? null : jsonNode.path("thema_ferocity_nonfoil").intValue());
        dto.setThemaNonfoil(jsonNode.path("thema_nonfoil").isNull() ? null : jsonNode.path("thema_nonfoil").intValue());
        dto.setThemaGraceNonfoil(jsonNode.path("thema_grace_nonfoil").isNull() ? null : jsonNode.path("thema_grace_nonfoil").intValue());
        dto.setThemaMystiqueNonfoil(jsonNode.path("thema_mystique_nonfoil").isNull() ? null : jsonNode.path("thema_mystique_nonfoil").intValue());
        dto.setThemaValorNonfoil(jsonNode.path("thema_valor_nonfoil").isNull() ? null : jsonNode.path("thema_valor_nonfoil").intValue());
        dto.setUUID(jsonNode.path("uuid").asText());
        return dto;
    }
}
