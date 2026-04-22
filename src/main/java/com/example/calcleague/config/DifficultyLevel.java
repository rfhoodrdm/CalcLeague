package com.example.calcleague.config;

/**
 * Difficulty levels for each exercise type.
 */
public enum DifficultyLevel {
    LEVEL1("Level 1"),
    LEVEL2("Level 2"),
    LEVEL3("Level 3");

    private final String displayName;

    DifficultyLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
