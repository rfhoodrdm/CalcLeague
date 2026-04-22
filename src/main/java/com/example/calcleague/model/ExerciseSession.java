package com.example.calcleague.model;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tracks the lifecycle of an exercise run.
 */
public class ExerciseSession {
    private final ExerciseType type;
    private final DifficultyLevel level;
    private final String playerName;
    private final List<Question> questions;
    private final List<UserAnswer> answers = new ArrayList<>();
    private final Instant startedAt = Instant.now();
    private Instant finishedAt;

    public ExerciseSession(ExerciseType type, DifficultyLevel level, String playerName, List<Question> questions) {
        this.type = type;
        this.level = level;
        this.playerName = playerName;
        this.questions = List.copyOf(questions);
    }

    public Question getCurrentQuestion() {
        if (isComplete()) {
            return null;
        }
        return questions.get(answers.size());
    }

    public UserAnswer submitAnswer(int submittedAnswer) {
        Question question = getCurrentQuestion();
        if (question == null) {
            throw new IllegalStateException("Exercise already completed");
        }
        boolean correct = question.getCorrectAnswer() == submittedAnswer;
        UserAnswer answer = new UserAnswer(question, submittedAnswer, correct);
        answers.add(answer);
        if (isComplete()) {
            finishedAt = Instant.now();
        }
        return answer;
    }

    public void finishEarly() {
        if (finishedAt == null) {
            finishedAt = Instant.now();
        }
    }

    public boolean isComplete() {
        return answers.size() >= questions.size();
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

    public int getQuestionCount() {
        return questions.size();
    }

    public int getCorrectCount() {
        return (int) answers.stream().filter(UserAnswer::isCorrect).count();
    }

    public List<UserAnswer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public Duration getElapsed() {
        Instant end = finishedAt != null ? finishedAt : Instant.now();
        return Duration.between(startedAt, end);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
