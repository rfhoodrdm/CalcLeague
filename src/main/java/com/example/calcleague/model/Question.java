package com.example.calcleague.model;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;

/**
 * Represents a single arithmetic question.
 */
public class Question {
    private final ExerciseType type;
    private final DifficultyLevel level;
    private final int firstValue;
    private final int secondValue;
    private final int thirdValue;
    private final int correctAnswer;
    private final String prompt;

    public Question(ExerciseType type,
                    DifficultyLevel level,
                    int firstValue,
                    int secondValue,
                    int thirdValue,
                    int correctAnswer,
                    String prompt) {
        this.type = type;
        this.level = level;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
        this.correctAnswer = correctAnswer;
        this.prompt = prompt;
    }

    public ExerciseType getType() {
        return type;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public int getThirdValue() {
        return thirdValue;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }
}
