package com.snake.lader;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerSnakesAndLaddersGame {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayerIndex;
    private boolean gameEnded;
    private Player winner;

    public MultiPlayerSnakesAndLaddersGame(List<String> playerNames) {
        if (playerNames == null || playerNames.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required");
        }

        this.board = new Board();
        this.players = new ArrayList<>();
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameEnded = false;
        this.winner = null;

        // Initialize players
        for (String name : playerNames) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty");
            }
            players.add(new Player(name.trim()));
        }
    }

    public GameResult playTurn() {
        if (gameEnded) {
            throw new IllegalStateException("Game has already ended");
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        int diceRoll = dice.roll();

        int previousPosition = currentPlayer.getPosition();
        int tentativePosition = previousPosition + diceRoll;

        // Check if move is valid (not exceeding 100)
        if (tentativePosition > 100) {
            String message = String.format("%s rolled %d. Position remains %d (would exceed 100)",
                    currentPlayer.getName(), diceRoll, previousPosition);

            moveToNextPlayer();
            return new GameResult(currentPlayer, previousPosition, previousPosition,
                    diceRoll, message, false);
        }

        // Move player to tentative position
        currentPlayer.setPosition(tentativePosition);

        // Check for snakes or ladders
        int finalPosition = board.getNewPosition(tentativePosition);
        currentPlayer.setPosition(finalPosition);

        // Create result message
        String message = createMoveMessage(currentPlayer, previousPosition,
                tentativePosition, finalPosition, diceRoll);

        // Check for win condition
        if (currentPlayer.hasWon()) {
            gameEnded = true;
            winner = currentPlayer;
            message += " -  WINNER! ";
        } else {
            moveToNextPlayer();
        }

        return new GameResult(currentPlayer, previousPosition, finalPosition,
                diceRoll, message, currentPlayer.hasWon());
    }

    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private String createMoveMessage(Player player, int from, int tentative, int to, int diceRoll) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("%s rolled %d and moved from %d to %d",
                player.getName(), diceRoll, from, to));

        if (tentative != to) {
            if (board.hasLadderAt(tentative)) {
                message.append(" (climbed ladder from ").append(tentative).append(")");
            } else if (board.hasSnakeAt(tentative)) {
                message.append(" (bit by snake at ").append(tentative).append(")");
            }
        }

        return message.toString();
    }

    public Player getCurrentPlayer() {
        return gameEnded ? winner : players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Player getWinner() {
        return winner;
    }

    public void printBoard() {
        board.printBoard();
    }

    public void printPlayerStatus() {
        System.out.println("\n=== PLAYER STATUS ===");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String indicator = (i == currentPlayerIndex && !gameEnded) ? " " : "   ";
            System.out.println(indicator + player);
        }
        System.out.println("====================\n");
    }
}
