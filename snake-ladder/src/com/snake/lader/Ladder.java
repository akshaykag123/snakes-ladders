package com.snake.lader;

public class Ladder {
    private final int foot;
    private final int top;

    public Ladder(int foot, int top) {
        if (foot >= top) {
            throw new IllegalArgumentException("Ladder foot must be less than top");
        }
        this.foot = foot;
        this.top = top;
    }

    public int getFoot() {
        return foot;
    }

    public int getTop() {
        return top;
    }

    @Override
    public String toString() {
        return String.format("Ladder[%d->%d]", foot, top);
    }
}
