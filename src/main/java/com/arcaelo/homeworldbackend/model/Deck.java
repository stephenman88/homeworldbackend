package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name="decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Player getPlayer(){return player;}
    public void setPlayer(Player player){this.player = player;}
}
