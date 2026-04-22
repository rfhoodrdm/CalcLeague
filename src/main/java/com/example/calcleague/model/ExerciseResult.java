package com.example.calcleague.model;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;

/**
 * Summary of a completed exercise session.
 */
public class ExerciseResult {
    private final ExerciseType type;
    private final DifficultyLevel level;
    private final String playerName;
    private final int totalQuestions;
    private final int correctAnswers;
    private final long durationSeconds;
    private final int score;

    public ExerciseResult(ExerciseType type,
                          DifficultyLevel level,
                          String playerName,
                          int totalQuestions,
                          int correctAnswers,
                          long durationSeconds,
                          int score) {
        this.type = type;
        this.level = level;
        this.playerName = playerName;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.durationSeconds = durationSeconds;
        this.score = score;
    }

    public ExerciseType getType() {
        return type;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public int getScore() {
        return score;
    }

    public double getAccuracy() {
        return totalQuestions == 0 ? 0 : (double) correctAnswers / totalQuestions;
    }
}
