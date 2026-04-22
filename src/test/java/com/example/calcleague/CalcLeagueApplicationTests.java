package com.example.calcleague;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CalcLeagueApplicationTests {

    @Test
    void contextInitializesServices() {
        GameContext context = new GameContext();
        assertNotNull(context.getQuestionGenerator());
        assertNotNull(context.getScoreCalculationService());
        assertNotNull(context.getHighScoreService());
        assertNotNull(context.getHistoryService());
    }
}
