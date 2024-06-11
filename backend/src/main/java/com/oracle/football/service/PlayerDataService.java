package com.oracle.football.service;

import com.oracle.football.exception.ResourceNotFoundException;
import com.oracle.football.model.Player;
import com.oracle.football.repository.PlayerRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerDataService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerDataService.class);
    private static final String FBREF_SEARCH_URL = "https://fbref.com/en";
    private static final int DEFAULT_AGE = 18;

    private final PlayerRepository playerRepository;

    public PlayerDataService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayerDataByName(String playerName) {
        return playerRepository.findByPlayerName(playerName)
                    .orElseGet(() -> {
                        Player player = fetchPlayerData(playerName);
                        if (player == null) {
                            throw new ResourceNotFoundException("Player not found: " + playerName);
                        }
                        playerRepository.save(player);
                        return player;
                    });
    }

    private Player fetchPlayerData(String playerName) {
        try {
            String playerUrl = searchForPlayerUrl(playerName);
            if (playerUrl == null) {
                logger.error("Player not found: {}", playerName);
                return null;
            }
            Player player = fetchPlayerDataByUrl(playerUrl);
            player.setPlayerName(playerName);
            playerRepository.save(player);
            return player;
        } catch (IOException e) {
            logger.error("Error fetching player data for: {}", playerName, e);
            return null;
        }
    }

    private String searchForPlayerUrl(String playerName) throws IOException {
        String searchUrl = FBREF_SEARCH_URL + "/search/search.fcgi?search=" + playerName.replace(" ", "+");
        Document searchPage = Jsoup.connect(searchUrl)
                .userAgent("Mozilla/5.0")
                .followRedirects(true)
                .get();
        return searchPage.location();
    }

    private Player fetchPlayerDataByUrl(String url) throws IOException {
        Player player = new Player();
        Document doc = Jsoup.connect(url).get();

        player.setPlayerPosition(extractPosition(doc));
        player.setBirthday(extractBirthday(doc));
        player.setPlayerAge(extractAge(doc));
        player.setTeam(extractTeam(doc));
        player.setStatistics(extractStatistics(doc));
        return player;
    }

    private String extractPosition(Document doc) {
        String positionAndFooted = extractText(doc, "p:contains(Position)");
        if (positionAndFooted.contains("▪")) {
            return positionAndFooted.split("▪")[0].trim();
        }
        return positionAndFooted;
    }

    private String extractBirthday(Document doc) {
        return extractText(doc, "span#necro-birth");
    }

    private int extractAge(Document doc) {
        String ageWithDays = extractText(doc, "nobr");
        if (ageWithDays.isEmpty()) {
            return DEFAULT_AGE;
        }
        String age = ageWithDays.split("&nbsp;")[1].split("-")[0].trim();
        return Integer.parseInt(age);
    }

    private String extractTeam(Document doc) {
        return extractText(doc, "p:contains(Club)");
    }

    private Map<String, String> extractStatistics(Document doc) {
        Map<String, String> stats = new HashMap<>();
        Elements rows = doc.select("table[id^=scout_summary_] tbody tr");
        for (Element row : rows) {
            String statName = row.selectFirst("th").text();
            String statValue = row.selectFirst("td").text();
            stats.put(statName, statValue);
        }
        return stats;
    }

    private String extractText(Document doc, String cssQuery) {
        Element element = doc.selectFirst(cssQuery);
        if (element != null) {
            return element.text();
        }
        return "";
    }
}
