package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CardSet {
    @Id
    private String id;
    private String language;
    private String lastUpdate;
    private String name;
    private String prefix;
    private LocalDateTime releaseDate;
    @OneToMany(mappedBy = "cardSet", cascade = CascadeType.PERSIST)
    private List<Edition> cardEditions;

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
    public List<Edition> getCardEditions(){return cardEditions;}
    public void setCardEditions(List<Edition> cardEditions){this.cardEditions = cardEditions;}
}
