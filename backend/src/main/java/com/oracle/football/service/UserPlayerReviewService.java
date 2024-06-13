package com.oracle.football.service;

import com.oracle.football.dto.UserPlayerReviewDto;
import com.oracle.football.dto.UserPlayerReviewRequest;
import com.oracle.football.exception.DuplicateResourceException;
import com.oracle.football.exception.ResourceNotFoundException;
import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.model.User;
import com.oracle.football.model.UserPlayerReview;
import com.oracle.football.repository.PlayerRepository;
import com.oracle.football.repository.UserPlayerReviewRepository;
import com.oracle.football.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPlayerReviewService {

    private final UserPlayerReviewRepository userPlayerReviewRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    public UserPlayerReviewService(UserPlayerReviewRepository userPlayerReviewRepository,
                                   UserRepository userRepository,
                                   PlayerRepository playerRepository) {
        this.userPlayerReviewRepository = userPlayerReviewRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    public UserPlayerReviewDto createReview(UserPlayerReviewRequest reviewRequest, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = playerRepository.findByPlayerId(reviewRequest.playerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlayerReport playerReport = player.getReports();

        List<UserPlayerReview> existingReviews = userPlayerReviewRepository.findAllByUser(user);
        if (existingReviews.stream().anyMatch(review -> review.getPlayer().equals(player))) {
            throw new DuplicateResourceException("A review by this user on this player already exists");
        }

        UserPlayerReview review = new UserPlayerReview();
        review.setUser(user);
        review.setPlayer(player);
        review.setPlayerReport(playerReport);
        review.setReview(reviewRequest.review());
        review.setRating(reviewRequest.rating());

        UserPlayerReview savedReview = userPlayerReviewRepository.save(review);

        return new UserPlayerReviewDto(
                savedReview.getReviewId(),
                user.getUsername(),
                player.getPlayerId(),
                player.getPlayerName(),
                player.getReports().getReportId(),
                savedReview.getReview(),
                savedReview.getRating());
    }

    public List<UserPlayerReviewDto> getReviewsByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userPlayerReviewRepository.findAllByUser(user).stream()
                .map(review -> new UserPlayerReviewDto(
                        review.getReviewId(),
                        user.getUsername(),
                        review.getPlayer().getPlayerId(),
                        review.getPlayer().getPlayerName(),
                        review.getPlayerReport().getReportId(),
                        review.getReview(), review.getRating()))
                .collect(Collectors.toList());
    }

    public UserPlayerReviewDto getPlayerReviewByUser(Integer playerId, String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserPlayerReview review = userPlayerReviewRepository.findAllByUser(user).stream()
                .filter(r -> r.getPlayer().getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        return new UserPlayerReviewDto(
                review.getReviewId(),
                user.getUsername(),
                review.getPlayer().getPlayerId(),
                review.getPlayer().getPlayerName(),
                review.getPlayerReport().getReportId(),
                review.getReview(),
                review.getRating());
    }

    public UserPlayerReviewDto updateReview(Integer reviewId,
                                            UserPlayerReviewRequest reviewRequest,
                                            String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserPlayerReview review = userPlayerReviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setReview(reviewRequest.review());
        review.setRating(reviewRequest.rating());

        userPlayerReviewRepository.save(review);

        return new UserPlayerReviewDto(
                reviewId,
                user.getUsername(),
                review.getPlayer().getPlayerId(),
                review.getPlayer().getPlayerName(),
                review.getPlayerReport().getReportId(),
                review.getReview(),
                review.getRating());
    }

    public void deleteReview(Integer reviewId) {
        userPlayerReviewRepository.deleteById(reviewId);
    }
}
