package com.example.calcleague;

import com.example.calcleague.gui.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Application entry point.
 */
public class CalcLeagueApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameContext context = new GameContext();
            MainWindow window = new MainWindow(context);
            window.setVisible(true);
        });
    }
}
