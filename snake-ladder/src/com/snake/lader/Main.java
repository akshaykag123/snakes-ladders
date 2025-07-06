package com.snake.lader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Multi-Player Snakes & Ladders! ");
        System.out.println("===============================================");

        // Get player names
        List<String> playerNames = getPlayerNames();

        // Initialize game
        MultiPlayerSnakesAndLaddersGame game = new MultiPlayerSnakesAndLaddersGame(playerNames);

        // Show game setup
        game.printBoard();
        game.printPlayerStatus();

        // Game loop
        playGame(game);

        System.out.println("\nThanks for playing! ");
    }

    private List<String> getPlayerNames() {
        System.out.print("Enter number of players (2-6): ");
        int numPlayers = getValidPlayerCount();

        List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            String name;
            do {
                System.out.printf("Enter name for Player %d: ", i);
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Please try again.");
                }
            } while (name.isEmpty());
            playerNames.add(name);
        }

        return playerNames;
    }

    private int getValidPlayerCount() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int count = Integer.parseInt(input);
                if (count >= 2 && count <= 6) {
                    return count;
                } else {
                    System.out.print("Please enter a number between 2 and 6: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between 2 and 6: ");
            }
        }
    }

    private void playGame(MultiPlayerSnakesAndLaddersGame game) {
        System.out.println("\n Game Started! Press Enter to roll dice, or type 'quit' to exit.");

        while (!game.isGameEnded()) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.printf("\n%s's turn! Press Enter to roll dice: ", currentPlayer.getName());

            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Game terminated by user.");
                break;
            }

            try {
                GameResult result = game.playTurn();

                // Display result
                System.out.println("Dice " + result.getMessage());

                if (result.isGameWon()) {
                    System.out.printf("\n CONGRATULATIONS %s! YOU WON! \n",
                            result.getPlayer().getName());

                    // Show final standings
                    showFinalStandings(game);
                    break;
                }

                // Show updated player status
                game.printPlayerStatus();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void showFinalStandings(MultiPlayerSnakesAndLaddersGame game) {
        System.out.println("\n=== FINAL STANDINGS ===");
        List<Player> players = game.getPlayers();

        // Sort players by position (descending)
        players.sort((p1, p2) -> Integer.compare(p2.getPosition(), p1.getPosition()));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String position = switch (i) {
                case 0 -> "1st";
                case 1 -> "2nd";
                case 2 -> "3rd";
                default -> String.format("   %d%s", i + 1, getOrdinalSuffix(i + 1));
            };
            System.out.printf("%s: %s\n", position, player);
        }
        System.out.println("=======================");
    }

    private String getOrdinalSuffix(int number) {
        if (number >= 11 && number <= 13) {
            return "th";
        }
        return switch (number % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public static void main(String[] args) {
        new Main().start();
    }
}