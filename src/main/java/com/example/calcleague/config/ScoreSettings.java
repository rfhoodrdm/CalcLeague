package com.example.calcleague.config;

/**
 * Scoring configuration for different difficulty levels.
 */
public final class ScoreSettings {
    private ScoreSettings() {
    }

    public static int multiplierFor(DifficultyLevel level) {
        return switch (level) {
            case LEVEL1 -> 1;
            case LEVEL2 -> 2;
            case LEVEL3 -> 3;
        };
    }
}
