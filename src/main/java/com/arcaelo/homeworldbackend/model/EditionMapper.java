package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.CardSetRepository;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EditionMapper {
    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CardSetRepository cardSetRepository;

    @Mapping(source="card.UUID", target="cardId")
    @Mapping(source="cardSet.id", target="cardSetId")
    @Mapping(source="otherOrientation", target="otherOrientationCardId")
    public abstract EditionDTO toDTO(Edition edition);

    protected List<String> mapOtherOrientation(List<Card> cards){
        return cards.stream().map(Card::getUUID).toList();
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
        ArrayList<Card> oolist = new ArrayList<Card>();
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

        return edition;
    }
}
