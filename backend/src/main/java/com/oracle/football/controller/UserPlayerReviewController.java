package com.oracle.football.controller;

import com.oracle.football.dto.UserPlayerReviewDto;
import com.oracle.football.dto.UserPlayerReviewRequest;
import com.oracle.football.service.UserPlayerReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class UserPlayerReviewController {

  private final UserPlayerReviewService userPlayerReviewService;

  public UserPlayerReviewController(UserPlayerReviewService userPlayerReviewService) {
    this.userPlayerReviewService = userPlayerReviewService;
  }

  @PostMapping
  public ResponseEntity<UserPlayerReviewDto> createReview(
      @RequestBody UserPlayerReviewRequest reviewRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto createdReview = userPlayerReviewService.createReview(reviewRequest,
        userDetails.getUsername());
    return ResponseEntity.ok(createdReview);
  }

  @PutMapping("/{reviewId}")
  public ResponseEntity<UserPlayerReviewDto> updateReview(@PathVariable Integer reviewId,
      @RequestBody UserPlayerReviewRequest reviewRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto updatedReview = userPlayerReviewService.updateReview(reviewId,
        reviewRequest, userDetails.getUsername());
    return ResponseEntity.ok(updatedReview);
  }

  @GetMapping
  public ResponseEntity<List<UserPlayerReviewDto>> getReviewsByUser(
      @AuthenticationPrincipal UserDetails userDetails) {
    List<UserPlayerReviewDto> reviews = userPlayerReviewService.getReviewsByUser(
        userDetails.getUsername());
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/player/{playerId}")
  public ResponseEntity<UserPlayerReviewDto> getReviewByPlayerId(@PathVariable Integer playerId,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto review = userPlayerReviewService.getPlayerReviewByUser(playerId,
        userDetails.getUsername());
    return ResponseEntity.ok(review);
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
    userPlayerReviewService.deleteReview(reviewId);
    return ResponseEntity.noContent().build();
  }

}
