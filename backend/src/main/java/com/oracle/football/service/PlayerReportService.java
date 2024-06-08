package com.oracle.football.service;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.repository.PlayerReportRepository;
import com.oracle.football.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerReportService {

    private final PlayerRepository playerRepository;
    private final PlayerReportRepository playerReportRepository;
    private final PlayerDataService playerDataService;
    private final PlayerReportMapper playerReportMapper;
    private final AIReportService aiReportService;

    public PlayerReportService(PlayerRepository playerRepository,
                               PlayerReportRepository playerReportRepository,
                               PlayerDataService playerDataService,
                               PlayerReportMapper playerReportMapper,
                               AIReportService aiReportService) {
        this.playerRepository = playerRepository;
        this.playerReportRepository = playerReportRepository;
        this.playerDataService = playerDataService;
        this.playerReportMapper = playerReportMapper;
        this.aiReportService = aiReportService;
    }

    public PlayerReportDto getOrCreatePlayerReport(String playerName) {

        return playerRepository.findByPlayerName(playerName)
                .map(player -> playerReportRepository.findByPlayer(player)
                        .map(playerReportMapper::toDto)
                        .orElseGet(() -> playerReportMapper.toDto(createPlayerReport(player))))
                .orElseGet(() -> playerReportMapper.toDto(createPlayerReport(playerDataService.getPlayerDataByName(playerName))));

    }

    private PlayerReport createPlayerReport(Player player) {
        PlayerReport playerReport = aiReportService.generateScoutingReport(player);
        return playerReportRepository.save(playerReport);
    }
}
