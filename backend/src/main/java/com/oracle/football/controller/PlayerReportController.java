package com.oracle.football.controller;

import com.oracle.football.model.Player;
import com.oracle.football.service.PlayerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/player-report")
public class PlayerReportController {

    @Autowired
    private PlayerDataService playerDataService;

    @GetMapping
    public Player getPlayerReport(@RequestParam String playerName) throws IOException {
        return playerDataService.getPlayerDataByName(playerName);
    }
}
