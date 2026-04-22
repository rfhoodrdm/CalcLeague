package com.example.calcleague.config;

/**
 * Inclusive operand bounds used when generating questions.
 */
public record OperandRange(int min, int max) {
    public int random(java.util.Random random) {
        return random.nextInt(max - min + 1) + min;
    }
}
