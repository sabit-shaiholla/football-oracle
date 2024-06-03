package com.oracle.football.repository;

import com.oracle.football.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByPlayerName(String playerName);
}
