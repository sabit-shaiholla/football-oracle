package com.oracle.football.dto;

public record UserPlayerReviewDto(
        Long reviewId,
        String review,
        int rating,
        Long userId,
        Long playerId,
        Long reportId
) {}
