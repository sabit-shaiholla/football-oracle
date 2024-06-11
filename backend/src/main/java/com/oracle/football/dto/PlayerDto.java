package com.oracle.football.dto;

import java.util.Map;

public record PlayerDto(
        Integer playerId,
        String playerName,
        String playerPosition,
        int playerAge,
        String birthday,
        String team,
        Map<String, String> statistics
) {}
