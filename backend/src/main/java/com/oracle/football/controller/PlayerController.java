package com.oracle.football.controller;

import com.oracle.football.dto.PlayerDto;
import com.oracle.football.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerName}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable String playerName) {
        PlayerDto player = playerService.getPlayerDtoByName(playerName);
        return ResponseEntity.ok(player);
    }
}
