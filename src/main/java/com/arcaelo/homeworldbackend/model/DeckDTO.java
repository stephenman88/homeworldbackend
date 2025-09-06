package com.arcaelo.homeworldbackend.model;

public class DeckDTO{
    private Long id;
    private Long playerId;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Long getPlayerId(){return playerId;}
    public void setPlayerId(Long playerId){this.playerId = playerId;}
}
