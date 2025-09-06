package com.arcaelo.homeworldbackend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Mapper(componentModel = "spring")
public abstract class PlayerMapper{
    @Mapping(source = "decks", target="deckIds")
    public abstract PlayerDTO toDTO(Player player);

    protected Set<Long> mapDecksToIds(Set<Deck> decks){
        if (decks == null) return null;
        return decks.stream().map(Deck::getId).collect(Collectors.toSet());
    }

    @Mapping(source="deckIds", target="decks")
    public Player toEntity(PlayerDTO playerDTO){
        if (playerDTO == null) return null;
        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setEmail(playerDTO.getEmail());

        if (playerDTO.getDeckIds() != null){
            Set<Deck> set = new HashSet<>();
            for (Long id : playerDTO.getDeckIds()){
                Deck deck = new Deck();
                deck.setId(id);
                set.add(deck);
            }
            player.setDecks(set);
        }
        return player;
    }
}
