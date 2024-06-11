package com.oracle.football.service;

import com.oracle.football.dto.PlayerDto;
import com.oracle.football.model.Player;
import com.oracle.football.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findPlayerByName(String playerName) {
        return playerRepository.findByPlayerName(playerName);
    }

    public PlayerDto getPlayerDtoByName(String playerName) {
        Player player = findPlayerByName(playerName)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return new PlayerDto(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getPlayerPosition(),
                player.getPlayerAge(),
                player.getBirthday(),
                player.getTeam(),
                player.getStatistics()
        );
    }
}
