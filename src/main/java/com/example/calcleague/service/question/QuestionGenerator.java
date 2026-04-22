package com.example.calcleague.service.question;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseRule;
import com.example.calcleague.config.ExerciseType;
import com.example.calcleague.config.GameSettings;
import com.example.calcleague.config.OperandRange;
import com.example.calcleague.model.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a set of questions for a given exercise type and level.
 */
public class QuestionGenerator {
    private final Random random = new Random();

    public List<Question> generateQuestions(ExerciseType type, DifficultyLevel level) {
        List<Question> questions = new ArrayList<>(GameSettings.QUESTIONS_PER_SET);
        for (int i = 0; i < GameSettings.QUESTIONS_PER_SET; i++) {
            questions.add(switch (type) {
                case ADDITION -> createAdditionQuestion(type, level);
                case SUBTRACTION -> createSubtractionQuestion(type, level);
                case MULTIPLICATION -> createMultiplicationQuestion(type, level);
                case DIVISION -> createDivisionQuestion(type, level);
            });
        }
        return questions;
    }

    private Question createAdditionQuestion(ExerciseType type, DifficultyLevel level) {
        ExerciseRule rule = GameSettings.ruleFor(type, level);
        int first = rule.firstRange().random(random);
        int second = rule.secondRange().random(random);
        int answer = first + second;
        return new Question(type, level, first, second, 0, answer, first + " + " + second + " = ?");
    }

    private Question createSubtractionQuestion(ExerciseType type, DifficultyLevel level) {
        ExerciseRule rule = GameSettings.ruleFor(type, level);
        int minuend = rule.firstRange().random(random);
        int subtrahend = rule.secondRange().random(random);
        if (subtrahend > minuend) {
            int tmp = minuend;
            minuend = subtrahend;
            subtrahend = tmp;
        }
        int answer = minuend - subtrahend;
        return new Question(type, level, minuend, subtrahend, 0, answer, minuend + " - " + subtrahend + " = ?");
    }

    private Question createMultiplicationQuestion(ExerciseType type, DifficultyLevel level) {
        ExerciseRule rule = GameSettings.ruleFor(type, level);
        int first = rule.firstRange().random(random);
        int second = rule.secondRange().random(random);
        int answer = first * second;
        return new Question(type, level, first, second, 0, answer, first + " × " + second + " = ?");
    }

    private Question createDivisionQuestion(ExerciseType type, DifficultyLevel level) {
        ExerciseRule rule = GameSettings.ruleFor(type, level);
        OperandRange divisorRange = rule.firstRange();
        OperandRange quotientRange = rule.secondRange();
        OperandRange dividendRange = rule.extraRange();
        for (int attempts = 0; attempts < 500; attempts++) {
            int divisor = divisorRange.random(random);
            int minQuotient = quotientRange.min();
            int maxQuotient = quotientRange.max();
            if (dividendRange != null) {
                minQuotient = Math.max(minQuotient, (int) Math.ceil(dividendRange.min() / (double) divisor));
                maxQuotient = Math.min(maxQuotient, (int) Math.floor(dividendRange.max() / (double) divisor));
            }
            if (minQuotient > maxQuotient) {
                continue;
            }
            int quotient = randomBetween(minQuotient, maxQuotient);
            int dividend = divisor * quotient;
            return new Question(type, level, dividend, divisor, 0, quotient, dividend + " ÷ " + divisor + " = ?");
        }
        throw new IllegalStateException("Unable to generate division question for level " + level);
    }

    private int randomBetween(int min, int max) {
        if (min == max) {
            return min;
        }
        return random.nextInt(max - min + 1) + min;
    }
}
