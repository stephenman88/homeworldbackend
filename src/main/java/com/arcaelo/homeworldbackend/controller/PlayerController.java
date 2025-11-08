package com.arcaelo.homeworldbackend.controller;

import com.arcaelo.homeworldbackend.model.PlayerDTO;
import com.arcaelo.homeworldbackend.service.PlayerService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDTO> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/test")
    public ResponseEntity<String> testResponse(){
        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok().body(userName);
        }catch(Exception e){
            return ResponseEntity.status(401).body("Failure to pass test api: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id){
        Optional<PlayerDTO> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PlayerDTO createPlayer(@RequestBody PlayerDTO playerDTO){
        return playerService.savePlayer(playerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO){
        try{
            PlayerDTO updatedPlayer = playerService.updatePlayer(id, playerDTO);
            return ResponseEntity.ok(updatedPlayer);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
