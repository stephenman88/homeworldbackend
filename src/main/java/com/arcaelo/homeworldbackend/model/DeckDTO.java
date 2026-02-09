package com.arcaelo.homeworldbackend.model;

import java.util.List;

public class DeckDTO{
    private Long id;
    private Long playerId;
    private String displayEditionId;
    private List<CardPieceDTO> deckList;
    private String hideStatus;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Long getPlayerId(){return playerId;}
    public void setPlayerId(Long playerId){this.playerId = playerId;}

    public String getDisplayEditionId(){return displayEditionId;}
    public void setDisplayEditionId(String displayEditionId){this.displayEditionId = displayEditionId;}

    public List<CardPieceDTO> getDeckList(){return deckList;}
    public void setDeckList(List<CardPieceDTO> deckList){this.deckList = deckList;}

    public String getHideStatus(){return hideStatus;}
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
}
