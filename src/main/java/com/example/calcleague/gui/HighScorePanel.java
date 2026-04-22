package com.example.calcleague.gui;

import com.example.calcleague.config.ExerciseType;
import com.example.calcleague.model.HighScoreEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

/**
 * Displays the high score table per exercise type.
 */
public class HighScorePanel extends JPanel {

    public interface Listener {
        void onBackToSplash();

        void onShowHistory();

        void onExerciseTypeSelected(ExerciseType type);
    }

    private final JComboBox<ExerciseType> typeCombo = new JComboBox<>(ExerciseType.values());
    private final HighScoreTableModel tableModel = new HighScoreTableModel();
    private Listener listener;

    public HighScorePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(250, 250, 240));
        add(buildHeader(), BorderLayout.NORTH);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
        typeCombo.addActionListener(e -> {
            if (listener != null) {
                listener.onExerciseTypeSelected((ExerciseType) typeCombo.getSelectedItem());
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ExerciseType getSelectedType() {
        return (ExerciseType) typeCombo.getSelectedItem();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel label = new JLabel("High Scores", SwingConstants.LEFT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24f));
        header.add(label, BorderLayout.WEST);
        JPanel selector = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        selector.setOpaque(false);
        selector.add(new JLabel("Exercise:"));
        selector.add(typeCombo);
        header.add(selector, BorderLayout.EAST);
        return header;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        JButton history = new JButton("View Progress");
        history.addActionListener(e -> {
            if (listener != null) {
                listener.onShowHistory();
            }
        });
        JButton splash = new JButton("Back to Splash");
        splash.addActionListener(e -> {
            if (listener != null) {
                listener.onBackToSplash();
            }
        });
        footer.add(history);
        footer.add(splash);
        return footer;
    }

    public void setHighScores(List<HighScoreEntry> entries) {
        tableModel.setEntries(entries);
    }

    private static class HighScoreTableModel extends AbstractTableModel {
        private final String[] columns = {"Rank", "Player", "Score", "Date"};
        private List<HighScoreEntry> entries = new ArrayList<>();

        @Override
        public int getRowCount() {
            return entries.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            HighScoreEntry entry = entries.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> rowIndex + 1;
                case 1 -> entry.getPlayerName();
                case 2 -> entry.getScore();
                case 3 -> entry.getAchievedDate();
                default -> "";
            };
        }

        public void setEntries(List<HighScoreEntry> entries) {
            this.entries = new ArrayList<>(entries);
            fireTableDataChanged();
        }
    }
}
