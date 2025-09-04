package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Deck> decks;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public Set<Deck> getDecks(){return decks;}
    public void setDecks(Set<Deck> decks){this.decks = decks;}
}
