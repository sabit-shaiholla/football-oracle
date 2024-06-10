package com.oracle.football.dto;

import java.util.List;

public record PlayerReportDto(
        Integer reportId,
        String playerName,
        String playerStrengths,
        String playerWeaknesses,
        String playerSummary,
        List<UserPlayerReviewDto> reviews
) {}
