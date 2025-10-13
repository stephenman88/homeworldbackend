package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.OrientationDTO;
import com.arcaelo.homeworldbackend.repo.EditionRepository;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CardMapper {
    @Autowired
    protected EditionRepository editionRepository;

    public void setEditionRepository(EditionRepository editionRepository) {
        this.editionRepository = editionRepository;
    }

    @Autowired
    protected EditionMapper editionMapper;

    @Mapping(source="editions", target="editionIds")
    public abstract CardDTO toDTO(Card card);

    protected List<String> mapEditionsToEditionIds(List<Edition> editions){
        if(editions == null)return null;
        return editions.stream().map(e -> e.getUUID()).toList();
    }

    @Mapping(source="editionIds", target="editions")
    public Card toEntity(CardDTO cardDTO){
        Card card = new Card();
        card.setClasses(cardDTO.getClasses());
        card.setCostMemory(cardDTO.getCostMemory());
        card.setCostReserve(cardDTO.getCostReserve());
        card.setDurability(cardDTO.getDurability());
        card.setElements(cardDTO.getElements());
        card.setEffect(cardDTO.getEffect());
        card.setEffectRaw(cardDTO.getEffectRaw());
        card.setFlavor(cardDTO.getFlavor());
        card.setLastUpdate(cardDTO.getLastUpdate());
        card.setLegality(cardDTO.getLegality());
        card.setLevel(cardDTO.getLevel());
        card.setLife(cardDTO.getLife());
        card.setName(cardDTO.getName());
        card.setPower(cardDTO.getPower());
        card.setReferencedBy(cardDTO.getReferencedBy());
        card.setReferences(cardDTO.getReferences());
        card.setRule(cardDTO.getRule());
        card.setSlug(cardDTO.getSlug());
        card.setSpeed(cardDTO.getSpeed());
        card.setSubtypes(cardDTO.getSubtypes());
        card.setTypes(cardDTO.getTypes());
        card.setUUID(cardDTO.getUUID());

        ArrayList<Edition> editions = new ArrayList<Edition>();
        for (String id : cardDTO.getEditionIds()){
            try{
                Edition edition = editionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Could not find edition id: " + id));
                editions.add(edition);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        card.setEditions(editions);
        return card;
    }

    public CardResponseDTO toResponseDTO(Card card){
        CardResponseDTO dto = new CardResponseDTO();
        dto.setClasses(card.getClasses());
        dto.setCostMemory(card.getCostMemory());
        dto.setCostReserve(card.getCostReserve());
        dto.setDurability(card.getDurability());
        dto.setElements(card.getElements());
        dto.setEffect(card.getEffect());
        dto.setEffectRaw(card.getEffectRaw());
        dto.setFlavor(card.getFlavor());
        dto.setLastUpdate(card.getLastUpdate());
        dto.setLegality(card.getLegality());
        dto.setLevel(card.getLevel());
        dto.setLife(card.getLife());
        dto.setName(card.getName());
        dto.setPower(card.getPower());
        dto.setReferencedBy(card.getReferencedBy());
        dto.setReferences(card.getReferences());
        dto.setRule(card.getRule());
        dto.setSpeed(card.getSpeed());
        dto.setSlug(card.getSlug());
        dto.setSubtypes(card.getSubtypes());
        dto.setTypes(card.getTypes());
        dto.setUUID(card.getUUID());

        List<EditionResponseDTO> editionResponses = new ArrayList<EditionResponseDTO>();
        for(Edition edition : card.getEditions()){
            editionResponses.add(
                editionMapper.toEditionResponseDTO(edition)
            );
        }
        dto.setEditionResponseDTOs(editionResponses);

        return dto;
    }

    public OrientationDTO toOrientationDTO(Card card, String editionId){
        OrientationDTO dto = new OrientationDTO();

        dto.setClasses(card.getClasses());
        dto.setCostMemory(card.getCostMemory());
        dto.setCostReserve(card.getCostReserve());
        dto.setDurability(card.getDurability());
        dto.setElements(card.getElements());
        dto.setEffect(card.getEffect());
        dto.setEffectRaw(card.getEffectRaw());
        dto.setFlavor(card.getFlavor());
        dto.setLevel(card.getLevel());
        dto.setLife(card.getLife());
        dto.setName(card.getName());
        dto.setPower(card.getPower());
        dto.setSpeed(card.getSpeed());
        dto.setSlug(card.getSlug());
        dto.setSubtypes(card.getSubtypes());
        dto.setTypes(card.getTypes());
        dto.setUUID(card.getUUID());
        for(Edition e : card.getEditions()){
            if(e.getUUID().equals(editionId)){
                dto.setEditionResponseDTO(
                    editionMapper.toInnerEditionResponseDTO(e)
                );
                break;
            }
        }

        return dto;
    }
}
