package com.arcaelo.homeworldbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeckResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("display_card_url")
    private String displayCardUrl;
    @JsonProperty("deck_list")
    private List<CardPieceDTO> deckList;
    @JsonProperty("hide_status")
    private String hideStatus;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getDisplayCardUrl(){return displayCardUrl;}
    public void setDisplayCardUrl(String displayCardUrl){this.displayCardUrl = displayCardUrl;}

    public List<CardPieceDTO> getDeckList(){return deckList;}
    public void setDeckList(List<CardPieceDTO> deckList){this.deckList = deckList;}

    public String getHideStatus(){return hideStatus;}
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
}
