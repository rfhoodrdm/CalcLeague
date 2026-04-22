package com.example.calcleague.gui;

import com.example.calcleague.GameContext;
import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;
import com.example.calcleague.model.ExerciseResult;
import com.example.calcleague.model.ExerciseSession;
import com.example.calcleague.model.HistoryRecord;
import com.example.calcleague.model.HighScoreEntry;
import com.example.calcleague.model.Question;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Hosts the various screens and orchestrates navigation between them.
 */
public class MainWindow extends JFrame implements SplashPanel.Listener,
        ExercisePanel.ExerciseListener,
        SummaryPanel.Listener,
        HighScorePanel.Listener,
        HistoryPanel.Listener {
    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());
    private static final String CARD_SPLASH = "SPLASH";
    private static final String CARD_EXERCISE = "EXERCISE";
    private static final String CARD_SUMMARY = "SUMMARY";
    private static final String CARD_HIGH = "HIGH";
    private static final String CARD_HISTORY = "HISTORY";

    private final GameContext context;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final SplashPanel splashPanel = new SplashPanel();
    private final ExercisePanel exercisePanel = new ExercisePanel();
    private final SummaryPanel summaryPanel = new SummaryPanel();
    private final HighScorePanel highScorePanel = new HighScorePanel();
    private final HistoryPanel historyPanel = new HistoryPanel();

    private ExerciseSession activeSession;

    public MainWindow(GameContext context) {
        super("CalcLeague");
        this.context = context;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 760));
        setLocationRelativeTo(null);
        splashPanel.setListener(this);
        summaryPanel.setListener(this);
        highScorePanel.setListener(this);
        historyPanel.setListener(this);
        cardPanel.add(splashPanel, CARD_SPLASH);
        cardPanel.add(exercisePanel, CARD_EXERCISE);
        cardPanel.add(summaryPanel, CARD_SUMMARY);
        cardPanel.add(highScorePanel, CARD_HIGH);
        cardPanel.add(historyPanel, CARD_HISTORY);
        add(cardPanel, BorderLayout.CENTER);
        showCard(CARD_SPLASH);
    }

    private void showCard(String card) {
        cardLayout.show(cardPanel, card);
    }

    @Override
    public void startExercise(ExerciseType type, DifficultyLevel level, String playerName) {
        try {
            List<Question> questions = context.getQuestionGenerator().generateQuestions(type, level);
            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Unable to generate questions. Please try again.");
                return;
            }
            ExerciseSession session = new ExerciseSession(type, level, playerName, questions);
            this.activeSession = session;
            exercisePanel.beginSession(session, this);
            showCard(CARD_EXERCISE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Unable to start exercise: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void openHighScores() {
        refreshHighScores(highScorePanel.getSelectedType());
        showCard(CARD_HIGH);
    }

    @Override
    public void openHistory() {
        refreshHistory();
        showCard(CARD_HISTORY);
    }

    @Override
    public void onExerciseCompleted(ExerciseSession session) {
        if (session != activeSession) {
            return;
        }
        finishExercise(session);
    }

    @Override
    public void onQuitRequested(ExerciseSession session) {
        if (session != activeSession) {
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "End the exercise and return to the splash screen?",
                "Quit Exercise",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            session.finishEarly();
            activeSession = null;
            showCard(CARD_SPLASH);
        }
    }

    @Override
    public void backToSplash() {
        showCard(CARD_SPLASH);
    }

    @Override
    public void onBackToSplash() {
        showCard(CARD_SPLASH);
    }

    @Override
    public void onShowHistory() {
        openHistory();
    }

    @Override
    public void onExerciseTypeSelected(ExerciseType type) {
        refreshHighScores(type);
    }

    @Override
    public void onShowHighScores() {
        openHighScores();
    }

    private void finishExercise(ExerciseSession session) {
        ExerciseResult result = toResult(session);
        context.getHighScoreService().recordResult(result);
        context.getHistoryService().appendResult(result);
        summaryPanel.showResult(result);
        LOGGER.info(() -> String.format(
                "Exercise completed: %s %s | accuracy %.0f%% | score %d",
                result.getType().getDisplayName(),
                result.getLevel().getDisplayName(),
                result.getAccuracy() * 100,
                result.getScore()));
        activeSession = null;
        showCard(CARD_SUMMARY);
    }

    private ExerciseResult toResult(ExerciseSession session) {
        int correct = session.getCorrectCount();
        int total = session.getQuestionCount();
        Duration elapsed = session.getElapsed();
        long seconds = Math.max(1, elapsed.getSeconds());
        int score = context.getScoreCalculationService().calculateScore(correct, session.getLevel());
        return new ExerciseResult(session.getType(),
                session.getLevel(),
                session.getPlayerName(),
                total,
                correct,
                seconds,
                score);
    }

    private void refreshHighScores(ExerciseType type) {
        List<HighScoreEntry> entries = context.getHighScoreService().loadHighScores(type);
        highScorePanel.setHighScores(entries);
    }

    private void refreshHistory() {
        List<HistoryRecord> history = context.getHistoryService().loadHistory();
        historyPanel.setHistory(history);
    }
}
