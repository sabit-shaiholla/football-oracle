package com.oracle.football.controller;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.service.PlayerReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/player-report")
public class PlayerReportController {

    private final PlayerReportService playerReportService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerReportController.class);

    public PlayerReportController(PlayerReportService playerReportService) {
        this.playerReportService = playerReportService;
    }

    @GetMapping
    public ResponseEntity<PlayerReportDto> getPlayerReport(@RequestParam String playerName) {
        logger.info("Received request to get player report for player: {}", playerName);
        PlayerReportDto playerReportDto = playerReportService.getOrCreatePlayerReport(playerName);
        logger.info("Returning player report for {}", playerName);
        return ResponseEntity.ok(playerReportDto);
    }
}
