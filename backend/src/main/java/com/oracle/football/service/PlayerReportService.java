package com.oracle.football.service;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.repository.PlayerReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PlayerReportService {

  private static final Logger logger = LoggerFactory.getLogger(PlayerReportService.class);
  private final PlayerReportRepository playerReportRepository;
  private final PlayerDataService playerDataService;
  private final PlayerReportMapper playerReportMapper;
  private final AIReportService aiReportService;

  public PlayerReportService(PlayerReportRepository playerReportRepository,
      @Lazy PlayerDataService playerDataService,
      PlayerReportMapper playerReportMapper,
      AIReportService aiReportService) {
    this.playerReportRepository = playerReportRepository;
    this.playerDataService = playerDataService;
    this.playerReportMapper = playerReportMapper;
    this.aiReportService = aiReportService;
  }

  public PlayerReportDto getOrCreatePlayerReport(String playerName) {
    logger.debug("Request to get or create player report for: {}", playerName);
    Player player = playerDataService.getPlayerDataByName(playerName);
    logger.debug("Retrieved player data: {}", player);
    PlayerReport playerReport = playerReportRepository.findByPlayer(player)
        .orElseGet(() -> {
          logger.debug("Generating new player report for: {}", playerName);
          PlayerReport newPlayerReport = aiReportService.generateScoutingReport(player);
          playerReportRepository.save(newPlayerReport);
          logger.debug("Saved new player report for: {}", playerName);
          return newPlayerReport;
        });
    return playerReportMapper.toDto(playerReport);
  }
}
