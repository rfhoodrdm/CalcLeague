package com.example.calcleague.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Resolves file locations for high scores and historic data.
 */
public class StoragePaths {
    private final Path baseDir;

    public StoragePaths() {
        this(Paths.get(System.getProperty("user.home"), ".calcleague"));
    }

    public StoragePaths(Path baseDir) {
        this.baseDir = baseDir;
        ensureExists(baseDir);
    }

    private void ensureExists(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create storage directory " + dir, e);
        }
    }

    public Path highScoreFile() {
        return baseDir.resolve("highscores.json");
    }

    public Path historyFile() {
        return baseDir.resolve("history.json");
    }
}
