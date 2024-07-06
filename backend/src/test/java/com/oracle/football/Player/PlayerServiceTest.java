package com.oracle.football.Player;

import com.oracle.football.dto.PlayerDto;
import com.oracle.football.model.Player;
import com.oracle.football.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

  @Mock
  private PlayerService playerService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSavePlayer() {
    Player player = new Player();
    player.setPlayerName("Test Player");

    when(playerService.savePlayer(any(Player.class))).thenReturn(player);

    Player result = playerService.savePlayer(player);

    assertEquals(player.getPlayerName(), result.getPlayerName());
    verify(playerService, times(1)).savePlayer(player);
  }

  @Test
  void testFindPlayerByName() {
    Player player = new Player();
    player.setPlayerName("Test Player");

    when(playerService.findPlayerByName(anyString())).thenReturn(Optional.of(player));

    Optional<Player> result = playerService.findPlayerByName("Test Player");

    assertEquals(player.getPlayerName(), result.get().getPlayerName());
    verify(playerService, times(1)).findPlayerByName("Test Player");
  }

  @Test
  void testGetPlayerDtoByName() {
    // Given
    String playerName = "Sabit";
    Player player = new Player();
    player.setPlayerName(playerName);
    player.setPlayerPosition("Midfielder");
    player.setPlayerAge(27);
    player.setBirthday("1996-12-28");
    player.setTeam("Manchester United");
    Map<String, String> stats = new HashMap<>();
    stats.put("Goals", "10");
    stats.put("Assists", "6");
    player.setStatistics(stats);

    PlayerDto expectedPlayerDto = new PlayerDto(
        player.getPlayerId(),
        player.getPlayerName(),
        player.getPlayerPosition(),
        player.getPlayerAge(),
        player.getBirthday(),
        player.getTeam(),
        player.getStatistics()
    );

    // When
    when(playerService.getPlayerDtoByName(anyString())).thenReturn(expectedPlayerDto);

    // Then
    PlayerDto actualPlayerDto = playerService.getPlayerDtoByName(playerName);
    assertEquals(expectedPlayerDto, actualPlayerDto);
  }
}
