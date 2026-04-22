package com.example.calcleague.model;

/**
 * Captures a player's response to a question.
 */
public class UserAnswer {
    private final Question question;
    private final int submittedAnswer;
    private final boolean correct;

    public UserAnswer(Question question, int submittedAnswer, boolean correct) {
        this.question = question;
        this.submittedAnswer = submittedAnswer;
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public int getSubmittedAnswer() {
        return submittedAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }
}
