package com.example.calcleague;

import com.example.calcleague.service.question.QuestionGenerator;
import com.example.calcleague.service.scoring.ScoreCalculationService;
import com.example.calcleague.service.storage.FileStorageService;
import com.example.calcleague.service.storage.HighScoreService;
import com.example.calcleague.service.storage.HistoryService;
import com.example.calcleague.service.storage.StoragePaths;
import com.example.calcleague.util.JsonMapperFactory;

/** 
 * Simple container for application level services. Swapping implementations later is easy.
 */
public class GameContext {
    private final QuestionGenerator questionGenerator = new QuestionGenerator();
    private final ScoreCalculationService scoreCalculationService = new ScoreCalculationService();
    private final HighScoreService highScoreService;
    private final HistoryService historyService;

    public GameContext() {
        StoragePaths paths = new StoragePaths();
        FileStorageService fileStorageService = new FileStorageService(JsonMapperFactory.createMapper());
        this.highScoreService = new HighScoreService(fileStorageService, paths);
        this.historyService = new HistoryService(fileStorageService, paths);
    }

    public QuestionGenerator getQuestionGenerator() {
        return questionGenerator;
    }

    public ScoreCalculationService getScoreCalculationService() {
        return scoreCalculationService;
    }

    public HighScoreService getHighScoreService() {
        return highScoreService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }
}
