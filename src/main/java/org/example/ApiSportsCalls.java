package org.example;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import javax.swing.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles API calls for getting sports statistics and organizes the data
 * into maps for soccer, basketball, and baseball. Holds the print formatting for
 * viewing statistics and comparing team statistics.
 */
public class ApiSportsCalls {
    public String apiKey;
    public OkHttpClient client;
    public Gson gson;
    private Map<String, StatisticsResponse> basketballStatisticsMap;
    private Map<String, bbStatisticsResponse> baseBallTeamStatisticsMap;
    private Map<String, scStatisticsResponse> soccerTeamStatisticsMap;

    /**
     * Initialize hashmaps, apikey, OkHttp, and gson
     */
    public ApiSportsCalls(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.basketballStatisticsMap = new HashMap<>();
        this.soccerTeamStatisticsMap = new HashMap<>();
        this.baseBallTeamStatisticsMap = new HashMap<>();

    }

    /**
     * Gets the statistics for Basketball teams and stores them in a map.
     * For each team in the array, the method will call the API using the URL and team ID to
     * find statistics and store them for each team.
     * uses Gson library to parse and format data into map.
     */
    public void basketballTeamStatistics() throws IOException {
        String[] teamIds = {"132", "133", "134", "135", "136", "137", "138", "139", "140", "141"};

        for (String teamId : teamIds) {
            String basketballUrl = "https://api-basketball.p.rapidapi.com/statistics?season=2023-2024&league=12&team=" + teamId;
            String basketballResponse = callApi(basketballUrl, "api-basketball.p.rapidapi.com");

            if (basketballResponse != null) {

                StatisticsResponse basketballStats = gson.fromJson(basketballResponse, StatisticsResponse.class);

                String teamName = (String) basketballStats.getResponse().getTeam().get("name");
                basketballStatisticsMap.put(teamName, basketballStats);

                System.out.println("Statistics for " + teamName + " have been stored.");
            }
        }
    }

    /**
     * Gets the statistics for Baseball teams and stores them in a map.
     * for each team in the array, the method will call the API using the URL and teamID to
     * find statistics and store them for each team.
     * uses Gson library to parse and format data into map.
     */
    public void baseballTeamStatistics() throws IOException {
        String[] teamIds = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};

