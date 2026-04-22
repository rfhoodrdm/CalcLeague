package com.example.calcleague.gui;

import com.example.calcleague.config.DifficultyLevel;
import com.example.calcleague.config.ExerciseType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Splash/home screen where the player configures the upcoming exercise.
 */
public class SplashPanel extends JPanel {

    public interface Listener {
        void startExercise(ExerciseType type, DifficultyLevel level, String playerName);

        void openHighScores();

        void openHistory();
    }

    private Listener listener;
    private final JComboBox<ExerciseType> typeCombo = new JComboBox<>(ExerciseType.values());
    private final JComboBox<DifficultyLevel> levelCombo = new JComboBox<>(DifficultyLevel.values());
    private final JTextField nameField = new JTextField("Player");
    private final JLabel validationLabel = new JLabel(" ");

    public SplashPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(64, 90, 128));
        JLabel title = new JLabel("CalcLeague", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        header.add(title, BorderLayout.CENTER);
        ImageIcon logoIcon = loadLogoIcon();
        if (logoIcon != null) {
            JLabel logo = new JLabel(logoIcon);
            logo.setPreferredSize(new Dimension(120, 120));
            header.add(logo, BorderLayout.WEST);
        }
        return header;
    }

    private ImageIcon loadLogoIcon() {
        String[] candidates = {"/images/small_logo.png", "/images/logo.png"};
        for (String candidate : candidates) {
            URL resource = getClass().getResource(candidate);
            if (resource != null) {
                ImageIcon icon = new ImageIcon(resource);
                Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                return new ImageIcon(image);
            }
        }
        return null;
    }

    private JPanel buildContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel subtitle = new JLabel("Choose an exercise to begin");
        subtitle.setFont(subtitle.getFont().deriveFont(Font.BOLD, 20f));
        gbc.gridwidth = 2;
        panel.add(subtitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Player Name"), gbc);
        gbc.gridx = 1;
        nameField.setColumns(15);
        panel.add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Exercise"), gbc);
        gbc.gridx = 1;
        panel.add(typeCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Level"), gbc);
        gbc.gridx = 1;
        panel.add(levelCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton startButton = new JButton("Start Exercise");
        startButton.addActionListener(e -> notifyStart());
        panel.add(startButton, gbc);

        gbc.gridy++;
        validationLabel.setForeground(Color.RED.darker());
        panel.add(validationLabel, gbc);

        gbc.gridy++;
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        JButton highScores = new JButton("View High Scores");
        highScores.addActionListener(e -> {
            if (listener != null) {
                listener.openHighScores();
            }
        });
        JButton history = new JButton("View Progress");
        history.addActionListener(e -> {
            if (listener != null) {
                listener.openHistory();
            }
        });
        navPanel.add(highScores);
        navPanel.add(history);
        panel.add(navPanel, gbc);

        return panel;
    }

    private void notifyStart() {
        if (listener == null) {
            return;
        }
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            validationLabel.setText("Please enter your name.");
            return;
        }
        validationLabel.setText(" ");
        listener.startExercise((ExerciseType) typeCombo.getSelectedItem(),
                (DifficultyLevel) levelCombo.getSelectedItem(),
                name);
    }
}
