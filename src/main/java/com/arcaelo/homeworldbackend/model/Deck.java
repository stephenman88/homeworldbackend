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
}
