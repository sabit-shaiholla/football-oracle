package com.oracle.football.dto;

public record UserPlayerReviewDto(
        Integer reviewId,
        String username,
        Integer playerId,
        String playerName,
        Integer reportId,
        String review,
        int rating
) {}
