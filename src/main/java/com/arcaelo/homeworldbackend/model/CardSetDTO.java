package com.arcaelo.homeworldbackend.model;

import java.time.LocalDateTime;
import java.util.Set;

public class CardSetDTO{
    private String id;
    private String language;
    private String lastUpdate;
    private String name;
    private String prefix;
    private LocalDateTime releaseDate;
    private Set<String> cardEditionIds;

    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getLanguage(){return language;}
    public void setLanguage(String language){this.language = language;}
    public String getLastUpdate(){return lastUpdate;}
    public void setLastUpdate(String lastUpdate){this.lastUpdate = lastUpdate;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getPrefix(){return prefix;}
    public void setPrefix(String prefix){this.prefix = prefix;}
    public LocalDateTime getReleaseDate(){return releaseDate;}
    public void setReleaseDate(LocalDateTime releaseDate){this.releaseDate = releaseDate;}
    public Set<String> getCardEditionIds(){return cardEditionIds;}
    public void setCardEditionsIds(Set<String> cardEditionIds){this.cardEditionIds = cardEditionIds;}
}