        for (String teamId : teamIds) {
            String baseballUrl = "https://api-baseball.p.rapidapi.com/teams/statistics?league=1&season=2022&team=" + teamId;
            String baseballResponse = callApi(baseballUrl, "api-baseball.p.rapidapi.com");

            if (baseballResponse != null) {
                bbStatisticsResponse baseballStats = gson.fromJson(baseballResponse, bbStatisticsResponse.class);

                String baseBallteamName = (String) baseballStats.getResponse().getTeam().get("name");
                baseBallTeamStatisticsMap.put(baseBallteamName, baseballStats);

                System.out.println("Statistics for " + baseBallteamName + " have been stored.");
            }
        }
    }

    /**
     * Gets the statistics for Soccer teams.
     * for each team in the array, the method will call the API using the URL and teamID to
     * find statistics and store them for each team.
     * uses Gson library to parse and format data into map.
     *
     * @throws IOException
     */
    public void soccerTeamStatistics() throws IOException {
        String[] teamIds = {"1595", "1596", "1597", "1598", "1599", "1600", "1601", "1602", "1603", "1604"};

        for (String teamId : teamIds) {
            String soccerUrl = "https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=253&season=2020&team=" + teamId;
            String soccerResponse = callApi(soccerUrl, "api-football-v1.p.rapidapi.com");

            if (soccerResponse != null) {
                scStatisticsResponse soccerStats = gson.fromJson(soccerResponse, scStatisticsResponse.class);

                String soccerteamName = (String) soccerStats.getResponse().getTeam().get("name");
                soccerTeamStatisticsMap.put(soccerteamName, soccerStats);

                System.out.println("Statistics for " + soccerteamName + " have been stored.");
            }
        }
    }

    /**
     * Method used to print out basketball team statistics within GUI.
     * If the Hashmap contains the name of the team, the method will print out the
     * following to the output area defined in the GUI
     *
     * Print out text written with ChatGPT (Accuracy and Ease)
     *
     * @param teamName   Key used to find the correct Team
     * @param outputArea Area in the GUI the text will print on
     */
    @SuppressWarnings("unchecked")
    public void printBasketballTeamStatistics(String teamName, JTextArea outputArea) {
        outputArea.setText("");
        if (basketballStatisticsMap.containsKey(teamName)) {
            StatisticsResponse stats = basketballStatisticsMap.get(teamName);
            outputArea.append("=== Statistics for " + teamName + " ===\n\n");


            Map<String, Object> games = stats.getResponse().getGames();
            Map<String, Object> played = (Map<String, Object>) games.get("played");
            Map<String, Object> wins = (Map<String, Object>) games.get("wins");
            Map<String, Object> losses = (Map<String, Object>) games.get("loses");

            outputArea.append("Games Played:\n");
            outputArea.append("  Home: " + played.get("home") + "\n");
            outputArea.append("  Away: " + played.get("away") + "\n");
            outputArea.append("  Total: " + played.get("all") + "\n\n");

            outputArea.append("Wins:\n");
            outputArea.append("  Home: " + ((Map<String, Object>) wins.get("home")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("home")).get("percentage") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) wins.get("away")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("away")).get("percentage") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) wins.get("all")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("all")).get("percentage") + ")\n\n");

            outputArea.append("Losses:\n");
            outputArea.append("  Home: " + ((Map<String, Object>) losses.get("home")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("home")).get("percentage") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) losses.get("away")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("away")).get("percentage") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) losses.get("all")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("all")).get("percentage") + ")\n\n");


            Map<String, Object> points = stats.getResponse().getPoints();
            Map<String, Object> pointsFor = (Map<String, Object>) points.get("for");
            Map<String, Object> pointsAgainst = (Map<String, Object>) points.get("against");

            outputArea.append("Points Scored (For):\n");
            outputArea.append("  Home: " + ((Map<String, Object>) pointsFor.get("total")).get("home")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("home") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) pointsFor.get("total")).get("away")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("away") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) pointsFor.get("total")).get("all")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("all") + ")\n\n");

            outputArea.append("Points Conceded (Against):\n");
            outputArea.append("  Home: " + ((Map<String, Object>) pointsAgainst.get("total")).get("home")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("home") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) pointsAgainst.get("total")).get("away")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("away") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) pointsAgainst.get("total")).get("all")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("all") + ")\n");

        } else {
            outputArea.append("No statistics found for team: " + teamName);
        }
    }

    /**
     * Method used to print out baseball team statistics within GUI.
     * If the Hashmap contains the name of the team, the method will print out the
     * following to the output area defined in the GUI

     * Print out text written with ChatGPT (Accuracy and Ease)
     *
     * @param teamName   Key used to find the correct Team
     * @param outputArea Area in the GUI the text will print on
     */
    @SuppressWarnings("unchecked")
    public void printBaseBallTeamStatistics(String teamName, JTextArea outputArea) {
        outputArea.setText("");
        if (baseBallTeamStatisticsMap.containsKey(teamName)) {
            bbStatisticsResponse stats = baseBallTeamStatisticsMap.get(teamName);
            outputArea.append("=== Statistics for " + teamName + " ===\n\n");


            Map<String, Object> games = stats.getResponse().getGames();
            Map<String, Object> played = (Map<String, Object>) games.get("played");
            Map<String, Object> wins = (Map<String, Object>) games.get("wins");
            Map<String, Object> losses = (Map<String, Object>) games.get("loses");

            outputArea.append("Games Played:\n");
            outputArea.append("  Home: " + played.get("home") + "\n");
            outputArea.append("  Away: " + played.get("away") + "\n");
            outputArea.append("  Total: " + played.get("all") + "\n\n");

            outputArea.append("Wins:\n");
            outputArea.append("  Home: " + ((Map<String, Object>) wins.get("home")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("home")).get("percentage") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) wins.get("away")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("away")).get("percentage") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) wins.get("all")).get("total")
                    + " (Win percentage: " + ((Map<String, Object>) wins.get("all")).get("percentage") + ")\n\n");

            outputArea.append("Losses:\n");
            outputArea.append("  Home: " + ((Map<String, Object>) losses.get("home")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("home")).get("percentage") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) losses.get("away")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("away")).get("percentage") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) losses.get("all")).get("total")
                    + " (Loss percentage: " + ((Map<String, Object>) losses.get("all")).get("percentage") + ")\n\n");


            Map<String, Object> points = stats.getResponse().getPoints();
            Map<String, Object> pointsFor = (Map<String, Object>) points.get("for");
            Map<String, Object> pointsAgainst = (Map<String, Object>) points.get("against");

            outputArea.append("Points Scored (For):\n");
            outputArea.append("  Home: " + ((Map<String, Object>) pointsFor.get("total")).get("home")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("home") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) pointsFor.get("total")).get("away")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("away") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) pointsFor.get("total")).get("all")
                    + " (Average: " + ((Map<String, Object>) pointsFor.get("average")).get("all") + ")\n\n");

            outputArea.append("Points Conceded (Against):\n");
            outputArea.append("  Home: " + ((Map<String, Object>) pointsAgainst.get("total")).get("home")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("home") + ")\n");
            outputArea.append("  Away: " + ((Map<String, Object>) pointsAgainst.get("total")).get("away")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("away") + ")\n");
            outputArea.append("  Total: " + ((Map<String, Object>) pointsAgainst.get("total")).get("all")
                    + " (Average: " + ((Map<String, Object>) pointsAgainst.get("average")).get("all") + ")\n");

        } else {
            outputArea.append("No statistics found for team: " + teamName);
        }
    }

    /**
     * Method used to print out Soccer team statistics within GUI.
     * If the Hashmap contains the name of the team, the method will print out the
     * following to the output area defined in the GUI

     * Print out text written with ChatGPT (Accuracy and Ease)
     *
     * @param teamName   Key used to find the correct Team
     * @param outputArea Area in the GUI the text will print on
     */
    @SuppressWarnings("unchecked")
    public void printScStatistics(String teamName, JTextArea outputArea) {
        outputArea.setText("");


        if (soccerTeamStatisticsMap.containsKey(teamName)) {
            scStatisticsResponse stats = soccerTeamStatisticsMap.get(teamName);

            outputArea.append("=== Statistics for " + teamName + " ===\n\n");


            Map<String, Object> fixtures = stats.getResponse().getFixtures();
            Map<String, Object> played = (Map<String, Object>) fixtures.get("played");
            Map<String, Object> wins = (Map<String, Object>) fixtures.get("wins");
            Map<String, Object> losses = (Map<String, Object>) fixtures.get("loses");

            outputArea.append("Games Played:\n");
            outputArea.append("  Home: " + played.get("home") + "\n");
            outputArea.append("  Away: " + played.get("away") + "\n");
            outputArea.append("  Total: " + played.get("total") + "\n\n");

            outputArea.append("Wins:\n");
            outputArea.append("  Home: " + wins.get("home") + "\n");
            outputArea.append("  Away: " + wins.get("away") + "\n");
            outputArea.append("  Total: " + wins.get("total") + "\n\n");

            outputArea.append("Losses:\n");
            outputArea.append("  Home: " + losses.get("home") + "\n");
            outputArea.append("  Away: " + losses.get("away") + "\n");
            outputArea.append("  Total: " + losses.get("total") + "\n\n");


            Map<String, Object> goals = stats.getResponse().getGoals();
            Map<String, Object> goalsFor = (Map<String, Object>) goals.get("for");
            Map<String, Object> goalsAgainst = (Map<String, Object>) goals.get("against");


            Map<String, Object> goalsForTotal = (Map<String, Object>) goalsFor.get("total");
            outputArea.append("Goals Scored (For):\n");
            outputArea.append("  Home: " + goalsForTotal.get("home") + "\n");
            outputArea.append("  Away: " + goalsForTotal.get("away") + "\n");
            outputArea.append("  Total: " + goalsForTotal.get("total") + "\n\n");

            Map<String, Object> goalsAgainstTotal = (Map<String, Object>) goalsAgainst.get("total");
            outputArea.append("Goals Conceded (Against):\n");
            outputArea.append("  Home: " + goalsAgainstTotal.get("home") + "\n");
            outputArea.append("  Away: " + goalsAgainstTotal.get("away") + "\n");
            outputArea.append("  Total: " + goalsAgainstTotal.get("total") + "\n");
        } else {
            outputArea.append("No statistics found for team: " + teamName);
        }
    }


    /**
     * Method to make API calls using okHttp, Called for
     * each API's statistics.
     *
     * @param url  URL of the sport API
     * @param host URL of the host of the API
     * @return The result of the API call as a string
     */
    public String callApi(String url, String host) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", apiKey)
                .addHeader("x-rapidapi-host", host)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println(host + " API is working!");
                return response.body().string();
            } else {
                System.out.println("Request to " + host + " failed: " + response.code());
                return null;
            }
        }
    }

    /**
     * getter for basketballStatisticsMap, so it can be used in GUI file
     *
     * @return basketballStatisticsMap
     */
    public Map<String, StatisticsResponse> getBasketballStatisticsMap() {
        return basketballStatisticsMap;
    }

    /**
     * getter for soccerStatisticsMap, so it can be used in GUI file
     *
     * @return soccerTeamStatisticsMap
     */
    public Map<String, scStatisticsResponse> getSoccerTeamStatisticsMap() {
        return soccerTeamStatisticsMap;
    }

    /**
     * getter for baseballStatisticsMap, so it can be used in GUI file
     *
     * @return baseballStatisticsMap
     */
    public Map<String, bbStatisticsResponse> getBaseBallTeamStatisticsMap() {
        return baseBallTeamStatisticsMap;
    }

    /**
     * The method used to compare the two teams, provides
     * each compare method with the blueprint for printout of
     * the comparison section.
     *
     * @param metricName The name of the statistic being compared
     * @param metric1    The first team's statistic
     * @param metric2    The second team's statistics
     * @param outputArea The area to write the text
     * @param team1Name  The first team chosen by user
     * @param team2Name  The second team chosen by user
     */
    @SuppressWarnings("unchecked")
    private void compareMetrics(String metricName, Object metric1, Object metric2, JTextArea outputArea, String team1Name, String team2Name) {
        outputArea.append(metricName + ":\n");

        if (metric1 instanceof Map && metric2 instanceof Map) {
            Map<String, Object> map1 = (Map<String, Object>) metric1;
            Map<String, Object> map2 = (Map<String, Object>) metric2;

            for (String key : map1.keySet()) {
                if (key.equals("total") || key.equals("average") || key.equals("home") || key.equals("away") || key.equals("all")) {
                    Object value1 = map1.get(key);
                    Object value2 = map2.get(key);

                    outputArea.append("  " + key + ":\n");
                    outputArea.append("    " + team1Name + ": " + formatValue(value1) + "\n");
                    outputArea.append("    " + team2Name + ": " + formatValue(value2) + "\n");
                }
            }
        } else {
            outputArea.append("  " + team1Name + ": " + formatValue(metric1) + "\n");
            outputArea.append("  " + team2Name + ": " + formatValue(metric2) + "\n");
        }

        outputArea.append("\n");
    }

    /**
     * Formats the given value into a readable string representation.
     * Handles null values by returning "N/A".
     *
     * @param value The data to be formatted.
     * @return A string representation of the formatted value.
     */
    private String formatValue(Object value) {
        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            StringBuilder builder = new StringBuilder();

            for (String key : map.keySet()) {
                Object subValue = map.get(key);
                builder.append(key).append(": ").append(formatValue(subValue)).append(", ");
            }

            if (builder.length() > 2) {
                builder.setLength(builder.length() - 2);
            }

            return builder.toString();
        } else if (value == null) {
            return "N/A";
        } else {
            return value.toString();
        }
    }

    /**
     * Printout method for comparing two soccer teams,
     * gets information from the statistics map for each team based
     * on the keys
     *
     * @param team1Name  First team based on what user has selected
     * @param team2Name  Second team the first one is being compared to
     * @param outputArea Where the printout will be placed
     */
    public void compareSoccerTeams(String team1Name, String team2Name, JTextArea outputArea) {
        outputArea.setText("");
        if (soccerTeamStatisticsMap.containsKey(team1Name) && soccerTeamStatisticsMap.containsKey(team2Name)) {
            scStatisticsResponse stats1 = soccerTeamStatisticsMap.get(team1Name);
            scStatisticsResponse stats2 = soccerTeamStatisticsMap.get(team2Name);

            outputArea.append("=== Soccer Team Comparison ===\n");
            outputArea.append("Metrics: " + team1Name + " vs " + team2Name + "\n\n");

            compareMetrics("Games Played", stats1.getResponse().getFixtures().get("played"),
                    stats2.getResponse().getFixtures().get("played"), outputArea, team1Name, team2Name);

            compareMetrics("Wins", stats1.getResponse().getFixtures().get("wins"),
                    stats2.getResponse().getFixtures().get("wins"), outputArea, team1Name, team2Name);

            compareMetrics("Losses", stats1.getResponse().getFixtures().get("loses"),
                    stats2.getResponse().getFixtures().get("loses"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Scored (For)", stats1.getResponse().getGoals().get("for"),
                    stats2.getResponse().getGoals().get("for"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Conceded (Against)", stats1.getResponse().getGoals().get("against"),
                    stats2.getResponse().getGoals().get("against"), outputArea, team1Name, team2Name);
        } else {
            outputArea.append("One or both teams not found in the soccer statistics map.");
        }
    }

    /**
     * Printout method for comparing two Basketball teams,
     * gets information from the statistics map for each team based
     * on the keys
     *
     * @param team1Name  First team based on what user has selected
     * @param team2Name  Second team the first one is being compared to
     * @param outputArea Where the printout will be placed
     */
    public void compareBasketballTeams(String team1Name, String team2Name, JTextArea outputArea) {
        outputArea.setText("");
        if (basketballStatisticsMap.containsKey(team1Name) && basketballStatisticsMap.containsKey(team2Name)) {
            StatisticsResponse stats1 = basketballStatisticsMap.get(team1Name);
            StatisticsResponse stats2 = basketballStatisticsMap.get(team2Name);

            outputArea.append("=== Basketball Team Comparison ===\n");
            outputArea.append("Metrics: " + team1Name + " vs " + team2Name + "\n\n");

            compareMetrics("Games Played", stats1.getResponse().getGames().get("played"),
                    stats2.getResponse().getGames().get("played"), outputArea, team1Name, team2Name);

            compareMetrics("Wins", stats1.getResponse().getGames().get("wins"),
                    stats2.getResponse().getGames().get("wins"), outputArea, team1Name, team2Name);

            compareMetrics("Losses", stats1.getResponse().getGames().get("loses"),
                    stats2.getResponse().getGames().get("loses"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Scored (For)", stats1.getResponse().getPoints().get("for"),
                    stats2.getResponse().getPoints().get("for"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Conceded (Against)", stats1.getResponse().getPoints().get("against"),
                    stats2.getResponse().getPoints().get("against"), outputArea, team1Name, team2Name);
        } else {
            outputArea.append("One or both teams not found in the basketball statistics map.");
        }
    }

    /**
     * Printout method for comparing two Baseball teams,
     * gets information from the statistics map for each team based
     * on the keys
     *
     * @param team1Name  First team based on what user has selected
     * @param team2Name  Second team the first one is being compared to
     * @param outputArea Where the printout will be placed
     */
    public void compareBaseballTeams(String team1Name, String team2Name, JTextArea outputArea) {
        outputArea.setText("");
        if (baseBallTeamStatisticsMap.containsKey(team1Name) && baseBallTeamStatisticsMap.containsKey(team2Name)) {
            bbStatisticsResponse stats1 = baseBallTeamStatisticsMap.get(team1Name);
            bbStatisticsResponse stats2 = baseBallTeamStatisticsMap.get(team2Name);

            outputArea.append("=== Baseball Team Comparison ===\n");
            outputArea.append("Metrics: " + team1Name + " vs " + team2Name + "\n\n");

            compareMetrics("Games Played", stats1.getResponse().getGames().get("played"),
                    stats2.getResponse().getGames().get("played"), outputArea, team1Name, team2Name);

            compareMetrics("Wins", stats1.getResponse().getGames().get("wins"),
                    stats2.getResponse().getGames().get("wins"), outputArea, team1Name, team2Name);

            compareMetrics("Losses", stats1.getResponse().getGames().get("loses"),
                    stats2.getResponse().getGames().get("loses"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Scored (For)", stats1.getResponse().getPoints().get("for"),
                    stats2.getResponse().getPoints().get("for"), outputArea, team1Name, team2Name);

            compareMetrics("Goals Conceded (Against)", stats1.getResponse().getPoints().get("against"),
                    stats2.getResponse().getPoints().get("against"), outputArea, team1Name, team2Name);
        } else {
            outputArea.append("One or both teams not found in the baseball statistics map.");
        }
    }
}
