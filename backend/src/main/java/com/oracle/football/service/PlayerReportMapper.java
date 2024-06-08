package com.oracle.football.service;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.dto.UserPlayerReviewDto;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.model.UserPlayerReview;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerReportMapper {

    public PlayerReportDto toDto(PlayerReport playerReport){
        return new PlayerReportDto(
                playerReport.getReportId(),
                playerReport.getPlayer().getPlayerName(),
                playerReport.getPlayerStrengths(),
                playerReport.getPlayerWeaknesses(),
                playerReport.getPlayerSummary(),
                playerReport.getReviews().stream()
                        .map(this::mapToPlayerReportDto)
                        .collect(Collectors.toList())
        );
    }

    public UserPlayerReviewDto mapToPlayerReportDto(UserPlayerReview review){
        return new UserPlayerReviewDto(
                review.getReviewId(),
                review.getReview(),
                review.getRating(),
                review.getUser().getId(),
                review.getPlayer().getPlayerId(),
                review.getPlayerReport().getReportId()
        );
    }
}
