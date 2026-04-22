package com.example.calcleague.config;

import java.util.EnumMap;
import java.util.Map;

/**
 * Central location for global app configuration.
 */
public final class GameSettings {
    public static final int QUESTIONS_PER_SET = 25;
    public static final int BASE_POINTS_PER_CORRECT = 10;

    private static final Map<ExerciseType, Map<DifficultyLevel, ExerciseRule>> RULES = buildRules();

    private GameSettings() {
    }

    public static ExerciseRule ruleFor(ExerciseType type, DifficultyLevel level) {
        return RULES.get(type).get(level);
    }

    private static Map<ExerciseType, Map<DifficultyLevel, ExerciseRule>> buildRules() {
        Map<ExerciseType, Map<DifficultyLevel, ExerciseRule>> map = new EnumMap<>(ExerciseType.class);
        map.put(ExerciseType.ADDITION, additionRules());
        map.put(ExerciseType.SUBTRACTION, subtractionRules());
        map.put(ExerciseType.MULTIPLICATION, multiplicationRules());
        map.put(ExerciseType.DIVISION, divisionRules());
        return map;
    }

    private static Map<DifficultyLevel, ExerciseRule> additionRules() {
        Map<DifficultyLevel, ExerciseRule> rules = new EnumMap<>(DifficultyLevel.class);
        rules.put(DifficultyLevel.LEVEL1, ExerciseRule.of(new OperandRange(10, 99), new OperandRange(10, 99)));
        rules.put(DifficultyLevel.LEVEL2, ExerciseRule.of(new OperandRange(100, 999), new OperandRange(10, 99)));
        rules.put(DifficultyLevel.LEVEL3, ExerciseRule.of(new OperandRange(100, 999), new OperandRange(100, 999)));
        return rules;
    }

    private static Map<DifficultyLevel, ExerciseRule> subtractionRules() {
        Map<DifficultyLevel, ExerciseRule> rules = new EnumMap<>(DifficultyLevel.class);
        rules.put(DifficultyLevel.LEVEL1, ExerciseRule.of(new OperandRange(20, 99), new OperandRange(10, 99)));
        rules.put(DifficultyLevel.LEVEL2, ExerciseRule.of(new OperandRange(100, 999), new OperandRange(100, 999)));
        rules.put(DifficultyLevel.LEVEL3, ExerciseRule.of(new OperandRange(100, 999), new OperandRange(100, 999)));
        return rules;
    }

    private static Map<DifficultyLevel, ExerciseRule> multiplicationRules() {
        Map<DifficultyLevel, ExerciseRule> rules = new EnumMap<>(DifficultyLevel.class);
        rules.put(DifficultyLevel.LEVEL1, ExerciseRule.of(new OperandRange(10, 99), new OperandRange(2, 9)));
        rules.put(DifficultyLevel.LEVEL2, ExerciseRule.of(new OperandRange(10, 99), new OperandRange(10, 20)));
        rules.put(DifficultyLevel.LEVEL3, ExerciseRule.of(new OperandRange(10, 999), new OperandRange(10, 99)));
        return rules;
    }

    private static Map<DifficultyLevel, ExerciseRule> divisionRules() {
        Map<DifficultyLevel, ExerciseRule> rules = new EnumMap<>(DifficultyLevel.class);
        // First range is divisor, second is quotient, third constrains the dividend/product
        rules.put(DifficultyLevel.LEVEL1,
                ExerciseRule.of(new OperandRange(2, 9), new OperandRange(2, 12), new OperandRange(10, 99)));
        rules.put(DifficultyLevel.LEVEL2,
                ExerciseRule.of(new OperandRange(2, 99), new OperandRange(2, 99), new OperandRange(100, 999)));
        rules.put(DifficultyLevel.LEVEL3,
                ExerciseRule.of(new OperandRange(2, 99), new OperandRange(2, 999), new OperandRange(100, 9999)));
        return rules;
    }
}
