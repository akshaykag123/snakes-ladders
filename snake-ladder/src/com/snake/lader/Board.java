package com.snake.lader;


import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int BOARD_SIZE = 100;
    private final Map<Integer, Snake> snakes;
    private final Map<Integer, Ladder> ladders;

    public Board() {
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize snakes (head -> tail)
        addSnake(new Snake(36, 19));
        addSnake(new Snake(65, 35));
        addSnake(new Snake(87, 32));
        addSnake(new Snake(98, 78));

        // Initialize ladders (foot -> top)
        addLadder(new Ladder(7, 33));
        addLadder(new Ladder(37, 85));
        addLadder(new Ladder(51, 72));
        addLadder(new Ladder(21, 42));
    }

    private void addSnake(Snake snake) {
        if (ladders.containsKey(snake.getHead())) {
            throw new IllegalArgumentException("Cannot place snake at position with ladder");
        }
        snakes.put(snake.getHead(), snake);
    }

    private void addLadder(Ladder ladder) {
        if (snakes.containsKey(ladder.getFoot())) {
            throw new IllegalArgumentException("Cannot place ladder at position with snake");
        }
        ladders.put(ladder.getFoot(), ladder);
    }

    public int getNewPosition(int currentPosition) {
        if (currentPosition < 0 || currentPosition > BOARD_SIZE) {
            return currentPosition;
        }

        // Check for ladder
        if (ladders.containsKey(currentPosition)) {
            return ladders.get(currentPosition).getTop();
        }

        // Check for snake
        if (snakes.containsKey(currentPosition)) {
            return snakes.get(currentPosition).getTail();
        }

        return currentPosition;
    }

    public boolean hasSnakeAt(int position) {
        return snakes.containsKey(position);
    }

    public boolean hasLadderAt(int position) {
        return ladders.containsKey(position);
    }

    public Snake getSnakeAt(int position) {
        return snakes.get(position);
    }

    public Ladder getLadderAt(int position) {
        return ladders.get(position);
    }

    public void printBoard() {
        System.out.println("\n=== BOARD CONFIGURATION ===");
        System.out.println("Snakes:");
        snakes.values().forEach(snake ->
                System.out.println("  " + snake));

        System.out.println("Ladders:");
        ladders.values().forEach(ladder ->
                System.out.println("  " + ladder));
        System.out.println("===========================\n");
    }
}
