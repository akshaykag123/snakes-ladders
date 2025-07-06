package com.snake.lader;

public class Player {
    private final String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0; // Starting position outside the board
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0 || position > 100) {
            throw new IllegalArgumentException("Position must be between 0 and 100");
        }
        this.position = position;
    }

    public void move(int steps) {
        int newPosition = position + steps;
        if (newPosition <= 100) {
            position = newPosition;
        }
        // If newPosition > 100, position remains unchanged
    }

    public boolean hasWon() {
        return position == 100;
    }

    @Override
    public String toString() {
        return String.format("%s (Position: %d)", name, position);
    }
}
