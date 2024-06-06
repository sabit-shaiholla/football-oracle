package com.oracle.football.repository;

import com.oracle.football.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByPlayerName(String playerName);
}
