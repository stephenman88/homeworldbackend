package com.arcaelo.homeworldbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeckRequestBodyDTO {
    private String displayEditionId;
    private List<CardPieceDTO> deckList;
    private String hideStatus;

    @JsonProperty("display_edition_id")
    public void setDisplayEditionId(String displayEditionId){
        this.displayEditionId = displayEditionId;
    }
    public String getDisplayEditionId(){return displayEditionId;}

    @JsonProperty("deck_list")
    public void setDeckList(List<CardPieceDTO> deckList){
        this.deckList = deckList;
    }
    public List<CardPieceDTO> getDeckList(){return deckList;}

    @JsonProperty("hide_status")
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
    public String getHideStatus(){return hideStatus;}
}
