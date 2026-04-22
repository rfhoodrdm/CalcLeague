package com.example.calcleague.gui;

import com.example.calcleague.model.HistoryRecord;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.time.format.DateTimeFormatter;
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
 * Shows historical progress in both a graph and tabular form.
 */
public class HistoryPanel extends JPanel {

    public interface Listener {
        void onBackToSplash();

        void onShowHighScores();
    }

    private final JComboBox<Metric> metricCombo = new JComboBox<>(Metric.values());
    private final HistoryGraphPanel graphPanel = new HistoryGraphPanel();
    private final HistoryTableModel tableModel = new HistoryTableModel();
    private Listener listener;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(buildHeader(), BorderLayout.NORTH);
        add(buildGraphSection(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
        metricCombo.addActionListener(e -> graphPanel.setMetric((Metric) metricCombo.getSelectedItem()));
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setOpaque(false);
        JLabel title = new JLabel("Progress Tracker");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        header.add(title);
        header.add(new JLabel("Metric:"));
        header.add(metricCombo);
        return header;
    }

    private JPanel buildGraphSection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        graphPanel.setPreferredSize(new Dimension(800, 300));
        panel.add(graphPanel, BorderLayout.NORTH);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        JButton highScores = new JButton("High Scores");
        highScores.addActionListener(e -> {
            if (listener != null) {
                listener.onShowHighScores();
            }
        });
        JButton splash = new JButton("Back to Splash");
        splash.addActionListener(e -> {
            if (listener != null) {
                listener.onBackToSplash();
            }
        });
        footer.add(highScores);
        footer.add(splash);
        return footer;
    }

    public void setHistory(List<HistoryRecord> records) {
        graphPanel.setHistory(records);
        graphPanel.setMetric((Metric) metricCombo.getSelectedItem());
        tableModel.setRecords(records);
    }

    private enum Metric {
        SCORE("Total Score") {
            @Override
            double value(HistoryRecord record) {
                return record.getScore();
            }
        },
        CORRECT("Correct Answers") {
            @Override
            double value(HistoryRecord record) {
                return record.getCorrectAnswers();
            }
        },
        TIME("Completion Time (s)") {
            @Override
            double value(HistoryRecord record) {
                return record.getDurationSeconds();
            }
        };

        private final String label;

        Metric(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        abstract double value(HistoryRecord record);
    }

    private static class HistoryGraphPanel extends JPanel {
        private final Color lineColor = new Color(70, 130, 180);
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        private List<HistoryRecord> records = new ArrayList<>();
        private Metric metric = Metric.SCORE;

        public HistoryGraphPanel() {
            setPreferredSize(new Dimension(800, 320));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }

        public void setHistory(List<HistoryRecord> records) {
            this.records = new ArrayList<>(records);
            repaint();
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            int margin = 40;
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect(margin - 10, margin - 10, width - 2 * margin + 20, height - 2 * margin + 20);
            if (records.isEmpty()) {
                g2.drawString("No history yet", width / 2 - 40, height / 2);
                g2.dispose();
                return;
            }
            double max = records.stream().mapToDouble(metric::value).max().orElse(1);
            if (max <= 0) {
                max = 1;
            }
            int plotWidth = width - 2 * margin;
            int plotHeight = height - 2 * margin;
            g2.drawLine(margin, height - margin, width - margin, height - margin);
            g2.drawLine(margin, margin, margin, height - margin);
            Path2D path = new Path2D.Double();
            int n = records.size();
            for (int i = 0; i < n; i++) {
                HistoryRecord record = records.get(i);
                double value = metric.value(record);
                double xRatio = (n == 1) ? 0.5 : (i / (double) (n - 1));
                int x = margin + (int) (xRatio * plotWidth);
                int y = height - margin - (int) ((value / max) * plotHeight);
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
                g2.setColor(Color.GRAY);
                if (record.getCompletedAt() != null) {
                    g2.drawString(formatter.format(record.getCompletedAt()), x - 15, height - margin + 15);
                }
            }
            g2.setColor(lineColor);
            g2.setStroke(new java.awt.BasicStroke(2f));
            g2.draw(path);
            g2.dispose();
        }
    }

    private static class HistoryTableModel extends AbstractTableModel {
        private final String[] columns = {"Completed", "Exercise", "Level", "Correct", "Score", "Time (s)"};
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private List<HistoryRecord> records = new ArrayList<>();

        @Override
        public int getRowCount() {
            return records.size();
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
            HistoryRecord record = records.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> record.getCompletedAt() != null ? formatter.format(record.getCompletedAt()) : "";
                case 1 -> record.getType().getDisplayName();
                case 2 -> record.getLevel().getDisplayName();
                case 3 -> record.getCorrectAnswers() + "/" + record.getTotalQuestions();
                case 4 -> record.getScore();
                case 5 -> record.getDurationSeconds();
                default -> "";
            };
        }

        public void setRecords(List<HistoryRecord> records) {
            this.records = new ArrayList<>(records);
            fireTableDataChanged();
        }
    }
}
