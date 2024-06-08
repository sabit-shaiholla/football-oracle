package com.oracle.football.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.*;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.oracle.football.model.Player;
import com.oracle.football.model.PlayerReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AIReportService {

    private static final Logger logger = LoggerFactory.getLogger(AIReportService.class);

    @Value("${google.cloud.project-id")
    private String projectId;

    @Value("${google.cloud.region}")
    private String region;

    @Value("${gemini.model.endpoint-id}")
    private String endpointId;

    @Value("${gemini.api.key}")
    private String apiKey;

    public PlayerReport generateScoutingReport(Player player) {
        logger.debug("Generating scouting report for player: {}", player.getPlayerName());

        String prompt = String.format("""
            I need you to create a scouting report on %s. Can you provide me with a summary of their strengths and weaknesses?
            Here is the data I have on him:
            Player: %s
            Position: %s
            Age: %d
            Team: %s
            %s
            Return the scouting report in the following markdown format:
            # Scouting Report for %s
            ## Strengths
            < a list of 1 to 3 strengths >
            ## Weaknesses
            < a list of 1 to 3 weaknesses >
            ## Summary
            < a brief summary of the player's overall performance and if he would be beneficial to the team >
            """,
                player.getPlayerName(),
                player.getPlayerName(),
                player.getPlayerPosition(),
                player.getPlayerAge(),
                player.getTeam(),
                player.getStatistics().toString(),
                player.getPlayerName());

        return callGeminiApi(prompt, player);
    }

    private PlayerReport callGeminiApi(String prompt, Player player) {
        logger.debug("Sending request to Gemini Pro: {}", prompt);

        try (VertexAI vertexAi = new VertexAI.Builder()
                .setProjectId(projectId)
                .setLocation(region)
                .setApiEndpoint(endpointId)
                .build()) {

            GenerativeModel model = new GenerativeModel("gemini-1.5-pro", vertexAi);
            GenerateContentResponse response = model.generateContent(prompt);

            String generatedContent = response.
                    getCandidatesList().getFirst().
                    getContent().
                    getPartsList().getFirst().getText();

            PlayerReport playerReport = new PlayerReport();
            playerReport.setPlayer(player);
            playerReport.setPlayerStrengths(parseStrengths(generatedContent));
            playerReport.setPlayerWeaknesses(parseWeaknesses(generatedContent));
            playerReport.setPlayerSummary(parseSummary(generatedContent));

            logger.debug("Received report from Gemini Pro for player: {}", player.getPlayerName());

            return playerReport;
        } catch (IOException e) {
            logger.error("Error while calling Gemini Pro API", e);
            throw new RuntimeException("Error while calling Gemini Pro API", e);
        }
    }

    private String parseStrengths(String content) {
        return extractSection(content, "## Strengths", "## Weaknesses");
    }

    private String parseWeaknesses(String content) {
        return extractSection(content, "## Weaknesses", "## Summary");
    }

    private String parseSummary(String content) {
        return extractSection(content, "## Summary", null);
    }

    private String extractSection(String content, String startSection, String endSection) {
        String regex;
        if (endSection != null) {
            regex = Pattern.quote(startSection) + "(.*?)" + Pattern.quote(endSection);
        } else {
            regex = Pattern.quote(startSection) + "(.*)";
        }
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
}
