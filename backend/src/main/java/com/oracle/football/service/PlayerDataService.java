package com.oracle.football.service;

import com.oracle.football.model.Player;
import com.oracle.football.repository.PlayerRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerDataService {

    private static final String FBREF_SEARCH_URL = "https://fbref.com/en/";

    @Autowired
    private PlayerRepository playerRepository;

    public Player getPlayerDataByName(String playerName) throws IOException {
        Optional<Player> existingPlayer = playerRepository.findByPlayerName(playerName);

        if (existingPlayer.isPresent()) {
            return existingPlayer.get();
        } else {
            String playerUrl = searchForPlayerUrl(playerName);
            if (playerUrl == null) {
                throw new IllegalArgumentException("Player not found");
            }
            Player player = fetchPlayerData(playerUrl);
            player.setPlayerName(playerName);
            return playerRepository.save(player);
        }
    }

    private String searchForPlayerUrl(String playerName) throws IOException {
        Document searchPage = Jsoup.connect(FBREF_SEARCH_URL)
                .data("search", playerName)
                .userAgent("Mozilla/5.0")
                .post();

        Element playerLink = searchPage.selectFirst("a[href*='/players/']");
        return playerLink != null ? "https://fbref.com" + playerLink.attr("href") : null;
    }

    private Player fetchPlayerData(String url) throws IOException {
        Player player = new Player();
        Document doc = Jsoup.connect(url).get();

        String position = extractPosition(doc);
        String birthday = extractBirthday(doc);
        int age = calculateAge(birthday);
        String team = extractTeam(doc);

        player.setPlayerPosition(position);
        player.setBirthday(birthday);
        player.setPlayerAge(age);
        player.setTeam(team);

        Map<String, String> statistics = extractStatistics(doc);
        player.setStatistics(statistics);
        return player;
    }

    private String extractPosition(Document doc) {
        Element positionElement = doc.selectFirst("p:-soup-contains(Position)");
        return positionElement != null ? positionElement.text().split(":")[1].trim() : "";
    }

    private String extractBirthday(Document doc) {
        Element birthdayElement = doc.selectFirst("span#necro-birth");
        return birthdayElement != null ? birthdayElement.text().trim() : "";
    }

    private int calculateAge(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDateTime birthDate = LocalDateTime.parse(birthday, formatter);
        return LocalDateTime.now().getYear() - birthDate.getYear();
    }

    private String extractTeam(Document doc) {
        Element teamElement = doc.selectFirst("p:-soup-contains(Club)");
        return teamElement != null ? teamElement.text().split(":")[1].trim() : "";
    }

    private Map<String, String> extractStatistics(Document doc) {
        Map<String, String> stats = new HashMap<>();
        Elements tables = doc.select("table[id^=scout_summary_]");
        if (!tables.isEmpty()) {
            Element table = tables.getFirst();
            Elements rows = table.select("tbody tr");
            for (Element row : rows) {
                String statName = row.select("th").text();
                String statValue = row.select("td").text();
                stats.put(statName, statValue);
            }
        }

        return stats;
    }
}
