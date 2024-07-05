package com.oracle.football.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_player_reviews")
public class UserPlayerReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;

  @ManyToOne
  @JoinColumn(name = "report_id", nullable = false)
  private PlayerReport playerReport;

  private String review;

  @Min(1)
  @Max(10)
  private int rating;
}
