package com.oracle.football.dto;

public record UserPlayerReviewDto(
        Integer reviewId,
        String review,
        int rating,
        Integer userId,
        Integer playerId,
        Integer reportId
) {}
