package com.oracle.football.controller;

import com.oracle.football.dto.UserPlayerReviewDto;
import com.oracle.football.dto.UserPlayerReviewRequest;
import com.oracle.football.service.UserPlayerReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user player review requests.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/reviews")
public class UserPlayerReviewController {

  private final UserPlayerReviewService userPlayerReviewService;

  /**
   * Constructs a {@code UserPlayerReviewController} with the specified user player review service.
   *
   * @param userPlayerReviewService the user player review service to use
   */
  public UserPlayerReviewController(UserPlayerReviewService userPlayerReviewService) {
    this.userPlayerReviewService = userPlayerReviewService;
  }

  /**
   * Creates a new review for a player.
   *
   * @param reviewRequest the request containing review details
   * @param userDetails the details of the authenticated user
   * @return a response entity containing the created review
   */
  @Operation(summary = "Create a new review", description = "Creates a new review for a player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Review created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  public ResponseEntity<UserPlayerReviewDto> createReview(
      @RequestBody UserPlayerReviewRequest reviewRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto createdReview = userPlayerReviewService.createReview(reviewRequest,
        userDetails.getUsername());
    return ResponseEntity.ok(createdReview);
  }

  /**
   * Updates an existing review.
   *
   * @param reviewId the ID of the review to update
   * @param reviewRequest the request containing updated review details
   * @param userDetails the details of the authenticated user
   * @return a response entity containing the updated review
   */
  @Operation(summary = "Update a review", description = "Updates an existing review.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Review updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "Review not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{reviewId}")
  public ResponseEntity<UserPlayerReviewDto> updateReview(@PathVariable Integer reviewId,
      @RequestBody UserPlayerReviewRequest reviewRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto updatedReview = userPlayerReviewService.updateReview(reviewId,
        reviewRequest, userDetails.getUsername());
    return ResponseEntity.ok(updatedReview);
  }

  /**
   * Retrieves all reviews made by the authenticated user.
   *
   * @param userDetails the details of the authenticated user
   * @return a response entity containing a list of reviews made by the user
   */
  @Operation(summary = "Get all reviews by user", description = "Retrieves all reviews made by the authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public ResponseEntity<List<UserPlayerReviewDto>> getReviewsByUser(
      @AuthenticationPrincipal UserDetails userDetails) {
    List<UserPlayerReviewDto> reviews = userPlayerReviewService.getReviewsByUser(
        userDetails.getUsername());
    return ResponseEntity.ok(reviews);
  }

  /**
   * Retrieves a review for a specific player made by the authenticated user.
   *
   * @param playerId the ID of the player
   * @param userDetails the details of the authenticated user
   * @return a response entity containing the review for the specified player
   */
  @Operation(summary = "Get review by player ID", description = "Retrieves a review for a specific player made by the authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Review retrieved successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "Review not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/player/{playerId}")
  public ResponseEntity<UserPlayerReviewDto> getReviewByPlayerId(@PathVariable Integer playerId,
      @AuthenticationPrincipal UserDetails userDetails) {
    UserPlayerReviewDto review = userPlayerReviewService.getPlayerReviewByUser(playerId,
        userDetails.getUsername());
    return ResponseEntity.ok(review);
  }

  /**
   * Deletes a review by its ID.
   *
   * @param reviewId the ID of the review to delete
   * @return a response entity with no content
   */
  @Operation(summary = "Delete a review", description = "Deletes a review by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "Review not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
    userPlayerReviewService.deleteReview(reviewId);
    return ResponseEntity.noContent().build();
  }

}
