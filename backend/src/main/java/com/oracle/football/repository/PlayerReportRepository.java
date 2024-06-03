package com.oracle.football.repository;

import com.oracle.football.model.PlayerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerReportRepository extends JpaRepository<PlayerReport, Long> {
}
