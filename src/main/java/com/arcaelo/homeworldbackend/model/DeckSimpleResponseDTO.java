package com.arcaelo.homeworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeckSimpleResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("display_card")
    private CardPieceDTO displayCard;
    @JsonProperty("hide_status")
    private String hideStatus;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public CardPieceDTO getDisplayCard(){return displayCard;}
    public void setDisplayCard(CardPieceDTO displayCard){this.displayCard = displayCard;}

    public String getHideStatus(){return hideStatus;}
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
}
