package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the GUI for the sports statistics user interface, allowing users to view or compare team statistics
 * for soccer, basketball, and baseball.
 */
public class GUI {
    private ApiSportsCalls apiSportsCalls;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public GUI() {
        apiSportsCalls = new ApiSportsCalls(System.getenv("RAPIDAPI_KEY"));

        try {
            apiSportsCalls.basketballTeamStatistics();
            apiSportsCalls.baseballTeamStatistics();
            apiSportsCalls.soccerTeamStatistics();
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }

        setupGui();
    }

    /**
     * Sets up the GUI components, including the main menu, sport-specific menus,
     * statistics viewer, and team statistics comparison.
     */
    private void setupGui() {
        frame = new JFrame("Sports Statistics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel sportSelectionPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton soccerButton = new JButton("Soccer");
        JButton basketballButton = new JButton("Basketball");
        JButton baseballButton = new JButton("Baseball");

        soccerButton.addActionListener(e -> showMenuPanel("Soccer"));
        basketballButton.addActionListener(e -> showMenuPanel("Basketball"));
        baseballButton.addActionListener(e -> showMenuPanel("Baseball"));

        sportSelectionPanel.add(soccerButton);
        sportSelectionPanel.add(basketballButton);
        sportSelectionPanel.add(baseballButton);

        mainPanel.add(sportSelectionPanel, "Main");
        mainPanel.add(createMenuPanel("Soccer"), "Soccer Menu");
        mainPanel.add(createMenuPanel("Basketball"), "Basketball Menu");
        mainPanel.add(createMenuPanel("Baseball"), "Baseball Menu");

        mainPanel.add(createSportPanel("Soccer"), "Soccer Stats");
        mainPanel.add(createSportPanel("Basketball"), "Basketball Stats");
        mainPanel.add(createSportPanel("Baseball"), "Baseball Stats");

        mainPanel.add(createComparePanel("Soccer"), "Soccer Compare");
        mainPanel.add(createComparePanel("Basketball"), "Basketball Compare");
        mainPanel.add(createComparePanel("Baseball"), "Baseball Compare");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Creates a menu panel for selecting between viewing statistics and comparing teams.
     * Includes a back button to return to the main menu.
     *
     * @param sport The selected sport the uesr want to see the options for
     */
    private JPanel createMenuPanel(String sport) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton viewStatsButton = new JButton("View Statistics");
        JButton compareTeamsButton = new JButton("Compare Teams");
        JButton backButton = new JButton("Back to Sports");

        viewStatsButton.addActionListener(e -> showSportPanel(sport + " Stats"));
        compareTeamsButton.addActionListener(e -> showComparePanel(sport));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        panel.add(viewStatsButton);
        panel.add(compareTeamsButton);
        panel.add(backButton);

        return panel;
    }

    /**
     * Creates the specified sport's statistics panel in the interface.
     *
     * @param sport The sport the user has selected
     */
    private JPanel createSportPanel(String sport) {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> teamDropdown;
        if ("Soccer".equals(sport)) {
            String[] fbTeams = apiSportsCalls.getSoccerTeamStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown = new JComboBox<>(fbTeams);
        } else if ("Basketball".equals(sport)) {
            String[] basketballTeams = apiSportsCalls.getBasketballStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown = new JComboBox<>(basketballTeams);
        } else {
            String[] baseballTeams = apiSportsCalls.getBaseBallTeamStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown = new JComboBox<>(baseballTeams);
        }

        JTextArea outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);

        JButton fetchButton = new JButton("Get Statistics");
        fetchButton.addActionListener(e -> {
            String selectedTeam = (String) teamDropdown.getSelectedItem();
            if ("Soccer".equals(sport)) {
                apiSportsCalls.printScStatistics(selectedTeam, outputArea);
            } else if ("Basketball".equals(sport)) {
                apiSportsCalls.printBasketballTeamStatistics(selectedTeam, outputArea);
            } else {
                apiSportsCalls.printBaseBallTeamStatistics(selectedTeam, outputArea);
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMenuPanel(sport));

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Team:"));
        topPanel.add(teamDropdown);
        topPanel.add(fetchButton);
        topPanel.add(backButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the comparison panel for a given sport in the Interface.
     *
     * @param sport The sport the user has selected
     */
    private JPanel createComparePanel(String sport) {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> teamDropdown1, teamDropdown2;

        if ("Soccer".equals(sport)) {
            String[] fbTeams = apiSportsCalls.getSoccerTeamStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown1 = new JComboBox<>(fbTeams);
            teamDropdown2 = new JComboBox<>(fbTeams);
        } else if ("Basketball".equals(sport)) {
            String[] basketballTeams = apiSportsCalls.getBasketballStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown1 = new JComboBox<>(basketballTeams);
            teamDropdown2 = new JComboBox<>(basketballTeams);
        } else {
            String[] baseballTeams = apiSportsCalls.getBaseBallTeamStatisticsMap().keySet().toArray(new String[0]);
            teamDropdown1 = new JComboBox<>(baseballTeams);
            teamDropdown2 = new JComboBox<>(baseballTeams);
        }

        JTextArea outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);


        JButton compareButton = new JButton("Compare");
        compareButton.addActionListener(e -> {
            String team1 = (String) teamDropdown1.getSelectedItem();
            String team2 = (String) teamDropdown2.getSelectedItem();
            if ("Soccer".equals(sport)) {
                apiSportsCalls.compareSoccerTeams(team1, team2, outputArea);
            } else if ("Basketball".equals(sport)) {
                apiSportsCalls.compareBasketballTeams(team1, team2, outputArea);
            } else {
                apiSportsCalls.compareBaseballTeams(team1, team2, outputArea);
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMenuPanel(sport));


        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Teams:"));
        topPanel.add(teamDropdown1);
        topPanel.add(teamDropdown2);
        topPanel.add(compareButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }


    /**
     * Displays the specified sport's options menu.
     *
     * @param sport User's selected sport
     */
    private void showMenuPanel(String sport) {
        cardLayout.show(mainPanel, sport + " Menu");
    }

    /**
     * Displays the statistics screen for the specified sport
     *
     * @param panelId Corrosponds to the selected team's panel
     */
    private void showSportPanel(String panelId) {
        cardLayout.show(mainPanel, panelId);
    }

    /**
     * Displays the Team Comparison screen for the specified sport
     *
     * @param sport User's Selected sport
     */
    private void showComparePanel(String sport) {
        cardLayout.show(mainPanel, sport + " Compare");
    }

    /**
     * The main method to initiate the program to collect the
     * data from the APIs and store them, then creates GUI for
     * user interaction
     *
     * @param args
     */
    public static void main(String[] args) {
        new GUI();
    }
}
