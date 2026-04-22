package com.example.calcleague.service.storage;

import com.example.calcleague.config.ExerciseType;
import com.example.calcleague.model.ExerciseResult;
import com.example.calcleague.model.HighScoreEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Handles persistence for the high score table.
 */
public class HighScoreService {
    private static final int MAX_ENTRIES_PER_TYPE = 10;

    private final FileStorageService storageService;
    private final Path file;

    public HighScoreService(FileStorageService storageService, StoragePaths paths) {
        this.storageService = storageService;
        this.file = paths.highScoreFile();
    }

    public List<HighScoreEntry> loadHighScores(ExerciseType type) {
        return Collections.unmodifiableList(readAll().get(type));
    }

    public void recordResult(ExerciseResult result) {
        Map<ExerciseType, List<HighScoreEntry>> data = readAll();
        List<HighScoreEntry> entries = new ArrayList<>(data.get(result.getType()));
        entries.add(new HighScoreEntry(result.getType(), result.getPlayerName(), result.getScore(), LocalDate.now()));
        entries.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());
        if (entries.size() > MAX_ENTRIES_PER_TYPE) {
            entries = new ArrayList<>(entries.subList(0, MAX_ENTRIES_PER_TYPE));
        }
        data.put(result.getType(), entries);
        storageService.write(file, data);
    }

    private Map<ExerciseType, List<HighScoreEntry>> readAll() {
        Map<ExerciseType, List<HighScoreEntry>> map = storageService.read(file, new TypeReference<>() {}, defaultMap());
        for (ExerciseType type : ExerciseType.values()) {
            map.computeIfAbsent(type, t -> new ArrayList<>());
        }
        return map;
    }

    private Map<ExerciseType, List<HighScoreEntry>> defaultMap() {
        Map<ExerciseType, List<HighScoreEntry>> result = new EnumMap<>(ExerciseType.class);
        for (ExerciseType type : ExerciseType.values()) {
            result.put(type, new ArrayList<>());
        }
        return result;
    }
}
