package com.oracle.football.controller;

import com.oracle.football.dto.PlayerDto;
import com.oracle.football.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling player-related requests.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/players")
public class PlayerController {

  private final PlayerService playerService;

  /**
   * Constructs a {@code PlayerController} with the specified player service.
   *
   * @param playerService the player service to use
   */
  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  /**
   * Retrieves the player details for the specified player name.
   *
   * @param playerName the name of the player to retrieve
   * @return a response entity containing the player details
   */
  @Operation(summary = "Get player details", description = "Retrieves the player details for the specified player name.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Player details retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{playerName}")
  public ResponseEntity<PlayerDto> getPlayer(@PathVariable String playerName) {
    PlayerDto player = playerService.getPlayerDtoByName(playerName);
    return ResponseEntity.ok(player);
  }
}
