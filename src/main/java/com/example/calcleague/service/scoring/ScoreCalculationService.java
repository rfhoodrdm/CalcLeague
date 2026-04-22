package com.example.calcleague.service.scoring;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.GameSettings;
import com.example.calcleague.config.ScoreSettings;

/**
 * Calculates the total score for a completed exercise.
 */
public class ScoreCalculationService {

    public int calculateScore(int correctAnswers, DifficultyLevel level) {
        int multiplier = ScoreSettings.multiplierFor(level);
        return Math.max(0, correctAnswers) * GameSettings.BASE_POINTS_PER_CORRECT * multiplier;
    }
}
