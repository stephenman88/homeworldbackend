package com.arcaelo.homeworldbackend.controller;

import com.arcaelo.homeworldbackend.model.PlayerDTO;
import com.arcaelo.homeworldbackend.service.PlayerService;

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

    @GetMapping("/test")
    public ResponseEntity<String> testResponse(){
        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok().body(userName);
        }catch(Exception e){
            return ResponseEntity.status(401).body("Failure to pass test api: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<PlayerDTO> getPlayerById(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PlayerDTO> player = playerService.getPlayerByEmail(userName);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            PlayerDTO playerProfile = playerService.getPlayerByEmail(userName).get();
            PlayerDTO updatedPlayer = playerService.updatePlayer(playerProfile.getId(), playerDTO);
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
