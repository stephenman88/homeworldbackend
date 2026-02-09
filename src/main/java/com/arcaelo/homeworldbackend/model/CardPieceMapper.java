package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.arcaelo.homeworldbackend.repo.CardPieceRepository;
import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.repo.DeckRepository;
import com.arcaelo.homeworldbackend.repo.EditionRepository;

@Mapper(componentModel = "spring")
public abstract class CardPieceMapper {
    @Autowired
    protected CardPieceRepository cardPieceRepository;
    @Autowired
    protected CardRepository cardRepo;
    @Autowired
    protected EditionRepository editionRepo;
    @Autowired
    protected DeckRepository deckRepo;

    @Mapping(source = "cardData.UUID", target= "cardDataUUID")
    @Mapping(source = "editionData.UUID", target="editionDataUUID")
    @Mapping(source = "deck.id", target = "deckId")
    public abstract CardPieceDTO toDTO(CardPiece cardPiece);

    @Mapping(source = "cardDataUUID", target= "cardData.UUID")
    @Mapping(source = "editionDataUUID", target="editionData.UUID")
    @Mapping(source = "deckId", target="deck.id")
    public CardPiece toEntity(CardPieceDTO cardPieceDTO){

        CardPiece cp = new CardPiece();
        Card cardData = cardRepo.findById(cardPieceDTO.getCardDataUUID()).orElse(null);
        if(cardData == null) return null;
        Edition editionData = editionRepo.findById(cardPieceDTO.getEditionDataUUID()).orElse(null);
        if(editionData == null) return null;
        if(cardPieceDTO.getDeckId() == null){
            cp.setDeck(null);
        }else{
            Deck deck = deckRepo.findById(cardPieceDTO.getDeckId()).orElse(null);
            cp.setDeck(deck);
        }
        cp.setCardData(
            cardData
        );
        cp.setEditionData(
            editionData
        );
        cp.setImageUrl(editionData.getImage());

        return cp;
    }

    @Mapping(source = "cardDataUUID", target= "cardData.UUID")
    @Mapping(source = "editionDataUUID", target="editionData.UUID")
    @Mapping(source = "deckId", target="deck.id")
    public CardPiece toEntity(CardPieceDTO cardPieceDTO, Deck deck){

        CardPiece cp = new CardPiece();
        Card cardData = cardRepo.findById(cardPieceDTO.getCardDataUUID()).orElse(null);
        if(cardData == null) return null;
        Edition editionData = editionRepo.findById(cardPieceDTO.getEditionDataUUID()).orElse(null);
        if(editionData == null) return null;
        cp.setDeck(deck);
        cp.setCardData(
            cardData
        );
        cp.setEditionData(
            editionData
        );
        cp.setImageUrl(editionData.getImage());

        return cp;
    }
}
