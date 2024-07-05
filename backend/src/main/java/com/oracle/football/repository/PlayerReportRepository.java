package com.oracle.football.repository;

import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerReportRepository extends JpaRepository<PlayerReport, Long> {

  Optional<PlayerReport> findByPlayer(Player player);
}
