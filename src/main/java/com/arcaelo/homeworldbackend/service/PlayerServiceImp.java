package com.arcaelo.homeworldbackend.service;

import com.arcaelo.homeworldbackend.model.DeckMapper;
import com.arcaelo.homeworldbackend.model.Player;
import com.arcaelo.homeworldbackend.model.PlayerDTO;
import com.arcaelo.homeworldbackend.model.PlayerMapper;
import com.arcaelo.homeworldbackend.repo.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImp implements PlayerService{
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerServiceImp(PlayerRepository playerRepository, DeckMapper deckMapper, PlayerMapper playerMapper){
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
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
    public Optional<PlayerDTO> getPlayerByEmail(String email){
        return playerRepository.findByEmail(email).map(this::convertToDTO);
    }

    @Override
    public PlayerDTO saveNewPlayer(PlayerDTO playerDTO){
        Player player = convertToEntity(playerDTO);
        if(playerRepository.findByEmail(player.getEmail()).isPresent()){
            return null;
        }
        Player savedPlayer = playerRepository.save(player);
        return convertToDTO(savedPlayer);
    }

    @Override
    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO){
        Player player = playerRepository.findById(id).orElseThrow();
        player.setEmail(playerDTO.getEmail());
        Player updatedPlayer = playerRepository.save(player);
        return convertToDTO(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id){
        playerRepository.deleteById(id);
    }

    private PlayerDTO convertToDTO(Player player){
        return playerMapper.toDTO(player);
    }

    private Player convertToEntity(PlayerDTO playerDTO){
        return playerMapper.toEntity(playerDTO);
    }
}
