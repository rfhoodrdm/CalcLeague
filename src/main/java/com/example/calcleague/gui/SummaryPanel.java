package com.example.calcleague.gui;

import com.example.calcleague.model.ExerciseResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Shows the stats for the recently completed exercise.
 */
public class SummaryPanel extends JPanel {

    public interface Listener {
        void backToSplash();
    }

    private final JLabel activityLabel = new JLabel();
    private final JLabel levelLabel = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JLabel accuracyLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private Listener listener;

    public SummaryPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(238, 246, 255));
        add(buildStats(), BorderLayout.CENTER);
        JButton backButton = new JButton("Return to Splash");
        backButton.addActionListener(e -> {
            if (listener != null) {
                listener.backToSplash();
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private JPanel buildStats() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 8, 8));
        panel.setOpaque(false);
        JLabel header = new JLabel("Exercise summary", JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 24f));
        panel.add(header);

        panel.add(activityLabel);
        panel.add(levelLabel);
        panel.add(timeLabel);
        panel.add(accuracyLabel);
        panel.add(scoreLabel);
        return panel;
    }

    public void showResult(ExerciseResult result) {
        activityLabel.setText("Exercise: " + result.getType().getDisplayName());
        levelLabel.setText("Level: " + result.getLevel().getDisplayName());
        timeLabel.setText("Time: " + result.getDurationSeconds() + "s");
        accuracyLabel.setText(String.format("Accuracy: %.0f%% (%d/%d)",
                result.getAccuracy() * 100,
                result.getCorrectAnswers(),
                result.getTotalQuestions()));
        scoreLabel.setText("Score: " + result.getScore());
    }
}
