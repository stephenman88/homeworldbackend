package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "display_card_edition_id", referencedColumnName = "uuid")
    private Edition displayEdition;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<CardPiece> deckList;
    private String hideStatus;

    public static class hideStatuses{
        public static String PUBLIC = "PUBLIC";
        public static String PRIVATE = "PRIVATE";
        public static String HIDDEN = "HIDDEN";
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Player getPlayer(){return player;}
    public void setPlayer(Player player){this.player = player;}

    public Edition getDisplayEdition(){return displayEdition;}
    public void setDisplayEdition(Edition displayEdition){this.displayEdition = displayEdition;}

    public List<CardPiece> getDeckList(){return deckList;}
    public void setDeckList(List<CardPiece> deckList){this.deckList = deckList;}

    public String getHideStatus(){return hideStatus;}
    public void setHideStatus(String hideStatus){this.hideStatus = hideStatus;}
}
