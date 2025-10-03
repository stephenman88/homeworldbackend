package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.EditionRepository;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CardMapper {
    @Autowired
    protected EditionRepository editionRepository;

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
}
