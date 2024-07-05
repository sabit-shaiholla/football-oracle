package com.oracle.football.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_reports")
public class PlayerReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reportId;

  @OneToOne
  @JoinColumn(name = "player_id", unique = true)
  private Player player;

  @Column(length = 1024)
  private String playerStrengths;

  @Column(length = 1024)
  private String playerWeaknesses;

  @Column(length = 1024)
  private String playerSummary;

  @OneToMany(mappedBy = "playerReport", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserPlayerReview> reviews = new ArrayList<>();
}
