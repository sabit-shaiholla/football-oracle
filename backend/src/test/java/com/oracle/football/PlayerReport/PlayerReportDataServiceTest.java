package com.oracle.football.PlayerReport;

import com.oracle.football.dto.PlayerReportDto;
import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import com.oracle.football.repository.PlayerReportRepository;
import com.oracle.football.service.AIReportService;
import com.oracle.football.service.PlayerDataService;
import com.oracle.football.service.PlayerReportMapper;
import com.oracle.football.service.PlayerReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerReportDataServiceTest {

    @Mock
    private AIReportService aiReportService;

    @Mock
    private PlayerReportRepository playerReportRepository;

    @Mock
    private PlayerReportMapper playerReportMapper;

    @Mock
    private PlayerDataService playerDataService;  // Mock PlayerDataService

    @InjectMocks
    private PlayerReportService playerReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrCreatePlayerReport() {
        // Given
        String playerName = "Ronaldo";
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPlayerPosition("Forward");
        player.setPlayerAge(25);
        player.setBirthday("1998-04-12");
        player.setTeam("Real Madrid");

        PlayerReport expectedReport = new PlayerReport();
        expectedReport.setPlayer(player);
        expectedReport.setPlayerStrengths("Strong footwork");
        expectedReport.setPlayerWeaknesses("Needs better stamina");
        expectedReport.setPlayerSummary("Overall a Phenomenal player.");

        PlayerReportDto expectedReportDto = new PlayerReportDto(
                null,
                player.getPlayerName(),
                expectedReport.getPlayerStrengths(),
                expectedReport.getPlayerWeaknesses(),
                expectedReport.getPlayerSummary(),
                null
        );

        // Mock the methods
        when(playerDataService.getPlayerDataByName(playerName)).thenReturn(player);
        when(playerReportRepository.findByPlayer(player)).thenReturn(Optional.empty());
        when(aiReportService.generateScoutingReport(player)).thenReturn(expectedReport);
        when(playerReportMapper.toDto(expectedReport)).thenReturn(expectedReportDto);

        PlayerReportDto actualReportDto = playerReportService.getOrCreatePlayerReport(playerName);

        // Assert
        assertEquals(expectedReportDto, actualReportDto);
        verify(playerReportRepository).findByPlayer(player);
        verify(aiReportService).generateScoutingReport(player);
        verify(playerReportMapper).toDto(expectedReport);
    }
}