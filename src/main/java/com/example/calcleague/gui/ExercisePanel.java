package com.example.calcleague.gui;

import com.example.calcleague.model.ExerciseSession;
import com.example.calcleague.model.Question;
import com.example.calcleague.model.UserAnswer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Presents each exercise question in sequence.
 */
public class ExercisePanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(ExercisePanel.class.getName());

    public interface ExerciseListener {
        void onExerciseCompleted(ExerciseSession session);

        void onQuitRequested(ExerciseSession session);
    }

    private ExerciseSession session;
    private ExerciseListener listener;

    private final JLabel typeLabel = new JLabel();
    private final JLabel levelLabel = new JLabel();
    private final JLabel questionCounterLabel = new JLabel();
    private final JLabel feedbackLabel = new JLabel(" \u00A0");
    private final JTextField stackedAnswerField = createAnswerField(SwingConstants.RIGHT);
    private final JTextField divisionAnswerField = createAnswerField(SwingConstants.CENTER);
    private JTextField activeAnswerField = stackedAnswerField;
    private final JButton submitButton = new JButton("Submit");
    private final JButton quitButton = new JButton("Quit");
    private final JProgressBar progressBar = new JProgressBar();

    private final CardLayout answerLayout = new CardLayout();
    private final JPanel answerHost = new JPanel(answerLayout);
    private final JLabel stackedFirstLabel = createValueLabel();
    private final JLabel stackedSecondLabel = createValueLabel();
    private final JLabel stackedOperatorLabel = new JLabel("+");
    private final JLabel divisionDividendLabel = createValueLabel();
    private final JLabel divisionDivisorLabel = createValueLabel();

    public ExercisePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(250, 252, 255));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
        configureActions();
    }

    public void beginSession(ExerciseSession session, ExerciseListener listener) {
        this.session = session;
        this.listener = listener;
        feedbackLabel.setText(" \u00A0");
        progressBar.setMaximum(session.getQuestionCount());
        progressBar.setValue(session.getAnswers().size());
        typeLabel.setText("Exercise: " + session.getType().getDisplayName());
        levelLabel.setText("Level: " + session.getLevel().getDisplayName());
        updateQuestionView();
        SwingUtilities.invokeLater(() -> activeAnswerField.requestFocusInWindow());
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        header.add(typeLabel, gbc);
        gbc.gridx = 1;
        header.add(levelLabel, gbc);
        gbc.gridx = 2;
        questionCounterLabel.setFont(questionCounterLabel.getFont().deriveFont(Font.BOLD));
        header.add(questionCounterLabel, gbc);
        gbc.gridx = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        header.add(progressBar, gbc);
        return header;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        answerHost.setOpaque(false);
        answerHost.add(buildStackedLayout(), "STACKED");
        answerHost.add(buildDivisionLayout(), "DIVISION");
        center.add(answerHost, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitPanel.setOpaque(false);
        submitPanel.add(submitButton);
        center.add(submitPanel, BorderLayout.SOUTH);
        return center;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        feedbackLabel.setForeground(Color.DARK_GRAY);
        footer.add(feedbackLabel, BorderLayout.CENTER);
        quitButton.addActionListener(e -> {
            if (listener != null && session != null) {
                listener.onQuitRequested(session);
            }
        });
        footer.add(quitButton, BorderLayout.EAST);
        return footer;
    }

    private JPanel buildStackedLayout() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        stackedFirstLabel.setFont(stackedFirstLabel.getFont().deriveFont(Font.BOLD, 36f));
        stackedSecondLabel.setFont(stackedSecondLabel.getFont().deriveFont(Font.BOLD, 36f));
        stackedOperatorLabel.setFont(stackedOperatorLabel.getFont().deriveFont(Font.BOLD, 32f));
        panel.add(stackedFirstLabel, gbc);
        gbc.gridy++;
        JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        secondRow.setOpaque(false);
        secondRow.add(stackedOperatorLabel);
        secondRow.add(stackedSecondLabel);
        panel.add(secondRow, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JSeparator(), gbc);
        gbc.gridy++;
        panel.add(stackedAnswerField, gbc);
        return panel;
    }

    private JPanel buildDivisionLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        divisionAnswerField.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(divisionAnswerField, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createDividerLine(), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        divisionDivisorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        divisionDivisorLabel.setFont(divisionDivisorLabel.getFont().deriveFont(Font.BOLD, 36f));
        panel.add(divisionDivisorLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        divisionDividendLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        divisionDividendLabel.setFont(divisionDividendLabel.getFont().deriveFont(Font.BOLD, 36f));
        panel.add(divisionDividendLabel, gbc);
        return panel;
    }

    private void configureActions() {
        submitButton.addActionListener(e -> submitAnswer());
        configureField(stackedAnswerField);
        configureField(divisionAnswerField);
    }

    private void configureField(JTextField field) {
        field.addActionListener(e -> submitAnswer());
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                feedbackLabel.setText(" \u00A0");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                feedbackLabel.setText(" \u00A0");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                feedbackLabel.setText(" \u00A0");
            }
        });
    }

    private void submitAnswer() {
        if (session == null || session.isComplete()) {
            return;
        }
        String text = activeAnswerField.getText().trim();
        if (text.isEmpty()) {
            feedbackLabel.setText("Please enter an answer.");
            return;
        }
        int value;
        try {
            value = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Answers must be whole numbers.");
            return;
        }
        UserAnswer answer = session.submitAnswer(value);
        progressBar.setValue(session.getAnswers().size());
        if (answer.isCorrect()) {
            feedbackLabel.setText("Correct!");
        } else {
            feedbackLabel.setText("Incorrect. Answer: " + answer.getQuestion().getCorrectAnswer());
            LOGGER.info(() -> String.format(
                    "Incorrect answer for %s: expected %d but received %d",
                    answer.getQuestion().getPrompt(),
                    answer.getQuestion().getCorrectAnswer(),
                    value));
        }
        activeAnswerField.setText("");
        if (session.isComplete()) {
            if (listener != null) {
                listener.onExerciseCompleted(session);
            }
        } else {
            updateQuestionView();
            SwingUtilities.invokeLater(() -> activeAnswerField.requestFocusInWindow());
        }
    }

    private void updateQuestionView() {
        Question question = session.getCurrentQuestion();
        if (question == null) {
            questionCounterLabel.setText("Completed");
            return;
        }
        int current = session.getAnswers().size() + 1;
        questionCounterLabel.setText("Question " + current + " / " + session.getQuestionCount());
        switch (question.getType()) {
            case ADDITION -> {
                stackedOperatorLabel.setText("+");
                stackedFirstLabel.setText(String.valueOf(question.getFirstValue()));
                stackedSecondLabel.setText(String.valueOf(question.getSecondValue()));
                answerLayout.show(answerHost, "STACKED");
                setActiveField(stackedAnswerField);
            }
            case SUBTRACTION -> {
                stackedOperatorLabel.setText("-");
                stackedFirstLabel.setText(String.valueOf(question.getFirstValue()));
                stackedSecondLabel.setText(String.valueOf(question.getSecondValue()));
                answerLayout.show(answerHost, "STACKED");
                setActiveField(stackedAnswerField);
            }
            case MULTIPLICATION -> {
                stackedOperatorLabel.setText("×");
                stackedFirstLabel.setText(String.valueOf(question.getFirstValue()));
                stackedSecondLabel.setText(String.valueOf(question.getSecondValue()));
                answerLayout.show(answerHost, "STACKED");
                setActiveField(stackedAnswerField);
            }
            case DIVISION -> {
                divisionDividendLabel.setText(String.valueOf(question.getFirstValue()));
                divisionDivisorLabel.setText(String.valueOf(question.getSecondValue()));
                answerLayout.show(answerHost, "DIVISION");
                setActiveField(divisionAnswerField);
            }
        }
    }

    private void setActiveField(JTextField field) {
        this.activeAnswerField = field;
        field.setText("");
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("0", SwingConstants.RIGHT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 32f));
        return label;
    }

    private JTextField createAnswerField(int alignment) {
        JTextField field = new JTextField(10);
        field.setHorizontalAlignment(alignment);
        field.setFont(field.getFont().deriveFont(Font.BOLD, 32f));
        return field;
    }

    private JSeparator createDividerLine() {
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        return separator;
    }
}
