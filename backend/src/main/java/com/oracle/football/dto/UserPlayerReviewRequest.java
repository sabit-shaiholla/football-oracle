package com.oracle.football.dto;

public record UserPlayerReviewRequest(
    Integer playerId,
    String review,
    int rating
) {

}
