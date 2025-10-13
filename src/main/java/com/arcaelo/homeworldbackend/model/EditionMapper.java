package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.CardSetResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.OrientationDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.OrientationDTO.InnerEditionResponseDTO;
import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.CardSetRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class EditionMapper {
    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CardSetRepository cardSetRepository;

    @Autowired
    protected CardSetMapper cardSetMapper;
    @Autowired
    @Lazy
    protected CardMapper cardMapper;

    @Mapping(source="card.UUID", target="cardId")
    @Mapping(source="cardSet.id", target="cardSetId")
    @Mapping(source="otherOrientation", target="otherOrientationCardId")
    public abstract EditionDTO toDTO(Edition edition);

    protected Set<String> mapOtherOrientationIds(Set<Card> cards){
        Set<String> ooStrings = new HashSet<String>();
        for(Card card : cards){
            ooStrings.add(card.getUUID());
        }
        return ooStrings;
    }

    @Mapping(source="cardId", target="card.UUID")
    @Mapping(source="cardSetId", target="cardSet.id")
    public Edition toEntity(EditionDTO editionDTO){
        Edition edition = new Edition();
        Card card = cardRepository.findById(editionDTO.getCardId()).orElseThrow(() -> new IllegalArgumentException("Failed to find card with id: " + editionDTO.getCardId()));
        edition.setCard(card);
        edition.setCollaborators(editionDTO.getCollaborators());
        edition.setCollectorNumber(editionDTO.getCollectorNumber());
        edition.setConfiguration(editionDTO.getConfiguration());
        edition.setEffect(editionDTO.getEffect());
        edition.setEffectHtml(editionDTO.getEffectHtml());
        edition.setEffectRaw(editionDTO.getEffectRaw());
        edition.setFlavor(editionDTO.getFlavor());
        edition.setIllustrator(editionDTO.getIllustrator());
        edition.setImage(editionDTO.getImage());
        edition.setOrientation(editionDTO.getOrientation());
        Set<Card> oolist = new HashSet<Card>();
        for (String id : editionDTO.getOtherOrientationCardId()){
            Card oo = cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Failed to find other orientation id: " + id));
            oolist.add(oo);
        }
        edition.setOtherOrientation(oolist);
        edition.setRarity(editionDTO.getRarity());
        edition.setSlug(editionDTO.getSlug());
        CardSet cardSet = cardSetRepository.findById(editionDTO.getCardSetId()).orElseThrow(() -> new IllegalArgumentException("Failed to find card set id: " + editionDTO.getCardSetId()));
        edition.setCardSet(cardSet);
        edition.setThemaCharmFoil(editionDTO.getThemaCharmFoil());
        edition.setThemaFerocityFoil(editionDTO.getThemaFerocityFoil());
        edition.setThemaFoil(editionDTO.getThemaFoil());
        edition.setThemaGraceFoil(editionDTO.getThemaGraceFoil());
        edition.setThemaMystiqueFoil(editionDTO.getThemaMystiqueFoil());
        edition.setThemaValorFoil(editionDTO.getThemaValorFoil());
        edition.setThemaCharmNonfoil(editionDTO.getThemaCharmNonfoil());
        edition.setThemaFerocityNonfoil(editionDTO.getThemaFerocityNonfoil());
        edition.setThemaNonfoil(editionDTO.getThemaNonfoil());
        edition.setThemaGraceNonfoil(editionDTO.getThemaGraceNonfoil());
        edition.setThemaMystiqueNonfoil(editionDTO.getThemaMystiqueNonfoil());
        edition.setThemaValorNonfoil(editionDTO.getThemaValorNonfoil());
        edition.setUUID(editionDTO.getUUID());
        edition.setOtherOrientationEditionIds(editionDTO.getOtherOrientationEditionIds());

        return edition;
    }

    @Mapping(source="card.UUID", target="cardId")
    @Mapping(source="cardSet.id", target="cardSet.id")
    public InnerEditionResponseDTO toInnerEditionResponseDTO(Edition edition){
        InnerEditionResponseDTO dto = new InnerEditionResponseDTO();
        dto.setCardId(edition.getCard().getUUID());
        dto.setCollaborators(edition.getCollaborators());
        dto.setCollectorNumber(edition.getCollectorNumber());
        dto.setConfiguration(edition.getConfiguration());
        dto.setEffect(edition.getEffect());
        dto.setEffectHtml(edition.getEffectHtml());
        dto.setEffectRaw(edition.getEffectRaw());
        dto.setFlavor(edition.getFlavor());
        dto.setIllustrator(edition.getIllustrator());
        dto.setImage(edition.getImage());
        dto.setOrientation(edition.getOrientation());
        dto.setRarity(edition.getRarity());
        dto.setSlug(edition.getSlug());

        CardSetResponseDTO cardSetDTO = cardSetMapper.toResponseDTO(edition.getCardSet());
        dto.setCardSet(cardSetDTO);

        dto.setThemaCharmFoil(edition.getThemaCharmFoil());
        dto.setThemaFerocityFoil(edition.getThemaFerocityFoil());
        dto.setThemaFoil(edition.getThemaFoil());
        dto.setThemaGraceFoil(edition.getThemaGraceFoil());
        dto.setThemaMystiqueFoil(edition.getThemaMystiqueFoil());
        dto.setThemaValorFoil(edition.getThemaValorFoil());
        dto.setThemaCharmNonfoil(edition.getThemaCharmNonfoil());
        dto.setThemaFerocityNonfoil(edition.getThemaFerocityNonfoil());
        dto.setThemaNonfoil(edition.getThemaNonfoil());
        dto.setThemaGraceNonfoil(edition.getThemaGraceNonfoil());
        dto.setThemaMystiqueNonfoil(edition.getThemaMystiqueNonfoil());
        dto.setThemaValorNonfoil(edition.getThemaValorNonfoil());
        dto.setUUID(edition.getUUID());

        return dto;
    }

    @Mapping(source="card.UUID", target="cardId")
    public EditionResponseDTO toEditionResponseDTO(Edition edition){
        EditionResponseDTO dto = new EditionResponseDTO();

        dto.setCardId(edition.getCard().getUUID());
        dto.setCollaborators(edition.getCollaborators());
        dto.setCollectorNumber(edition.getCollectorNumber());
        dto.setConfiguration(edition.getConfiguration());
        dto.setEffect(edition.getEffect());
        dto.setEffectHtml(edition.getEffectHtml());
        dto.setEffectRaw(edition.getEffectRaw());
        dto.setFlavor(edition.getFlavor());
        dto.setIllustrator(edition.getIllustrator());
        dto.setImage(edition.getImage());
        dto.setOrientation(edition.getOrientation());

        List<OrientationDTO> orientationDTOs = new ArrayList<OrientationDTO>();
        for(Card or : edition.getOtherOrientation()){
            for(Edition e : or.getEditions()){
                if(edition.getOtherOrientationEditionIds().contains(e.getUUID())){
                    orientationDTOs.add(
                        cardMapper.toOrientationDTO(or, e.getUUID())
                    );
                }
            }
        }
        dto.setOtherOrientationCard(orientationDTOs);

        dto.setRarity(edition.getRarity());
        dto.setSlug(edition.getSlug());
        dto.setCardSetResponseDTO(
            cardSetMapper.toResponseDTO(edition.getCardSet())
        );

        dto.setThemaCharmFoil(edition.getThemaCharmFoil());
        dto.setThemaFerocityFoil(edition.getThemaFerocityFoil());
        dto.setThemaFoil(edition.getThemaFoil());
        dto.setThemaGraceFoil(edition.getThemaGraceFoil());
        dto.setThemaMystiqueFoil(edition.getThemaMystiqueFoil());
        dto.setThemaValorFoil(edition.getThemaValorFoil());
        dto.setThemaCharmNonfoil(edition.getThemaCharmNonfoil());
        dto.setThemaFerocityNonfoil(edition.getThemaFerocityNonfoil());
        dto.setThemaNonfoil(edition.getThemaNonfoil());
        dto.setThemaGraceNonfoil(edition.getThemaGraceNonfoil());
        dto.setThemaMystiqueNonfoil(edition.getThemaMystiqueNonfoil());
        dto.setThemaValorNonfoil(edition.getThemaValorNonfoil());
        dto.setUUID(edition.getUUID());

        return dto;
    }
}
