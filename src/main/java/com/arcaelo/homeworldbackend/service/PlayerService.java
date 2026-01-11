package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<PlayerDTO> getAllPlayers();
    Optional<PlayerDTO> getPlayerById(Long Id);
    Optional<PlayerDTO> getPlayerByEmail(String email);
    PlayerDTO saveNewPlayer(PlayerDTO playerDTO);
    PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO);
    void deletePlayer(Long id);
}
