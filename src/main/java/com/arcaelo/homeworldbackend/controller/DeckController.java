package com.arcaelo.homeworldbackend.controller;

import com.arcaelo.homeworldbackend.model.Deck;
import com.arcaelo.homeworldbackend.model.DeckDTO;
import com.arcaelo.homeworldbackend.model.DeckRequestBodyDTO;
import com.arcaelo.homeworldbackend.model.DeckResponseDTO;
import com.arcaelo.homeworldbackend.model.DeckSimpleResponseDTO;
import com.arcaelo.homeworldbackend.model.PlayerDTO;
import com.arcaelo.homeworldbackend.service.DeckService;
import com.arcaelo.homeworldbackend.service.PlayerService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deck")
public class DeckController {
    private final DeckService deckService;
    private final PlayerService playerService;

    public DeckController(DeckService deckService, PlayerService playerService){
        this.deckService = deckService;
        this.playerService = playerService;
    }
    
    @GetMapping
    public ResponseEntity<List<DeckSimpleResponseDTO>> getOwnDecks(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            PlayerDTO playerDTO = playerService.getPlayerByEmail(email).orElseThrow();
            List<DeckSimpleResponseDTO> allDeckLists = deckService.getAllDecksAsSimpleResponse(playerDTO.getId());
            return ResponseEntity.ok().body(allDeckLists);
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/random")
    public List<DeckSimpleResponseDTO> getSampleDecks(){
        return deckService.getSampleDecks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckResponseDTO> getDeckById(@PathVariable Long id){
        DeckDTO deck = deckService.getDeckById(id).orElseThrow();
        if(deck.getHideStatus().equals(Deck.hideStatuses.HIDDEN)){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            PlayerDTO playerDTO = playerService.getPlayerByEmail(email).orElseThrow();
            if(deck.getPlayerId() == playerDTO.getId()){
                return ResponseEntity.ok().body(deckService.convertToResponseDTO(deck));
            }
        }else{
            return ResponseEntity.ok().body(deckService.convertToResponseDTO(deck));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/new", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckResponseDTO> createDeck(@RequestBody DeckRequestBodyDTO deckBodyDTO){
        System.out.println("DeckController: deck_list size" + deckBodyDTO.getDeckList().size());
        System.out.println("DeckController: hidden_status" + deckBodyDTO.getHideStatus());
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            PlayerDTO playerDTO = playerService.getPlayerByEmail(email).orElseThrow();
            DeckDTO deckDTO = deckService.convertToDTO(deckBodyDTO);
            deckDTO.setId(null);
            deckDTO.setPlayerId(playerDTO.getId());
            System.out.println("DeckController: deck_list size" + deckDTO.getDeckList().size());
            System.out.println("DeckController: hidden_status" + deckDTO.getHideStatus());
            return ResponseEntity.ok().body(deckService.convertToResponseDTO(deckService.saveDeck(deckDTO)));
        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeckResponseDTO> updateDeck(@PathVariable Long id, @RequestBody DeckRequestBodyDTO deckBodyDTO){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            PlayerDTO playerDTO = playerService.getPlayerByEmail(email).orElseThrow();
            DeckDTO deckDTO = deckService.convertToDTO(deckBodyDTO);
            deckDTO.setId(id);
            deckDTO.setPlayerId(playerDTO.getId());
            DeckResponseDTO response = deckService.convertToResponseDTO(deckService.updateDeck(id, deckDTO));
            return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        PlayerDTO playerDTO = playerService.getPlayerByEmail(email).orElseThrow();
        DeckDTO deckDTO = deckService.getDeckById(id).orElseThrow();
        if(deckDTO.getPlayerId() == playerDTO.getId()){
            deckService.deleteDeck(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
