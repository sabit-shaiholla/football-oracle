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
@Table(name = "player_report")
public class PlayerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private String playerStrengths;
    private String playerWeaknesses;
    private String playerSummary;

    @OneToMany(mappedBy = "playerReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPlayerReview> reviews;
}
