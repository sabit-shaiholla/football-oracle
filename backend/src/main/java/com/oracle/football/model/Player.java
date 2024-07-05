package com.oracle.football.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer playerId;

  @Column(unique = true, nullable = false)
  private String playerName;

  private String playerPosition;
  private int playerAge;
  private String birthday;
  private String team;

  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserPlayerReview> reviews;

  @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
  private PlayerReport reports;

  @ElementCollection
  @CollectionTable(name = "player_statistics")
  @MapKeyColumn(name = "statistic_name")
  @Column(name = "statistic_value")
  private Map<String, String> statistics;
}
