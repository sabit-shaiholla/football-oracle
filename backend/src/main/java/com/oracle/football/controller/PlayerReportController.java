package com.oracle.football.controller;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.exception.ResourceNotFoundException;
import com.oracle.football.service.PlayerReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling player report requests.
 */
@RestController
@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/player-report")
public class PlayerReportController {

  private final PlayerReportService playerReportService;
  private static final Logger logger = LoggerFactory.getLogger(PlayerReportController.class);

  /**
   * Constructs a {@code PlayerReportController} with the specified player report service.
   *
   * @param playerReportService the player report service to use
   */
  public PlayerReportController(PlayerReportService playerReportService) {
    this.playerReportService = playerReportService;
  }

  /**
   * Retrieves or creates a player report for the specified player name.
   *
   * @param playerName the name of the player for whom the report is requested
   * @return a response entity containing the player report details
   * @throws ResourceNotFoundException if the player is not found
   * @throws RuntimeException if an unexpected error occurs
   */
  @Operation(summary = "Get or create player report", description = "Retrieves or creates a player report for the specified player name.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Player report retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping()
  public ResponseEntity<PlayerReportDto> getPlayerReport(@RequestParam String playerName) {
    logger.info("Received request for player report: {}", playerName);
    try {
      PlayerReportDto playerReportDto = playerReportService.getOrCreatePlayerReport(playerName);
      logger.info("Successfully retrieved player report for: {}", playerName);
      return ResponseEntity.ok(playerReportDto);
    } catch (ResourceNotFoundException e) {
      logger.error("Player not found: {}", playerName);
      throw e;
    } catch (RuntimeException e) {
      logger.error("An error occurred while getting player report: {}", playerName);
      throw e;
    }
  }
}
