package com.example.calcleague.model;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

/**
 * Entry used to power the history/progress graph.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryRecord {
    private ExerciseType type;
    private DifficultyLevel level;
    private int totalQuestions;
    private int correctAnswers;
    private long durationSeconds;
    private int score;
    private LocalDateTime completedAt;

    public HistoryRecord() {
        // Default constructor for Jackson
    }

    public HistoryRecord(ExerciseType type,
                         DifficultyLevel level,
                         int totalQuestions,
                         int correctAnswers,
                         long durationSeconds,
                         int score,
                         LocalDateTime completedAt) {
        this.type = type;
        this.level = level;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.durationSeconds = durationSeconds;
        this.score = score;
        this.completedAt = completedAt;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public void setLevel(DifficultyLevel level) {
        this.level = level;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public double getAccuracy() {
        return totalQuestions == 0 ? 0 : (double) correctAnswers / totalQuestions;
    }
}
