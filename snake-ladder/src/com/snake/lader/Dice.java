package com.snake.lader;

import java.util.Random;

public class Dice {
    private final Random random;

    public Dice() {
        this.random = new Random();
    }

    public Dice(long seed) {
        this.random = new Random(seed);
    }

    public int roll() {
        return random.nextInt(6) + 1; // Returns 1-6
    }
}
