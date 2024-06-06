package com.oracle.football.service;

import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.repository.PlayerReportRepository;
import com.oracle.football.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerReportRepository playerReportRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findPlayerByName(String playerName) {
        return playerRepository.findByPlayerName(playerName);
    }

    public PlayerReport generatePlayerReport(String playerName) {
        Player player = findPlayerByName(playerName).orElseThrow(() -> new RuntimeException("Player not found"));
        //TODO: generate player report
        PlayerReport playerReport = new PlayerReport();
        return playerReportRepository.save(playerReport);
    }

}
