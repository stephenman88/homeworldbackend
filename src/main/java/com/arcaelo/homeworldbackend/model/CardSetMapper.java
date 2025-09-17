package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.arcaelo.homeworldbackend.repo.EditionRepository;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

@Mapper(componentModel = "spring")
public abstract class CardSetMapper {
    @Autowired
    protected EditionRepository editionRepository;

    @Mapping(source="cardEditions", target="cardEditionIds")
    public CardSetDTO toDTO(CardSet cardSet){
        CardSetDTO csd = new CardSetDTO();
        csd.setId(cardSet.getId());
        csd.setLanguage(cardSet.getLanguage());
        csd.setLastUpdate(cardSet.getLastUpdate());
        csd.setName(cardSet.getName());
        csd.setPrefix(cardSet.getPrefix());
        Set<String> editionIds = new HashSet<String>();
        for (Edition edition : cardSet.getCardEditions()){
            if(!editionIds.contains(edition.getUUID())){
                editionIds.add(edition.getUUID());
            }
        }
        csd.setCardEditionsIds(editionIds);
        return csd;
    }

    @Mapping(source="cardEditionIds", target="cardEditions")
    public CardSet toEntity(CardSetDTO cardSetDTO){
        CardSet cs = new CardSet();
        cs.setId(cardSetDTO.getId());
        cs.setLanguage(cardSetDTO.getLanguage());
        cs.setLastUpdate(cardSetDTO.getLastUpdate());
        cs.setName(cardSetDTO.getName());
        cs.setPrefix(cardSetDTO.getPrefix());
        cs.setReleaseDate(cardSetDTO.getReleaseDate());
        ArrayList<Edition> editions = new ArrayList<Edition>();
        for (String editionId : cardSetDTO.getCardEditionIds())
        {
            Edition edition = editionRepository.findById(editionId).orElse(null);
            if(edition != null){
                editions.add(edition);
            }
        }
        cs.setCardEditions(editions);
        return cs;
    }
}
