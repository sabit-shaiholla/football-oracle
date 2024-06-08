package com.oracle.football.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "player_reports")
public class PlayerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @OneToOne
    @JoinColumn(name = "player_id", unique = true)
    private Player player;

    private String playerStrengths;
    private String playerWeaknesses;
    private String playerSummary;

    @OneToMany(mappedBy = "playerReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPlayerReview> reviews;
}
