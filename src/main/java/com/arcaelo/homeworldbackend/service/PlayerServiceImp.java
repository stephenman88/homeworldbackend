package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.Player;
import com.arcaelo.homeworldbackend.model.PlayerDTO;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImp implements PlayerService{
    private final PlayerRepository playerRepository;

    public PlayerServiceImp(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @Override
    public List<PlayerDTO> getAllPlayers(){
        return playerRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PlayerDTO> getPlayerById(Long id){
        return playerRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public PlayerDTO savePlayer(PlayerDTO playerDTO){
        Player player = convertToEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return convertToDTO(savedPlayer);
    }

    @Override
    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO){
        Player player = playerRepository.findById(id).orElseThrow();
        player.setEmail(playerDTO.email());
        player.setPassword(playerDTO.password());
        Player updatedPlayer = playerRepository.save(player);
        return convertToDTO(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id){
        playerRepository.deleteById(id);
    }

    private PlayerDTO convertToDTO(Player player){
        return new PlayerDTO(player.getId(), player.getEmail(), player.getPassword());
    }

    private Player convertToEntity(PlayerDTO playerDTO){
        Player player = new Player();
        player.setEmail(playerDTO.email());
        player.setPassword(playerDTO.password());
        return player;
    }
}
