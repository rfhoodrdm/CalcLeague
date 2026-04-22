package com.example.calcleague.model;

import com.example.calcleague.config.ExerciseType;
import java.time.LocalDate;

/**
 * Represents a player's high score entry stored on disk.
 */
public class HighScoreEntry {
    private ExerciseType type;
    private String playerName;
    private int score;
    private LocalDate achievedDate;

    public HighScoreEntry() {
        // Required for Jackson
    }

    public HighScoreEntry(ExerciseType type, String playerName, int score, LocalDate achievedDate) {
        this.type = type;
        this.playerName = playerName;
        this.score = score;
        this.achievedDate = achievedDate;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getAchievedDate() {
        return achievedDate;
    }

    public void setAchievedDate(LocalDate achievedDate) {
        this.achievedDate = achievedDate;
    }
}
