package com.arcaelo.homeworldbackend.model;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.arcaelo.homeworldbackend.repo.CardPieceRepository;
import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.EditionRepository;

@Mapper(componentModel = "spring")
public abstract class CardPieceMapper {
    @Autowired
    protected CardPieceRepository cardPieceRepository;
    @Autowired
    protected CardRepository cardRepo;
    @Autowired
    protected EditionRepository editionRepo;

    @Mapping(source = "cardData.UUID", target= "cardDataUUID")
    @Mapping(source = "editionData.UUID", target="editionDataUUID")
    public abstract CardPieceDTO toDTO(CardPiece cardPiece);

    public CardPiece toEntity(CardPieceDTO cardPieceDTO){
        if(cardPieceDTO.getCardDataUUID() == null || cardPieceDTO.getEditionDataUUID() == null){
            return null;
        }

        Specification<CardPiece> spec = Specification.allOf(
            filterByCardUUI(cardPieceDTO.getCardDataUUID()),
            filterByEditionUUID(cardPieceDTO.getEditionDataUUID())
        );
        Optional<CardPiece> existingCP = cardPieceRepository.findOne(spec);

        if(existingCP.isPresent() && existingCP.get() != null){
            return existingCP.get();
        }

        CardPiece cp = new CardPiece();
        Card cardData = cardRepo.findById(cardPieceDTO.getCardDataUUID()).orElse(null);
        if(cardData == null) return null;
        Edition editionData = editionRepo.findById(cardPieceDTO.getEditionDataUUID()).orElse(null);
        if(editionData == null) return null;
        cp.setCardData(
            cardData
        );
        cp.setEditionData(
            editionData
        );
        cp.setImageUrl(editionData.getImage());
        CardPiece ret = cardPieceRepository.save(cp);

        return ret;
    }

    private Specification<CardPiece> filterByCardUUI(String cardUUID){
        return(root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("card_uuid"), cardUUID);
        };
    }

    private Specification<CardPiece> filterByEditionUUID(String editionUUID){
        return(root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("edition_uuid"), editionUUID);
        };
    }
}
