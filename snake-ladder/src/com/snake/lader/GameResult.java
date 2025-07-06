package com.snake.lader;

public class GameResult {
    private final Player player;
    private final int previousPosition;
    private final int newPosition;
    private final int diceRoll;
    private final String message;
    private final boolean gameWon;

    public GameResult(Player player, int previousPosition, int newPosition,
                      int diceRoll, String message, boolean gameWon) {
        this.player = player;
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
        this.diceRoll = diceRoll;
        this.message = message;
        this.gameWon = gameWon;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public String getMessage() {
        return message;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}

