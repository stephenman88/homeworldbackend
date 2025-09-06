package com.arcaelo.homeworldbackend.controller;

import com.arcaelo.homeworldbackend.model.DeckDTO;
import com.arcaelo.homeworldbackend.service.DeckService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
    private final DeckService deckService;

    public DeckController(DeckService deckService){
        this.deckService = deckService;
    }

    @GetMapping()
    public List<DeckDTO> getAllDecks(){
        return deckService.getAllDecks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable Long id){
        Optional<DeckDTO> deck = deckService.getDeckById(id);
        return deck.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public DeckDTO createDeck(@RequestBody DeckDTO deckDTO){
        return deckService.saveDeck(deckDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long id, @RequestBody DeckDTO deckDTO){
        try{
            DeckDTO dto = deckService.updateDeck(id, deckDTO);
            return ResponseEntity.ok().body(dto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id){
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
