package com.example.calcleague.service.storage;

import com.example.calcleague.model.ExerciseResult;
import com.example.calcleague.model.HistoryRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Stores and retrieves exercise history for the progress screen.
 */
public class HistoryService {
    private static final int MAX_HISTORY_ENTRIES = 500;

    private final FileStorageService storageService;
    private final Path file;

    public HistoryService(FileStorageService storageService, StoragePaths paths) {
        this.storageService = storageService;
        this.file = paths.historyFile();
    }

    public List<HistoryRecord> loadHistory() {
        List<HistoryRecord> records = new ArrayList<>(readAll());
        records.sort(Comparator.comparing(HistoryRecord::getCompletedAt));
        return records;
    }

    public void appendResult(ExerciseResult result) {
        List<HistoryRecord> history = new ArrayList<>(readAll());
        history.add(new HistoryRecord(result.getType(),
                result.getLevel(),
                result.getTotalQuestions(),
                result.getCorrectAnswers(),
                result.getDurationSeconds(),
                result.getScore(),
                LocalDateTime.now()));
        history.sort(Comparator.comparing(HistoryRecord::getCompletedAt));
        if (history.size() > MAX_HISTORY_ENTRIES) {
            history = new ArrayList<>(history.subList(history.size() - MAX_HISTORY_ENTRIES, history.size()));
        }
        storageService.write(file, history);
    }

    private List<HistoryRecord> readAll() {
        return storageService.read(file, new TypeReference<>() {}, new ArrayList<>());
    }
}
