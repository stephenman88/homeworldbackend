package com.arcaelo.homeworldbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeckRequestBodyDTO {
    private CardPieceDTO displayCard;
    private List<CardPieceDTO> deckList;
    private String hideStatus;

    @JsonProperty("display_card")
    public void setDisplayCard(CardPieceDTO displayCard){
        this.displayCard = displayCard;
    }
    public CardPieceDTO getDisplayCard(){return displayCard;}

    @JsonProperty("deck_list")
    public void setDeckList(List<CardPieceDTO> deckList){
        this.deckList = deckList;
    }
    public List<CardPieceDTO> getDeckList(){return deckList;}

    @JsonProperty("hide_status")
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
    public String getHideStatus(){return hideStatus;}
}
