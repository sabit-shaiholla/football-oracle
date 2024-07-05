package com.oracle.football.dto;

public record UserPlayerReportDto(
    Integer reviewId,
    String review,
    int rating,
    Integer userId,
    Integer playerId,
    Integer reportId
) {

}
