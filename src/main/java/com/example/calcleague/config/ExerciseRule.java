package com.example.calcleague.config;

/**
 * Configuration details describing how operands should be generated for an exercise.
 */
public final class ExerciseRule {
    private final OperandRange firstRange;
    private final OperandRange secondRange;
    private final OperandRange extraRange;

    private ExerciseRule(OperandRange firstRange, OperandRange secondRange, OperandRange extraRange) {
        this.firstRange = firstRange;
        this.secondRange = secondRange;
        this.extraRange = extraRange;
    }

    public static ExerciseRule of(OperandRange firstRange, OperandRange secondRange) {
        return new ExerciseRule(firstRange, secondRange, null);
    }

    public static ExerciseRule of(OperandRange firstRange, OperandRange secondRange, OperandRange extraRange) {
        return new ExerciseRule(firstRange, secondRange, extraRange);
    }

    public OperandRange firstRange() {
        return firstRange;
    }

    public OperandRange secondRange() {
        return secondRange;
    }

    public OperandRange extraRange() {
        return extraRange;
    }
}
