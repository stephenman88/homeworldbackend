package com.arcaelo.homeworldbackend.model;

import java.util.Set;

public class PlayerDTO {
    private Long id;
    private String email;
    private Set<Long> deckIds;
    private String name;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public Set<Long> getDeckIds(){return deckIds;}
    public void setDeckIds(Set<Long> deckIds){this.deckIds = deckIds;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
}
