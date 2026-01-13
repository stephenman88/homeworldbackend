package com.arcaelo.homeworldbackend.model;

import java.util.List;

public class DeckDTO{
    private Long id;
    private Long playerId;
    private Long displayCardId;
    private List<Long> deckListIds;
    private String hideStatus;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Long getPlayerId(){return playerId;}
    public void setPlayerId(Long playerId){this.playerId = playerId;}

    public Long getDisplayCardId(){return displayCardId;}
    public void setDisplayCardId(Long displayCardId){this.displayCardId = displayCardId;}

    public List<Long> getDeckListIds(){return deckListIds;}
    public void setDeckListIds(List<Long> deckListIds){this.deckListIds = deckListIds;}

    public String getHideStatus(){return hideStatus;}
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
}
